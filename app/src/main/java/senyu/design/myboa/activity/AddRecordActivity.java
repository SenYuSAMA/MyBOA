package senyu.design.myboa.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import senyu.design.myboa.R;
import senyu.design.myboa.bean.BalanceBean;
import senyu.design.myboa.utils.SPUtils;

/**
 * @author Senyu
 */
public class AddRecordActivity extends AppCompatActivity implements View.OnClickListener{

    private TimePickerView mTimePickerView;
    private TimePickerBuilder mBuilder;
    private TextView mTypeTV;
    private TextView mDateTV;
    private TextView mItemTV;
    private EditText mCountET;
    private List<String> mOneList;
    private List<BalanceBean> mTwoList;
    private List<String> mThreeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_record);
        mDateTV = findViewById(R.id.select_date);
        mItemTV = findViewById(R.id.select_item);
        mTypeTV = findViewById(R.id.select_record_type);
        mCountET = findViewById(R.id.count_et);
        mDateTV.setOnClickListener(this);
        mItemTV.setOnClickListener(this);
        mCountET.setOnClickListener(this);
        mTypeTV.setOnClickListener(this);
    }

    private void initTimePicker() {
        mBuilder = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhh");
                    String text = simpleDateFormat.format(date);
                    mDateTV.setText(text);
            }
        });
        mTimePickerView = mBuilder.setCancelText("取消")
                .setSubmitText("确认")
                .setLabel("年","月","日","时","分","秒")
                .setType(new boolean[]{true,true,true,true,false,false})
                .build();
        mTimePickerView.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.select_date:
                initTimePicker();
                break;
            case R.id.select_item:
                initItemSelector();
                break;
            case R.id.count_et:

                break;
            case R.id.select_record_type:

                break;
            default:break;
        }
    }

    /**
     * 生成item选择器
     */
    private void initItemSelector() {
        mOneList = new ArrayList<>();
        mOneList.add("余额");
        mOneList.add("欠款");
        mTwoList = SPUtils.getBeanFromSP(this,SPUtils.BALANCE_BEAN_KEY,"");
        mThreeList = new ArrayList<>();
        mThreeList.add("吃");
        mThreeList.add("喝");
        OptionsPickerView  optionsPickerView = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
             /*   String tx = mOneList.get(options1)
                        + mTwoList.get(options2).getPickerViewText()
                        + mThreeList.get(options3);
                mItemTV.setText(tx);*/
            }
        }).build();
        optionsPickerView.setPicker(mOneList,mTwoList,mThreeList);
        optionsPickerView.show();
    }
}
