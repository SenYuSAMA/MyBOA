package senyu.design.myboa.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import senyu.design.myboa.CallBackInterface;
import senyu.design.myboa.R;
import senyu.design.myboa.activity.AddItemActivity;
import senyu.design.myboa.adapter.MyFragmentAdapter;
import senyu.design.myboa.bean.BalanceBean;
import senyu.design.myboa.bean.OweBean;
import senyu.design.myboa.bean.Record;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoneyFragment extends Fragment {
    private List<Fragment>  mList;
    private ViewPager mViewPager;
    private TextView mBalanceTv;
    private TextView mOweTv;
    private ImageView addItemIvBtn;
    private BalanceFragment mBalanceFragment;
    private OweFragment mOweFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_money, container, false);
        bindViews(view);
        initViewPager(view);
        return view;
    }

    private void bindViews(View view) {
        mBalanceTv = view.findViewById(R.id.balance_tv);
        mOweTv = view.findViewById(R.id.owe_tv);
        mBalanceTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
            }
        });
        mOweTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(1);
            }
        });
        addItemIvBtn = view.findViewById(R.id.add_item_iv_btn);
        addItemIvBtn = view.findViewById(R.id.add_item_iv_btn);
        addItemIvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddItemActivity.class);
                startActivity(intent);
                setCallBackListener();
            }
        });
    }

    private void setCallBackListener() {
        AddItemActivity.getInstance().setOnCallBackListener(new CallBackInterface() {
            @Override
            public void onCallBack(int layoutID) {
                mBalanceFragment.addItem(layoutID);
                mBalanceFragment.noticeAndSave();
            }

            @Override
            public void onOweCallBack(int layoutID) {
                mOweFragment.addItem(layoutID);
                mOweFragment.noticeAndSave();
            }
        });
    }

    private void initViewPager(final View view) {
        mViewPager = view.findViewById(R.id.view_pager);
        mList = new ArrayList<>();
        mBalanceFragment = new BalanceFragment();
        mOweFragment = new OweFragment();
        mList.add(mBalanceFragment);
        mList.add(mOweFragment);
        mViewPager.setAdapter(new MyFragmentAdapter(getFragmentManager(),mList));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0){
                    mBalanceTv.setTextColor(getResources().getColor(R.color.white));
                    mOweTv.setTextColor(getResources().getColor(R.color.navy));
                    mBalanceTv.setBackgroundColor(getResources().getColor(R.color.navy));
                    mOweTv.setBackgroundColor(getResources().getColor(R.color.white));
                }else if(position == 1){
                    mBalanceTv.setTextColor(getResources().getColor(R.color.navy));
                    mOweTv.setTextColor(getResources().getColor(R.color.white));
                    mBalanceTv.setBackgroundColor(getResources().getColor(R.color.white));
                    mOweTv.setBackgroundColor(getResources().getColor(R.color.navy));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    public MoneyFragment() {
        // Required empty public constructor
    }


    public void upDate(Record record) {
        String title = record.getByTitle();
        int balanceSize = mBalanceFragment.getmDatas().size();
        int oweSize = mOweFragment.getmDatas().size();
        int i = 0;
        int j = 0;
        while(i < balanceSize){
            if(title.equals(mBalanceFragment.getmDatas().get(i).getTitle())){
                mBalanceFragment.update(i,record.getCost(),record.isPlusOrNot());
                return;
            }
            i++;
        }

        while( j < oweSize){
            if(title.equals(mOweFragment.getmDatas().get(j).getTitle())){
                mOweFragment.update(j,record.getCost(),record.isPlusOrNot());
                return;
            }
            j++;
        }
    }
}
