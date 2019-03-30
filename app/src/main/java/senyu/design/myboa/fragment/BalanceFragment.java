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
import senyu.design.myboa.adapter.MoneyRecyclerViewAdapter;
import senyu.design.myboa.bean.BalanceBean;
import senyu.design.myboa.utils.SPUtils;

public class BalanceFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<BalanceBean> mDatas;
    private MoneyRecyclerViewAdapter adapter;
    private TextView totalCountTV;
    public BalanceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_balance, container, false);
        recyclerView = view.findViewById(R.id.balance_rcv);
        totalCountTV = view.findViewById(R.id.amount);
        String countText = (String) SPUtils.get(getActivity(),"totalBalance","0.0");
        totalCountTV.setText(countText);
        initData();
        //初始化adapter和recyclerview
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MoneyRecyclerViewAdapter(mDatas);
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
                SPUtils.saveBeantoSP(mDatas,getActivity());
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
                SPUtils.saveBeantoSP(mDatas,getActivity());
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
                dialogFragment.setTargetFragment(BalanceFragment.this,1);
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
        if(SPUtils.getBeanFromSP(getActivity(),SPUtils.BALANCE_BEAN_KEY,"") != null){
            mDatas =  SPUtils.getBeanFromSP(getActivity(),SPUtils.BALANCE_BEAN_KEY,"");
        }
        else{
            mDatas = new ArrayList<>();
            BalanceBean cash = new BalanceBean(BalanceBean.ID.CASH, "现金", R.drawable.cash, R.drawable.cash_bg, 0, "剩余现金总额");
            BalanceBean invest = new BalanceBean(BalanceBean.ID.INVEST, "投资账户", R.drawable.invest, R.drawable.invest_bg, 0, "投资账户上的资金");
            BalanceBean bank = new BalanceBean(BalanceBean.ID.CREDIT_CARD, "储蓄卡余额", R.drawable.credit_card, R.drawable.credit_card_bg, 0, "储蓄卡余额");
            BalanceBean lent = new BalanceBean(BalanceBean.ID.LENT, "应收帐", R.drawable.lent, R.drawable.lent_bg, 0, "别人欠我的钱");
            BalanceBean wechat = new BalanceBean(BalanceBean.ID.WECHAT_PAY, "微信钱包", R.drawable.wechat_icon, R.drawable.wechat_bg, 0, "微信钱包余额");
            BalanceBean alipay = new BalanceBean(BalanceBean.ID.ALI_PAY, "支付宝", R.drawable.alipay, R.drawable.alipay_bg, 0, "支付宝余额");
            mDatas.add(cash);
            mDatas.add(invest);
            mDatas.add(bank);
            mDatas.add(lent);
            mDatas.add(wechat);
            mDatas.add(alipay);
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
                        SPUtils.saveBeantoSP(mDatas,getActivity());
                    }
                }
                break;
                default:break;
        }
    }

   public void addItem(int layoutID){
        switch (layoutID){
            case BalanceBean.ID.CASH:
                BalanceBean cash = new BalanceBean(BalanceBean.ID.CASH, "现金" , R.drawable.cash, R.drawable.cash_bg, 0, "剩余现金总额");
                mDatas.add(cash);
                break;
            case BalanceBean.ID.INVEST:
                BalanceBean invest = new BalanceBean(BalanceBean.ID.INVEST, "投资账户" , R.drawable.invest, R.drawable.invest_bg, 0, "投资账户上的资金");
                mDatas.add(invest);
                break;
            case BalanceBean.ID.LENT:
                BalanceBean lent = new BalanceBean(BalanceBean.ID.LENT, "应收帐" , R.drawable.lent, R.drawable.lent_bg, 0, "别人欠我的钱");
                mDatas.add(lent);
                break;
            case BalanceBean.ID.CREDIT_CARD:
                BalanceBean bank = new BalanceBean(BalanceBean.ID.CREDIT_CARD, "储蓄卡余额" , R.drawable.credit_card, R.drawable.credit_card_bg, 0, "储蓄卡余额");
                mDatas.add(bank);
                break;
            case BalanceBean.ID.WECHAT_PAY:
                BalanceBean wechat = new BalanceBean(BalanceBean.ID.WECHAT_PAY, "微信钱包", R.drawable.wechat_icon, R.drawable.wechat_bg, 0, "微信钱包余额");
                mDatas.add(wechat);
                break;
            case BalanceBean.ID.ALI_PAY:
                BalanceBean alipay = new BalanceBean(BalanceBean.ID.ALI_PAY, "支付宝", R.drawable.alipay, R.drawable.alipay_bg, 0, "支付宝余额");
                mDatas.add(alipay);
                break;
            default:break;
        }
   }
    public  void noticeAndSave(){
        adapter.notifyDataSetChanged();
        countTotal();
        SPUtils.saveBeantoSP(mDatas,getActivity());
    }

    public void countTotal(){
        Double count = 0.0;
        for(int i = 0; i < mDatas.size();i++){
            count+= mDatas.get(i).getAmount();
        }
        totalCountTV.setText(String.valueOf(count));
        SPUtils.put(getActivity(),"totalBalance",count);
    }
}
