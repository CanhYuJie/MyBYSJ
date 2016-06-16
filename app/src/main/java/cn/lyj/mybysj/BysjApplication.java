package cn.lyj.mybysj;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Administrator on 2016/6/16.
 */
public class BysjApplication extends Application{
    public static final String INF_ROOT_SERVER = "http://115.28.2.61:8080/StudentManagerServer/";
    public static final String CHAT_ROOT_SERVER = "http://115.28.2.61:8080/SuperWeChatServer/";
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
