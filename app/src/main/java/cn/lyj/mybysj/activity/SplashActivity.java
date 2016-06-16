package cn.lyj.mybysj.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.lyj.mybysj.R;
public class SplashActivity extends AppCompatActivity {
    private final String TAG = SplashActivity.class.getName();
    private TextView versionText;
    private RelativeLayout rootLayout;
    private boolean isFirstIn = false;
    private static final int sleepTime = 2000;
    private static final int GO_HOME = 1000;
    private static final int GO_GUIDE = 1001;
    private static final int GO_LOGIN = 1002;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case GO_HOME:
                    goHome();
                    break;
                case GO_GUIDE:
                    goGuide();
                    break;
                case GO_LOGIN:
                    goLogin();
                    break;
            }
        }
    };

    private void goLogin() {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void goGuide() {
        Intent intent = new Intent(this,GuideActivity.class);
        startActivity(intent);
        finish();
    }

    private void goHome() {
//        Intent intent = new Intent(this,MainActivity.class);
//        startActivity(intent);
//        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        initView();

        versionText.setText(getVersion());
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.3f, 1.0f);
        alphaAnimation.setDuration(3000);
        rootLayout.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                SharedPreferences sp = getSharedPreferences("firstIn",MODE_PRIVATE);
                isFirstIn = sp.getBoolean("firstIn",true);
                if(isFirstIn){
                    mHandler.sendEmptyMessage(GO_GUIDE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("firstIn",false);
                    editor.commit();
                }else {
                    //判断是否已经登录，如果登录了就进入主界面，没有就进入登录页面
                    Log.e("yujie","不是第一次登录");
                }
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void initView() {
        versionText = (TextView) findViewById(R.id.appVersion);
        rootLayout = (RelativeLayout) findViewById(R.id.rootLayout);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * 获取当前应用版本号
     * @return
     */
    private String getVersion() {
        String st = getResources().getString(R.string.version_number_is_wrong);
        PackageManager manager = getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(getPackageName(),0);
            String versionName = info.versionName;
            return  versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return st;
        }
    }
}
