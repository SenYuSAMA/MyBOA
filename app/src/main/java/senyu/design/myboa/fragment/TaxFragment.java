package senyu.design.myboa.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import senyu.design.myboa.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaxFragment extends Fragment {
    //todo 搞定个税界面及逻辑

    public TaxFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tax, container, false);
    }

}
