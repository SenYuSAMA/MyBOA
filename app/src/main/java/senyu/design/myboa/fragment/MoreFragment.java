package senyu.design.myboa.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import senyu.design.myboa.R;
import senyu.design.myboa.activity.AboutMeActivity;
import senyu.design.myboa.activity.LoginActivity;
import senyu.design.myboa.bean.BalanceBean;
import senyu.design.myboa.bean.OweBean;
import senyu.design.myboa.bean.Record;
import senyu.design.myboa.utils.HttpUtil;
import senyu.design.myboa.utils.JSONUtil;
import senyu.design.myboa.utils.SPUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends Fragment implements View.OnClickListener {
    private static final String URL_DOWN_BALANCE = "http://47.100.206.82:8080/MyBOA/BalanceServlet?username=l1usy";
    private static final String URL_DOWN_OWE = "http://47.100.206.82:8080/MyBOA/OweServlet?username=l1usy";
    private static final String URL_DOWN_RECORD = "http://47.100.206.82:8080/MyBOA/RecordServlet?username=l1usy";
    public static final int RESULT_LOGIN_CODE = 41;

    private TextView mOweTV;
    private TextView mBalanceTV;
    private RelativeLayout mAboutMeRL;
    private RelativeLayout mDownloadRL;
    private RelativeLayout mLoginRl;
    private TextView mLoginTV;
    private TextView mLoginTipsTV;
    private RelativeLayout mUpdateRL;
    private OnDataRefresh mRefresher;

    public MoreFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        bindViews(view);
        initState();
        return view;
    }

    private void initState(){
        if((boolean)SPUtils.get(getActivity(),"isLogin",false)){
            //如果是登录状态，就要隐藏tips，显示用户名
            mLoginTV.setText((String)SPUtils.get(getActivity(),"username",""));
            mLoginTipsTV.setVisibility(View.INVISIBLE);
        }else{
            //如果是注销状态，要刷新UI
            mLoginTV.setText("立即登录");
            mLoginTipsTV.setVisibility(View.VISIBLE);
        }
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
        mAboutMeRL.setOnClickListener(this);
        mDownloadRL = view.findViewById(R.id.download_rl);
        mDownloadRL.setOnClickListener(this);
        mLoginRl = view.findViewById(R.id.login_rl);
        mLoginRl.setOnClickListener(this);
        mLoginTV = view.findViewById(R.id.login_tv);
        mLoginTipsTV = view.findViewById(R.id.login_tips);
        mUpdateRL = view.findViewById(R.id.update_rl);
        mUpdateRL.setOnClickListener(this);
    }

    public void updateBalance(Double data) {
        mBalanceTV.setText(data.toString());
    }

    public void updateOwe(Double data) {
        mOweTV.setText(data.toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.about_me_rl:
                final Intent intent = new Intent(getActivity(), AboutMeActivity.class);
                startActivity(intent);
                break;
            case R.id.download_rl:
               downloadRecord();
                break;
            case R.id.login_rl:
                Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(intent1,RESULT_LOGIN_CODE);
                break;
            case R.id.update_rl:
                uploadRecord();
                break;
            default:break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case RESULT_LOGIN_CODE:
                if(data!=null) {
                    String username = data.getStringExtra("username");
                    if (username != null && username.length() != 0) {
                        mLoginTV.setText(username);
                        mLoginTipsTV.setVisibility(View.INVISIBLE);
                    }
                }else{
                    mRefresher.clearUI();
                }
                break;
            default:break;
        }
    }

    private void uploadRecord(){
        String username = (String)SPUtils.get(getActivity(),"username","");
        if(username==null||username.length()==0){
            Toasty.error(getActivity(),"请先登录帐号，再进行数据绑定").show();
            return;
        }
        String recrodURL = "http://47.100.206.82:8080/MyBOA/RecordSaveServlet?username=";
        //发送记录的请求
        HttpUtil.sendOkHttpJSONPost(recrodURL + username, (String) SPUtils.get(getActivity(), SPUtils.RECORD, ""), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toasty.error(getActivity(),"网络出错，请重试").show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toasty.info(getActivity(),"消费记录上传完成，准备上传余额记录...").show();
                        uploadBalance();
                    }
                });
            }
        });
    }


    private  void uploadBalance(){
        String username = (String)SPUtils.get(getActivity(),"username","");
        if(username==null||username.length()==0){
            Toasty.error(getActivity(),"请先登录帐号，再进行数据绑定").show();
            return;
        }
        String balanceURL = "http://47.100.206.82:8080/MyBOA/BalanceSaveServlet?username=";
        String oweURL = "http://47.100.206.82:8080/MyBOA/OweSaveServlet?username=";
        //发送记录的请求
        HttpUtil.sendOkHttpJSONPost(balanceURL + username, (String) SPUtils.get(getActivity(), SPUtils.BALANCE_BEAN_KEY, ""), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toasty.error(getActivity(),"网络出错，请重试").show();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toasty.info(getActivity(),"余额记录上传完成，准备上传欠款记录...").show();
                        uploadOwe();
                    }
                });
            }
        });
    }

    private  void uploadOwe(){
        String username = (String)SPUtils.get(getActivity(),"username","");
        if(username==null||username.length()==0){
            Toasty.error(getActivity(),"请先登录帐号，再进行数据绑定").show();
            return;
        }
        String oweURL = "http://47.100.206.82:8080/MyBOA/OweSaveServlet?username=";
        //发送记录的请求
        HttpUtil.sendOkHttpJSONPost(oweURL + username, (String) SPUtils.get(getActivity(), SPUtils.OWE_BEAN_KEY, ""), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toasty.error(getActivity(),"网络出错，请重试").show();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toasty.success(getActivity(),"服务器数据已与本地同步").show();
                    }
                });
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        initState();
    }
    public interface OnDataRefresh{
        public void refreshUI();
        public void clearUI();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mRefresher = (OnDataRefresh) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnDataRefresh");
        }
    }

    private void downloadRecord(){
        String username = (String)SPUtils.get(getActivity(),"username","");
        if(username==null||username.length()==0){
            Toasty.error(getActivity(),"请先登录帐号，再进行数据同步").show();
            return;
        }
        String recordUrl = "http://47.100.206.82:8080/MyBOA/RecordServlet?username=";
        HttpUtil.sendOkHttpRequest(recordUrl + username, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toasty.error(getActivity(),"网络请求出错，请重试").show();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final List<Record> datas = JSONUtil.parseRecordJSON(response.body().string());
                SPUtils.saveRecordToSP(datas,getActivity());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toasty.info(getActivity(),"消费记录同步完成，准备同步余额记录...").show();
                        downloadBalance();
                    }
                });
            }
        });
    }


    private void downloadBalance(){
        String username = (String)SPUtils.get(getActivity(),"username","");
        if(username==null||username.length()==0){
            Toasty.error(getActivity(),"请先登录帐号，再进行数据同步").show();
            return;
        }
        String balanceUrl = "http://47.100.206.82:8080/MyBOA/BalanceServlet?username=";
        HttpUtil.sendOkHttpRequest(balanceUrl + username, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toasty.error(getActivity(),"网络请求出错，请重试").show();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final List<BalanceBean> datas = JSONUtil.parseBalanceJSON(response.body().string());
                SPUtils.saveBeantoSP(datas,getActivity());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toasty.info(getActivity(),"余额记录同步完成，准备同步欠款记录...").show();
                        downloadOwe();
                    }
                });
            }
        });
    }


    public void downloadOwe(){
        String username = (String)SPUtils.get(getActivity(),"username","");
        if(username==null||username.length()==0){
            Toasty.error(getActivity(),"请先登录帐号，再进行数据同步").show();
            return;
        }
        String oweUrl = "http://47.100.206.82:8080/MyBOA/OweServlet?username=";
        HttpUtil.sendOkHttpRequest(oweUrl + username, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toasty.error(getActivity(),"网络请求出错，请重试").show();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final List<OweBean> datas = JSONUtil.parseOweJSON(response.body().string());
                SPUtils.saveOweToSP(datas,getActivity());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toasty.success(getActivity(),"全部记录同步完成，更新界面").show();
                        mRefresher.refreshUI();
                    }
                });

            }
        });
    }


    public void clearUI(){
        mLoginTV.setText("立即登录");
        mLoginTipsTV.setVisibility(View.VISIBLE);
    }

}
