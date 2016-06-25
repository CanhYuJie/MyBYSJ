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

import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMClient;

import org.codehaus.jackson.map.ObjectMapper;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;

import cn.lyj.mybysj.BysjApplication;
import cn.lyj.mybysj.I;
import cn.lyj.mybysj.R;
import cn.lyj.mybysj.bean.BedRoom;
import cn.lyj.mybysj.bean.ClassObj;
import cn.lyj.mybysj.bean.Department;
import cn.lyj.mybysj.bean.Floor;
import cn.lyj.mybysj.utils.Utils;

@ContentView(R.layout.activity_splash)
public class SplashActivity extends AppCompatActivity {
    private final String TAG = SplashActivity.class.getName();
    @ViewInject(R.id.appVersion)
    private TextView versionText;
    @ViewInject(R.id.rootLayout)
    private RelativeLayout rootLayout;
    private ArrayList<Department> departments;
    private ArrayList<ClassObj> classObjs;
    private ArrayList<BedRoom> bedRooms;
    private ArrayList<Floor> floors;
    private boolean isFirstIn = false;
    private static final int GO_HOME = 1000;
    private static final int GO_GUIDE = 1001;
    private static final int GO_LOGIN = 1002;
    private final int CODE_DEPARTMENT = 100;
    private final int CODE_CLASS = 101;
    private final int CODE_BEDROOM = 102;
    private final int CODE_FLOOR = 103;
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
        Intent intent = new Intent(this,StuLoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void goGuide() {
        Intent intent = new Intent(this,GuideActivity.class);
        startActivity(intent);
        finish();
    }

    private void goHome() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        x.view().inject(this);
        initData();
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
                    String loginName = sp.getString("loginName", null);
                    String loginPwd = sp.getString("loginPwd", null);
                    if(loginName!=null&loginPwd!=null){
                        BysjApplication.getInstance().setLoginName(loginName);
                        BysjApplication.getInstance().setLoginPwd(loginPwd);
                        mHandler.sendEmptyMessage(GO_HOME);
                    }else {
                        mHandler.sendEmptyMessage(GO_LOGIN);
                    }
                }
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        RequestParams departmentParams = getRequestParams(CODE_DEPARTMENT);
        setData(departmentParams,CODE_DEPARTMENT);
        RequestParams classParams = getRequestParams(CODE_CLASS);
        setData(classParams,CODE_CLASS);
        RequestParams bedroomParams = getRequestParams(CODE_BEDROOM);
        setData(bedroomParams,CODE_BEDROOM);
        RequestParams floorParams = getRequestParams(CODE_FLOOR);
        setData(floorParams,CODE_FLOOR);
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


    private void setData(RequestParams params, final int code){
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(result!=null){
                    ObjectMapper om = new ObjectMapper();
                    switch (code){
                        case CODE_DEPARTMENT:
                            try {
                                Department[] department = om.readValue(result, Department[].class);
                                departments = Utils.array2List(department);
                                Log.e("yujie",departments.toString());
                                BysjApplication.getInstance().setDepartments(departments);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case CODE_CLASS:
                            try {
                                ClassObj[] classObj = om.readValue(result, ClassObj[].class);
                                classObjs = Utils.array2List(classObj);
                                Log.e("yujie",classObjs.toString());
                                BysjApplication.getInstance().setClassObjs(classObjs);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case CODE_BEDROOM:
                            try {
                                BedRoom[] bedRoom = om.readValue(result, BedRoom[].class);
                                bedRooms = Utils.array2List(bedRoom);
                                Log.e("yujie",bedRooms.toString());
                                BysjApplication.getInstance().setBedRooms(bedRooms);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case CODE_FLOOR:
                            try {
                                Floor[] floor = om.readValue(result, Floor[].class);
                                floors = Utils.array2List(floor);
                                Log.e("yujie",floors.toString());
                                BysjApplication.getInstance().setFloors(floors);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
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

    private RequestParams getRequestParams(int paramsCode){
        RequestParams params = new RequestParams(BysjApplication.INF_ROOT_SERVER);
        switch (paramsCode){
            case CODE_DEPARTMENT:
                params.addParameter(I.KEY_REQUEST,I.REQUEST_GETDEPARTMENT);
                params.addParameter(I.GETDEPARTMENT.OPTUSER, I.CLIENTUSER);
                break;
            case CODE_CLASS:
                params.addParameter(I.KEY_REQUEST,I.REQUEST_GETCLASS);
                params.addParameter(I.GETCLASS.OPTUSER,I.CLIENTUSER);
                break;
            case CODE_BEDROOM:
                params.addParameter(I.KEY_REQUEST,I.REQUEST_GETBEDROOMINFO);
                params.addParameter(I.GETBEDROOMINFO.OPTUSER,I.CLIENTUSER);
                break;
            case CODE_FLOOR:
                params.addParameter(I.KEY_REQUEST,I.REQUEST_GETFLOORINFO);
                params.addParameter(I.GETFLOORINFO.OPTUSER,I.CLIENTUSER);
                break;
        }
        return params;
    }
}
