package senyu.design.myboa.fragment;


import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import senyu.design.myboa.MyItemDragAndSwipeCallback;
import senyu.design.myboa.R;
import senyu.design.myboa.adapter.OweRecyclerViewAdapter;
import senyu.design.myboa.bean.OweBean;
import senyu.design.myboa.utils.SPUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class OweFragment extends Fragment {



    private RecyclerView recyclerView;
    private List<OweBean> mDatas;
    private OweRecyclerViewAdapter adapter;
    private TextView totaloweTV;

    public OweFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_owe, container, false);
        recyclerView = view.findViewById(R.id.owe_rv);
        totaloweTV = view.findViewById(R.id.amount);
        String countText = (String) SPUtils.get(getActivity(),"totalOwe","0.0");
        totaloweTV.setText(countText);
        initData();
        //初始化adapter和recyclerview
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new OweRecyclerViewAdapter(mDatas);
        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new MyItemDragAndSwipeCallback(adapter);
        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        //注册拖动
        adapter.enableDragItem(itemTouchHelper,R.id.iv_img,true);
        //注册滑动
        adapter.enableSwipeItem();
        //滑动删除监听
        adapter.setOnItemSwipeListener(new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                mDatas.remove(pos);
                adapter.notifyDataSetChanged();
                countTotal();
                SPUtils.saveOweToSP(mDatas,getActivity());
            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {

            }
        });
        //拖动监听
        final OnItemDragListener onItemDragListener = new OnItemDragListener() {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos){

            }
            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {


            }
            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
                adapter.notifyDataSetChanged();
                countTotal();
                SPUtils.saveOweToSP(mDatas,getActivity());
            }
        };
        adapter.setOnItemDragListener(onItemDragListener);
        //点击事件
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                DialogFragment dialogFragment = new MyEditNumDialog();
                Bundle bundle = new Bundle();
                bundle.putDouble("amount",mDatas.get(position).getAmount());
                bundle.putInt("position",position);
                dialogFragment.setTargetFragment(OweFragment.this,1);
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getFragmentManager(),"dialogfragment");
                Toasty.info(getActivity(),"请修改金额").show();
            }
        });
        recyclerView.setAdapter(adapter);
        return view;
    }


    /**
     * 从本地初始化数据
     * */
    private void initData(){
        if(SPUtils.getBeanFromSP(getActivity(),SPUtils.OWE_BEAN_KEY,"") != null){
            mDatas =  SPUtils.getOweFromSP(getActivity(),SPUtils.OWE_BEAN_KEY,"");
        }
        else{
            mDatas = new ArrayList<>();
            OweBean huabei = new OweBean(OweBean.ID.HUA_BEI, "蚂蚁花呗", R.drawable.huabei, R.drawable.alipay_bg, 0, "蚂蚁花呗使用额度");
            OweBean baitiao = new OweBean(OweBean.ID.JD_BAITIAO, "京东白条", R.drawable.jd, R.drawable.jd_bg, 0, "京东白条使用额度");
            OweBean bank = new OweBean(OweBean.ID.CREDIT_OWE, "信用卡欠款", R.drawable.credit_owe, R.drawable.credit_card_bg, 0, "信用卡使用额度");
            OweBean bill = new OweBean(OweBean.ID.OWE_BILL, "借款", R.drawable.borrow, R.drawable.lent_bg, 0, "欠别人的钱");
            mDatas.add(huabei);
            mDatas.add(baitiao);
            mDatas.add(bank);
            mDatas.add(bill);
            SPUtils.saveOweToSP(mDatas,getActivity());
        }
    }

    /**
     * 识别从EditText上回来的double数据并且更新ui和存储到sp
     * */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == 1 && data != null) {
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        String myData = bundle.get("amount").toString();
                        int position = bundle.getInt("position");
                        mDatas.get(position).setAmount(Double.valueOf(myData));
                        adapter.notifyDataSetChanged();
                        countTotal();
                        SPUtils.saveOweToSP(mDatas,getActivity());
                    }
                }
                break;
            default:break;
        }
    }

    public void addItem(int layoutID){
        switch (layoutID){
            case OweBean.ID.HUA_BEI:
                OweBean cash = new OweBean(OweBean.ID.HUA_BEI, "蚂蚁花呗", R.drawable.huabei, R.drawable.alipay_bg, 0, "蚂蚁花呗使用额度");
                mDatas.add(cash);
                break;
            case OweBean.ID.JD_BAITIAO:
                OweBean invest = new OweBean(OweBean.ID.JD_BAITIAO, "京东白条", R.drawable.jd, R.drawable.jd_bg, 0, "京东白条使用额度");
                mDatas.add(invest);
                break;
            case OweBean.ID.CREDIT_OWE:
                OweBean lent = new OweBean(OweBean.ID.CREDIT_OWE, "信用卡欠款", R.drawable.credit_owe, R.drawable.credit_card_bg, 0, "信用卡使用额度");
                mDatas.add(lent);
                break;
            case OweBean.ID.OWE_BILL:
                OweBean bank = new OweBean(OweBean.ID.OWE_BILL, "借款", R.drawable.borrow, R.drawable.lent_bg, 0, "欠别人的钱");
                mDatas.add(bank);
                break;
            default:break;
        }
    }
    public  void noticeAndSave(){
        adapter.notifyDataSetChanged();
        countTotal();
        SPUtils.saveOweToSP(mDatas,getActivity());
    }
    public void countTotal(){
        Double count = 0.0;
        for(int i = 0; i < mDatas.size();i++){
            count+= mDatas.get(i).getAmount();
        }
        totaloweTV.setText(String.valueOf(count));
        SPUtils.put(getActivity(),"totalOwe",count);
    }

    public List<OweBean> getmDatas() {
        return mDatas;
    }


    public void update(int j, double cost, boolean plusOrNot) {
        mDatas.get(j).computerAmount(cost,plusOrNot);
        adapter.notifyDataSetChanged();
        SPUtils.saveOweToSP(mDatas,getActivity());
        countTotal();
    }
}
