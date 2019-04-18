package senyu.design.myboa.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

import es.dmoral.toasty.Toasty;
import senyu.design.myboa.R;
import senyu.design.myboa.utils.TaxUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaxFragment extends Fragment implements View.OnClickListener {
    //todo 搞定个税界面及逻辑
    private EditText incomeET;
    private EditText insuranceET;
    private EditText houseET;
    private EditText sonET;
    private EditText parentET;
    private EditText educationET;
    private EditText rentET;
    private Button computeBtn;
    private Button resetBtn;
    private TextView resultTV;
    private ScrollView scrollView;
    public TaxFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tax, container, false);
        bindViews(view);
        return view;
    }

    private void bindViews(View view) {
        incomeET = view.findViewById(R.id.income_et);
        insuranceET = view.findViewById(R.id.social_insurance_et);
        houseET = view.findViewById(R.id.house_insurance_et);
        sonET = view.findViewById(R.id.son_education_et);
        parentET = view.findViewById(R.id.raise_old_et);
        educationET = view.findViewById(R.id.self_education_et);
        rentET = view.findViewById(R.id.house_rent_et);
        resultTV = view.findViewById(R.id.result_tv);
        computeBtn = view.findViewById(R.id.computer_btn);
        scrollView = view.findViewById(R.id.scrollView);
        resetBtn = view.findViewById(R.id.reset_btn);
        computeBtn.setOnClickListener(this);
        resetBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.computer_btn:
                computeTax();
                break;
            case R.id.reset_btn:
                reset();
                break;
            default:break;
        }
    }

    private void reset() {
        incomeET.setText("");
        insuranceET.setText("");
        houseET.setText("");
        sonET.setText("");
        parentET.setText("");
        educationET.setText("");
        rentET.setText("");
        resultTV.setVisibility(View.GONE);
    }

    private void computeTax() {

        String income = incomeET.getText().toString().trim();
        if(TextUtils.isEmpty(income)){
            Toasty.error(getActivity(),"必须输入薪酬").show();
            return;
        }
        String insurance = insuranceET.getText().toString().trim();
        if(TextUtils.isEmpty(insurance)){
            Toasty.error(getActivity(),"必须输入社保").show();
            return;
        }
        String house = houseET.getText().toString().trim();
        String son = sonET.getText().toString().trim();
        String education = educationET.getText().toString().trim();
        String rent = rentET.getText().toString().trim();
        String parent = parentET.getText().toString().trim();
        resultTV.setVisibility(View.VISIBLE);
        resultTV.setText(TaxUtils.compute(income,insurance,house,son,education,rent,parent).toString());
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });

    }
}
