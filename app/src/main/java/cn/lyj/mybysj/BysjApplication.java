package cn.lyj.mybysj;

import android.app.Application;
import android.content.Context;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

import org.xutils.x;

/**
 * Created by Administrator on 2016/6/16.
 */
public class BysjApplication extends Application{
    public static final String INF_ROOT_SERVER = "http://115.28.2.61:8080/StudentManagerServer/Server?";
    private static BysjApplication instance;
    Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        EMOptions options = new EMOptions();
        context = this;
        instance = this;
        x.Ext.init(this);
// 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        EMClient.getInstance().init(context, options);
//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
    }

    public static BysjApplication getInstance(){
        return instance;
    }

    private String LoginName;
    private String loginPwd;

    public String getLoginName() {
        return LoginName;
    }

    public void setLoginName(String loginName) {
        LoginName = loginName;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }
}
