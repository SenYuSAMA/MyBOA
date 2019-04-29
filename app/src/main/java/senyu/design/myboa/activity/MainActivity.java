package senyu.design.myboa.activity;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TimePicker;

import com.bigkoo.pickerview.view.TimePickerView;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;
import me.majiajie.pagerbottomtabstrip.listener.SimpleTabItemSelectedListener;
import senyu.design.myboa.R;
import senyu.design.myboa.bean.Record;
import senyu.design.myboa.fragment.AddFragment;
import senyu.design.myboa.fragment.BalanceFragment;
import senyu.design.myboa.fragment.MoneyFragment;
import senyu.design.myboa.fragment.MoreFragment;
import senyu.design.myboa.fragment.OweFragment;
import senyu.design.myboa.fragment.TableFragment;
import senyu.design.myboa.fragment.TaxFragment;
import senyu.design.myboa.utils.SPUtils;

public class MainActivity extends FragmentActivity implements OweFragment.UpdateOwe, BalanceFragment.UpdateBalance {
    PageNavigationView tab;
    MoneyFragment mMoneyFragment;
    TableFragment mTableFragment;
    AddFragment mAddFragment;
    TaxFragment mTaxFragment;
    MoreFragment mMoreFragment;
    FragmentManager mFragmentManager;
    NavigationController mNavigationController;
    private static final int RESULT_CODE_ADDRECORD = 923;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean firstCome = (Boolean)SPUtils.get(this,SPUtils.FIRST_COME,true);
        //判断是否是第一次启动，是的话就打开引导页
        if(firstCome){
            SPUtils.put(this,SPUtils.FIRST_COME,false);
            Intent i = new Intent(MainActivity.this, GuideActivity.class);
            startActivity(i);
        }
        else{
            initUI();
            tab = findViewById(R.id.tab_host);
            mNavigationController = tab.material()
                    .addItem(R.drawable.money_unselected,R.drawable.money_selected,"资产",getResources().getColor(R.color.selectedBlue))
                    .addItem(R.drawable.table_unselected,R.drawable.table_selected,"报表",getResources().getColor(R.color.selectedBlue))
                    .addItem(R.drawable.add_selected,R.drawable.add_selected,"记账",getResources().getColor(R.color.red))
                    .addItem(R.drawable.tax_unselected,R.drawable.tax_selected,"个税",getResources().getColor(R.color.selectedBlue))
                    .addItem(R.drawable.more_unselected,R.drawable.more_selected,"更多",getResources().getColor(R.color.selectedBlue))
                    .dontTintIcon()
                    .build();
            //添加点击事件
            mNavigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
                @Override
                public void onSelected(int index, int old) {
                    switch (index){
                        case 0:
                            FragmentTransaction transaction1 = mFragmentManager.beginTransaction();
                            transaction1.show(mMoneyFragment);
                            transaction1.hide(mTaxFragment);
                            transaction1.hide(mTableFragment);
                            transaction1.hide(mMoreFragment);
                            transaction1.hide(mAddFragment);
                            transaction1.commit();
                            break;
                        case 1:
                            FragmentTransaction transaction2 = mFragmentManager.beginTransaction();
                            transaction2.hide(mMoneyFragment);
                            transaction2.hide(mTaxFragment);
                            transaction2.show(mTableFragment);
                            transaction2.hide(mMoreFragment);
                            transaction2.hide(mAddFragment);
                            transaction2.commit();
                            break;
                        case 2:
                            Intent intent = new Intent(MainActivity.this,AddRecordActivity.class);
                            startActivityForResult(intent,RESULT_CODE_ADDRECORD);
                            break;
                        case 3:
                            FragmentTransaction transaction4= mFragmentManager.beginTransaction();
                            transaction4.hide(mMoneyFragment);
                            transaction4.show(mTaxFragment);
                            transaction4.hide(mTableFragment);
                            transaction4.hide(mMoreFragment);
                            transaction4.hide(mAddFragment);
                            transaction4.commit();
                            break;
                        case 4:
                            FragmentTransaction transaction5= mFragmentManager.beginTransaction();
                            transaction5.hide(mMoneyFragment);
                            transaction5.hide(mTaxFragment);
                            transaction5.hide(mTableFragment);
                            transaction5.show(mMoreFragment);
                            transaction5.hide(mAddFragment);
                            transaction5.commit();
                            break;
                        default:
                            break;
                    }
                }

                @Override
                public void onRepeat(int index) {
                    if(index == 2) {
                        Intent intent = new Intent(MainActivity.this, AddRecordActivity.class);
                        startActivityForResult(intent,RESULT_CODE_ADDRECORD);
                    }
                }
            });
        }
    }


    private void initUI(){
        mMoneyFragment = new MoneyFragment();
        mTableFragment = new TableFragment();
        mAddFragment = new AddFragment();
        mTaxFragment = new TaxFragment();
        mMoreFragment = new MoreFragment();
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.main_content,mMoneyFragment);
        transaction.add(R.id.main_content,mTableFragment);
        transaction.add(R.id.main_content,mAddFragment);
        transaction.add(R.id.main_content,mTaxFragment);
        transaction.add(R.id.main_content,mMoreFragment);
        transaction.show(mMoneyFragment);
        transaction.hide(mTaxFragment);
        transaction.hide(mTableFragment);
        transaction.hide(mMoreFragment);
        transaction.hide(mAddFragment);
        transaction.commit();

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case RESULT_CODE_ADDRECORD:
                Record record = null;
                if (data != null) {
                    record = (Record)data.getSerializableExtra("data");
                    Toasty.success(this,record.toString()).show();
                    mTableFragment.upDate(record);
                    mMoneyFragment.upDate(record);
                }

                break;

                default:break;
        }
    }


    @Override
    public void updateBalacne(Double data) {
            mMoreFragment.updateBalance(data);
    }

    @Override
    public void updateOwe(Double data) {
            mMoreFragment.updateOwe(data);
    }
}
