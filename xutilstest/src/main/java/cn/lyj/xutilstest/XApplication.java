package cn.lyj.xutilstest;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Administrator on 2016/6/17.
 */
public class XApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
