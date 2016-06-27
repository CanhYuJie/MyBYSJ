package cn.lyj.mybysj.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

@ContentView(R.layout.activity_reset_pwd)
public class ResetPwdActivity extends AppCompatActivity {
    private Context context;
    @ViewInject(R.id.modPassWord)
    private EditText modPassWord;
    @ViewInject(R.id.surePassWord)
    private EditText surePassWord;
    @ViewInject(R.id.modPWD)
    private Button modPWD;
    @ViewInject(R.id.currentUser)
    private TextView currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        x.view().inject(this);
        initView();
    }

    private void initView() {
        currentUser.setText(BysjApplication.getInstance().getCurrentUser());
    }

    @Event(value = R.id.modPWD)
    private void setModPWD(View view){
        String pwd = modPassWord.getText().toString();
        if (pwd.isEmpty()){
            modPassWord.setError("密码不能为空");
            modPassWord.requestFocus();
            return;
        }
        String surePwd = surePassWord.getText().toString();
        if (surePwd.isEmpty()){
            surePassWord.setError("确认密码不能为空");
            surePassWord.requestFocus();
            return;
        }
        if(!pwd.equals(surePwd)){
            Toast.makeText(this,"两次密码输入不一致，请重新输入",Toast.LENGTH_SHORT).show();
            modPassWord.setText("");
            surePassWord.setText("");
            return;
        }
        RequestParams params = new RequestParams(BysjApplication.INF_ROOT_SERVER);
        params.addParameter(I.KEY_REQUEST,I.REQUEST_UPDATEPASSWORD);
        params.addParameter(I.MODPASSWORD.OPTUSER,I.CLIENTUSER);
        params.addParameter(I.MODPASSWORD.PASSWORD,surePwd);
        params.addParameter(I.MODPASSWORD.MARK,BysjApplication.getInstance().getCurrentUser());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(result!=null){
                    ObjectMapper om = new ObjectMapper();
                    try {
                        Result result1 = om.readValue(result, Result.class);
                        if (result1.getMsg().equals("修改成功")){
                            BysjApplication.getInstance().setCurrentUser(null);
                            Toast.makeText(context,"密码修改成功，请重新登录",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(context,LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
