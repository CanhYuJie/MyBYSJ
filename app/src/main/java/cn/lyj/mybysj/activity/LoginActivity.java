package cn.lyj.mybysj.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import org.codehaus.jackson.map.ObjectMapper;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;

import cn.lyj.mybysj.BysjApplication;
import cn.lyj.mybysj.I;
import cn.lyj.mybysj.R;
import cn.lyj.mybysj.bean.Result;
import cn.lyj.mybysj.bean.User;
import cn.lyj.mybysj.utils.Utils;
@ContentView(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity  {
    @ViewInject(R.id.login_userName)
    private EditText login_userName;
    @ViewInject(R.id.login_passWord)
    private EditText login_passWord;
    private String currentUserName;
    private String currentPassWord;
    private Context context;
    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        context = this;
        initUserName();
        initPd();
    }

    private void initPd() {
        pd = new ProgressDialog(context);
        pd.setMessage("正在登陆");
    }

    private boolean validLogin() {
        currentUserName = login_userName.getText().toString();
        currentPassWord = login_passWord.getText().toString();
        if(currentUserName.isEmpty()){
            login_userName.setError("用户名不能为空");
            login_userName.requestFocus();
            return false;
        }
        if(currentPassWord.isEmpty()){
            login_passWord.setError("密码不能为空");
            login_passWord.requestFocus();
            return false;
        }
        return true;
    }

    private void initUserName() {
        SharedPreferences sp = getSharedPreferences("loginName",MODE_PRIVATE);
        String loginName = sp.getString("loginName", null);
        if(loginName!=null){
            login_userName.setText(loginName);
        }
    }

    @Event(type = View.OnClickListener.class,value = R.id.login_button)
    private void login(View view){
        if(validLogin()){
            pd.show();
            RequestParams requestParams = new RequestParams(BysjApplication.INF_ROOT_SERVER);
            requestParams.addParameter(I.KEY_REQUEST,I.REQUEST_LOGIN);
            requestParams.addParameter(I.USERLOGIN.USERNAME,currentUserName);
            requestParams.addParameter(I.USERLOGIN.PASSWORD,currentPassWord);
            x.http().post(requestParams, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    ObjectMapper om = new ObjectMapper();
                    try {
                        User user = om.readValue(result,User.class);
                        if(user!=null){
                            SharedPreferences sp = getSharedPreferences("loginName",MODE_PRIVATE);
                            sp.edit().putString("loginName",currentUserName).commit();
                            sp.edit().putString("loginPwd",currentPassWord).commit();
                            startActivity(new Intent(context,MainActivity.class));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {

                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {
                    pd.dismiss();
                }
            });
        }
    }


}
