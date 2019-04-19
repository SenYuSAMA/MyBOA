package senyu.design.myboa.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import senyu.design.myboa.R;
import senyu.design.myboa.activity.AboutMeActivity;
import senyu.design.myboa.utils.SPUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends Fragment {
    private TextView mOweTV;
    private TextView mBalanceTV;
    private RelativeLayout mAboutMeRL;



    public MoreFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        bindViews(view);
        return view;
    }

    private void bindViews(View view) {
        mOweTV = view.findViewById(R.id.total_owe_tv);
        mBalanceTV = view.findViewById(R.id.total_balance_tv);
        String oweStr = (String) SPUtils.get(getActivity(),SPUtils.TOTAL_OWE,"0.00");
        String balanceStr = (String) SPUtils.get(getActivity(),SPUtils.TOTAL_BALANCE,"0.00");
        if(oweStr != null){
            mOweTV.setText(oweStr);
        }else {
            oweStr = "0.00";
            SPUtils.put(getActivity(),SPUtils.TOTAL_OWE,oweStr);
            mOweTV.setText(oweStr);
        }
        if(balanceStr != null){
            mBalanceTV.setText(balanceStr);
        }else{
            balanceStr = "0.00";
            SPUtils.put(getActivity(),SPUtils.TOTAL_BALANCE,balanceStr);
            mBalanceTV.setText(balanceStr);
        }
        mAboutMeRL = view.findViewById(R.id.about_me_rl);
        mAboutMeRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AboutMeActivity.class);
                startActivity(intent);
            }
        });
    }

    public void updateBalance(Double data) {
        mBalanceTV.setText(data.toString());
    }

    public void updateOwe(Double data) {
        mOweTV.setText(data.toString());
    }
}
