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
import android.widget.TextView;
import android.widget.Toast;

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
import cn.lyj.mybysj.bean.Student;
import cn.lyj.mybysj.bean.User;
import cn.lyj.mybysj.utils.Utils;

@ContentView(R.layout.activity_stu_login)
public class StuLoginActivity extends AppCompatActivity {
    @ViewInject(R.id.login_userName)
    private EditText login_userName;
    @ViewInject(R.id.login_passWord)
    private EditText login_passWord;
    @ViewInject(R.id.register)
    private TextView register;
    @ViewInject(R.id.userLogin)
    private TextView userLogin;
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

    @Event(type = View.OnClickListener.class,value = R.id.register)
    private void register(View view){
        startActivity(new Intent(context,RegisterActivity.class));
    }

    @Event(type = View.OnClickListener.class,value = R.id.userLogin)
    private void setUserLogin(View view){
        startActivity(new Intent(context,LoginActivity.class));
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
        String register_just = getIntent().getStringExtra("register_just");
        if(register_just!=null){
            login_userName.setText(register_just);
        }else {
            SharedPreferences sp = getSharedPreferences("StuloginName",MODE_PRIVATE);
            String loginName = sp.getString("StuloginName", null);
            if(loginName!=null){
                login_userName.setText(loginName);
            }
        }
    }

    @Event(type = View.OnClickListener.class,value = R.id.login_button)
    private void login(View view){
        Utils.logOut();
        if(validLogin()){
            pd.show();
            RequestParams requestParams = new RequestParams(BysjApplication.INF_ROOT_SERVER);
            requestParams.addParameter(I.KEY_REQUEST,I.REQUEST_GETONSTUINFO);
            requestParams.addParameter(I.GETONESTUINFO.OPTUSER,I.CLIENTUSER);
            requestParams.addParameter(I.GETONESTUINFO.UID,currentUserName);
            x.http().post(requestParams, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    ObjectMapper om = new ObjectMapper();
                    try {
                        Log.e("yujie",result);
                        Student student = om.readValue(result, Student.class);
                        Log.e("yujie",student.getName());
                        if(student!=null&(student.getRemark().equals(currentPassWord))){
                            Log.e("yujie","登录远端服务器成功");
                            EMClient.getInstance().login(currentUserName, currentPassWord, new EMCallBack() {
                                @Override
                                public void onSuccess() {
                                    Log.e("yujie","登录环信服务器成功");
                                    pd.dismiss();
                                    Log.e("yujie",currentUserName+"\n"+currentPassWord);
                                    getSharedPreferences("StuloginName",MODE_PRIVATE).edit().putString("StuloginName",currentUserName).commit();
                                    startActivity(new Intent(context,StuMainActivity.class));
                                }

                                @Override
                                public void onError(int i, String s) {

                                }

                                @Override
                                public void onProgress(int i, String s) {

                                }
                            });
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

                }
            });
        }
    }
}
