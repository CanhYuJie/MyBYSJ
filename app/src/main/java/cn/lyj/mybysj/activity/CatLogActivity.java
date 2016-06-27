package cn.lyj.mybysj.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
import cn.lyj.mybysj.bean.Opt_Log;

@ContentView(R.layout.activity_cat_log)
public class CatLogActivity extends AppCompatActivity {
    private Context context;
    @ViewInject(R.id.logListView)
    private ListView listView;
    private ArrayList<String> logs;
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        context = this;
        initData();
    }



    private void initData() {
        logs = new ArrayList<>();
        adapter = new ArrayAdapter<String>(context,R.layout.support_simple_spinner_dropdown_item,logs);
        RequestParams params = new RequestParams(BysjApplication.INF_ROOT_SERVER);
        params.addParameter(I.KEY_REQUEST,I.REQUEST_GETLOG);
        params.addParameter(I.GETLOG.OPTUSER,I.CLIENTUSER);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(result!=null){
                    ObjectMapper om = new ObjectMapper();
                    try {
                        Opt_Log[] opt_logs = om.readValue(result, Opt_Log[].class);
                        for(Opt_Log s:opt_logs){
                            logs.add(s.getOptUser()+"用户在"+s.getOptTime()+"进行了"+s.getOptType()+"操作");
                        }
                        listView.setAdapter(adapter);
                    } catch (IOException e) {


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
