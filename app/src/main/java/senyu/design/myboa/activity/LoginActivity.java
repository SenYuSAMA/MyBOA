package senyu.design.myboa.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import senyu.design.myboa.R;
import senyu.design.myboa.fragment.MoreFragment;
import senyu.design.myboa.utils.HttpUtil;
import senyu.design.myboa.utils.JSONUtil;
import senyu.design.myboa.utils.SPUtils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText userNameET;
    private EditText passwordET;
    private Button loginButton;
    private RelativeLayout signOutRL;
    private TextView registerTV;
    private LinearLayout etLL;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        bindViews();
        initState();
    }

    private void initState() {
        //如果登录状态，就要显示注销按钮，并且不显示用户名和密码
        if((boolean)SPUtils.get(this,"isLogin",false)){
        signOutRL.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.INVISIBLE);
        etLL.setVisibility(View.INVISIBLE);
        registerTV.setVisibility(View.INVISIBLE);
        }else{
            signOutRL.setVisibility(View.INVISIBLE);
            loginButton.setVisibility(View.VISIBLE);
            etLL.setVisibility(View.VISIBLE);
            registerTV.setVisibility(View.VISIBLE);
        }
    }

    private void bindViews() {
        userNameET = findViewById(R.id.username_et);
        passwordET = findViewById(R.id.password_et);
        loginButton = findViewById(R.id.login_btn);
        signOutRL = findViewById(R.id.sign_out_rl);
        registerTV = findViewById(R.id.register_tv);
        etLL = findViewById(R.id.et_ll);
        loginButton.setOnClickListener(this);
        signOutRL.setOnClickListener(this);
        registerTV.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn:
                login();
                break;
            case R.id.sign_out_rl:
                signOut();
                break;
            case R.id.register_tv:
                register();
                break;
            default:break;
        }
    }

    private void signOut() {
        SPUtils.remove(this,"username");
        SPUtils.put(this,"isLogin",false);
        SPUtils.remove(this,SPUtils.TOTAL_BALANCE);
        SPUtils.remove(this,SPUtils.TOTAL_OWE);
        SPUtils.remove(this,SPUtils.OWE_BEAN_KEY);
        SPUtils.remove(this,SPUtils.RECORD);
        SPUtils.remove(this,SPUtils.BALANCE_BEAN_KEY);
        //TODO 通知UI更新的逻辑
        setResult(MoreFragment.RESULT_LOGIN_CODE,null);
        finish();
    }

    private void register() {
        StringBuilder URL_REGISTER = new StringBuilder();
        if(userNameET.getText().length()==0||passwordET.getText().length()==0){
            Toasty.error(this,"请填写完整数据").show();
            return;
        }
        URL_REGISTER.append("http://47.100.206.82:8080/MyBOA/RegisterServlet?")
                .append("username="+userNameET.getText().toString())
                .append("&password="+passwordET.getText().toString());
        HttpUtil.sendOkHttpRequest(URL_REGISTER.toString(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toasty.success(LoginActivity.this,"网络出错，请重试").show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String log = response.body().string();
                if(JSONUtil.parseRegisterJSON(log)){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toasty.success(LoginActivity.this,"注册成功，请登录").show();
                        }
                    });
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toasty.error(LoginActivity.this,"用户名已存在，请重试").show();
                        }
                    });
                }
            }
        });
    }

    private void login() {
        StringBuilder URL_LOGIN = new StringBuilder();
        if(userNameET.getText().length()==0||passwordET.getText().length()==0){
            Toasty.error(this,"请填写完整数据").show();
            return;
        }
        URL_LOGIN.append("http://47.100.206.82:8080/MyBOA/LoginServlet?")
                .append("username="+userNameET.getText().toString())
                .append("&password="+passwordET.getText().toString());
        HttpUtil.sendOkHttpRequest(URL_LOGIN.toString(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toasty.success(LoginActivity.this,"网络出错，请重试").show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String log = response.body().string();
                //Login和Register的Json格式一样，所以可以用同一个解析方法
                if(JSONUtil.parseRegisterJSON(log)){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toasty.success(LoginActivity.this,"登陆成功！").show();
                            //成功以后要SP存储，
                            //TODO 在哪里读取sp
                            //存储用户名
                            SPUtils.put(LoginActivity.this,"username",userNameET.getText().toString());
                            //存储登录状态
                            SPUtils.put(LoginActivity.this,"isLogin",true);
                            Intent intent = new Intent();
                            intent.putExtra("username",userNameET.getText().toString());
                            setResult(MoreFragment.RESULT_LOGIN_CODE,intent);
                            finish();
                        }
                    });
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toasty.error(LoginActivity.this,"帐号密码验证出错，请重试").show();
                        }
                    });
                }
            }
        });
    }

    //todo 用sp标识是否登录，已登录的话，要记录用户名

}
