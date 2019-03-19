package senyu.design.myboa.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import senyu.design.myboa.activity.GuideActivity;
import senyu.design.myboa.R;

public class LastGuidePageFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_entry,null);
        v.findViewById(R.id.btn_entry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuideActivity activity = (GuideActivity) getActivity();
                activity.entryApp();
            }
        });
        return v;
    }
}