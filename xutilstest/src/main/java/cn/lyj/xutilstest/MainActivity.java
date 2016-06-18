package cn.lyj.xutilstest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    @ViewInject(R.id.iv)
    private ImageView iv;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        context = this;
    }
    @Event(type = View.OnClickListener.class,value = R.id.iv)
    private void onclick(View v){
        String url = "http://115.28.2.61:8080/StudentManagerServer/Server?";
        RequestParams requestParams = new RequestParams(url);
        requestParams.addParameter("request","login");
        requestParams.addParameter("userName","lyj");
        requestParams.addParameter("passWord","123456");
        Callback.Cancelable yujie = x.http().post(requestParams, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Log.e("yujie", result);
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
