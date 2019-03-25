package senyu.design.myboa.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.List;

import senyu.design.myboa.CallBackInterface;
import senyu.design.myboa.R;
import senyu.design.myboa.adapter.MoneyRecyclerViewAdapter;
import senyu.design.myboa.bean.BalanceBean;
import senyu.design.myboa.bean.OweBean;
import senyu.design.myboa.fragment.BalanceFragment;

public class AddItemActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout cashLL;
    private LinearLayout investLL;
    private LinearLayout bankLL;
    private LinearLayout lentLL;
    private LinearLayout wechatLL;
    private LinearLayout alipayLL;
    private RelativeLayout backRL;
    private LinearLayout huabeill;
    private LinearLayout jdll;
    private LinearLayout creditOwell;
    private LinearLayout borrowll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_item);
        bindViews();
    }
    private void bindViews(){
        cashLL = findViewById(R.id.cash_ll);
        investLL = findViewById(R.id.invest_ll);
        bankLL = findViewById(R.id.bank_ll);
        lentLL = findViewById(R.id.lent_ll);
        wechatLL = findViewById(R.id.wechat_ll);
        alipayLL = findViewById(R.id.alipay_ll);
        backRL = findViewById(R.id.back_rl);
        huabeill = findViewById(R.id.huabei_ll);
        jdll = findViewById(R.id.jd_ll);
        creditOwell = findViewById(R.id.credit_owe_ll);
        borrowll = findViewById(R.id.borrow_ll);
        huabeill.setOnClickListener(this);
        jdll.setOnClickListener(this);
        creditOwell.setOnClickListener(this);
        borrowll.setOnClickListener(this);
        cashLL.setOnClickListener(this);
        investLL.setOnClickListener(this);
        bankLL.setOnClickListener(this);
        lentLL.setOnClickListener(this);
        wechatLL.setOnClickListener(this);
        alipayLL.setOnClickListener(this);
        backRL.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cash_ll:
                instance.listener.onCallBack(BalanceBean.ID.CASH);
                if(instance!=null){
                    instance.finish();
                    instance = null;
                }
                finish();
                break;
            case R.id.invest_ll:
                instance.listener.onCallBack(BalanceBean.ID.INVEST);
                if(instance!=null){
                    instance.finish();
                    instance = null;
                }
                finish();
                break;
            case R.id.lent_ll:
                instance.listener.onCallBack(BalanceBean.ID.LENT);
                if(instance!=null){
                    instance.finish();
                    instance = null;
                }
                finish();
                break;
            case R.id.wechat_ll:
                instance.listener.onCallBack(BalanceBean.ID.WECHAT_PAY);
                if(instance!=null){
                    instance.finish();
                    instance = null;
                }
                finish();
                break;
            case R.id.alipay_ll:
                instance.listener.onCallBack(BalanceBean.ID.ALI_PAY);
                if(instance!=null){
                    instance.finish();
                    instance = null;
                }
                finish();
                break;
            case R.id.bank_ll:
                instance.listener.onCallBack(BalanceBean.ID.CREDIT_CARD);
                if(instance!=null){
                    instance.finish();
                    instance = null;
                }
                finish();
                break;
            case R.id.huabei_ll:
                instance.listener.onOweCallBack(OweBean.ID.HUA_BEI);
                if(instance!=null){
                    instance.finish();
                    instance = null;
                }
                finish();
                break;
            case R.id.jd_ll:
                instance.listener.onOweCallBack(OweBean.ID.JD_BAITIAO);
                if(instance!=null){
                    instance.finish();
                    instance = null;
                }
                finish();
                break;
            case R.id.credit_owe_ll:
                instance.listener.onOweCallBack(OweBean.ID.CREDIT_OWE);
                if(instance!=null){
                    instance.finish();
                    instance = null;
                }
                finish();
                break;
            case R.id.borrow_ll:
                instance.listener.onOweCallBack(OweBean.ID.OWE_BILL);
                if(instance!=null){
                    instance.finish();
                    instance = null;
                }
                finish();
                break;
            case R.id.back_rl:
                if(instance!=null){
                    instance.finish();
                    instance = null;
                }
                finish();
                break;
            default:break;
        }
    }

    public static AddItemActivity instance;
    public static AddItemActivity getInstance(){
        if(instance == null){
            instance = new AddItemActivity();
        }
        return instance;
    }

    CallBackInterface listener;
    public void setOnCallBackListener(CallBackInterface callBacker){
        this.listener = callBacker;
    }

}
