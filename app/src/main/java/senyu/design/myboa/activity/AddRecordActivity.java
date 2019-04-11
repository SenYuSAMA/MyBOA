package senyu.design.myboa.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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
import senyu.design.myboa.bean.FinanceBean;
import senyu.design.myboa.bean.Record;
import senyu.design.myboa.utils.SPUtils;

/**
 * @author Senyu
 */
public class AddRecordActivity extends AppCompatActivity implements View.OnClickListener{

    private TimePickerView mTimePickerView;
    private TimePickerBuilder mBuilder;
    private TextView mDateTV;
    private TextView mItemTV;
    private EditText mCountET;
    private List<String> mOneList;
    private List<FinanceBean> mTwoList;
    private List<String> mThreeList;
    private Record mRecord;
    private ImageView closeIV;
    private ImageView checkIV;
    private static final int RESULT_CODE_ADDRECORD = 923;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_record);
        mDateTV = findViewById(R.id.select_date);
        mItemTV = findViewById(R.id.select_item);
        mCountET = findViewById(R.id.count_et);
        checkIV = findViewById(R.id.iv_check);
        closeIV = findViewById(R.id.iv_close);
        mRecord = new Record();
        mDateTV.setOnClickListener(this);
        mItemTV.setOnClickListener(this);
        mCountET.setOnClickListener(this);
        checkIV.setOnClickListener(this);
        closeIV.setOnClickListener(this);
        mCountET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED || actionId ==EditorInfo.IME_ACTION_SEND
                        || (event != null && event.getKeyCode() ==KeyEvent.KEYCODE_ENTER)) {

                    InputMethodManager inputMethodManager = (InputMethodManager) AddRecordActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return true;
            }
        });
    }

    private void initTimePicker() {
        mBuilder = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhh");
                    String text = simpleDateFormat.format(date);
                    mRecord.setDate(text);
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
            case R.id.iv_check:
                String str = mCountET.getText().toString();

                if (str.length() != 0 && mRecord.getByTitle()!=null && mCountET.getText()!=null && mRecord.getUsage()!=null ){
                    mRecord.setCost(Double.valueOf(str));
                    Intent intent = new Intent();
                    intent.putExtra("data",mRecord);
                    setResult(RESULT_CODE_ADDRECORD,intent);
                    finish();
                    //todo 传这个bean回去记录那边 然后自我关闭
                }else{
                    Toasty.error(this,"有东西没填完，请重试！").show();
                }

                break;

            case R.id.iv_close:
                    finish();
                break;

            default:break;
        }
    }

    /**
     * 生成item选择器
     */
    private void initItemSelector() {
        mOneList = new ArrayList<>();
        mOneList.add(getString(R.string.cost));
        mOneList.add(getString(R.string.income));
        mTwoList = SPUtils.getFinanceBean(this);
        mThreeList = new ArrayList<>();
        mThreeList.add(getString(R.string.eat));
        mThreeList.add(getString(R.string.drink));

        OptionsPickerView pvNoLinkOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                mRecord.setByTitle(mTwoList.get(options2).getPickerViewText());
                mRecord.setPlusOrNot(mOneList.get(options1).equals(R.string.income));
                mRecord.setUsage(mThreeList.get(options3));
                String str = mOneList.get(options1) +  mTwoList.get(options2).getPickerViewText() + mThreeList.get(options3);
                mItemTV.setText(str);
                Toasty.success(AddRecordActivity.this,str).show();
            }
        }).build();
        pvNoLinkOptions.setNPicker(mOneList,mTwoList,mThreeList);
        pvNoLinkOptions.show();
    }
}
