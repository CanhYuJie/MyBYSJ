package cn.lyj.mybysj.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.hyphenate.chat.EMClient;

import cn.lyj.mybysj.R;

public class StuMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_main);
        Log.e("yujie", EMClient.getInstance().getCurrentUser().toString());
    }
}
