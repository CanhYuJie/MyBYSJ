package cn.lyj.mybysj.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import cn.lyj.mybysj.adapter.FloorAdapter;
import cn.lyj.mybysj.bean.Floor;
import cn.lyj.mybysj.bean.Result;
import cn.lyj.mybysj.utils.Utils;

@ContentView(R.layout.fragment_floor_manager)
public class FloorManagerFragment extends Fragment {
    private Context context;
    private ArrayList<Floor> floors;
    private StaggeredGridLayoutManager manager;
    @ViewInject(R.id.floorToolBar)
    private Toolbar floorToolBar;
    @ViewInject(R.id.floorRefresh)
    private SwipeRefreshLayout refreshLayout;
    @ViewInject(R.id.floorRecycleView)
    private RecyclerView recyclerView;
    private FloorAdapter adapter;
    private AlertDialog dialog;
    public FloorManagerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = x.view().inject(this, inflater, container);
        context = getActivity();
        initData();
        initToolBar();
        initRefresh();
        initAdapter();
        return view;
    }

    private void initRefresh() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RequestParams params = new RequestParams(BysjApplication.INF_ROOT_SERVER);
                params.addParameter(I.KEY_REQUEST,I.REQUEST_GETFLOORINFO);
                params.addParameter(I.GETFLOORINFO.OPTUSER,I.CLIENTUSER);
                x.http().get(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        if (result!=null) {
                            ObjectMapper om = new ObjectMapper();
                            try {
                                floors = Utils.array2List(om.readValue(result, Floor[].class));
                                adapter.refresh(floors);
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
        });
    }

    private void initToolBar() {
        floorToolBar.setTitle("查看楼宇信息");
        floorToolBar.setNavigationIcon(R.drawable.floormanager);
        floorToolBar.inflateMenu(R.menu.floor_menu);
        floorToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.add_floor:
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        View view = LayoutInflater.from(context).inflate(R.layout.sea_stu_dialog, null);
                        final EditText floorTxt = (EditText) view.findViewById(R.id.sea_stu_uid);
                        floorTxt.setHint("请输入楼宇名称");
                        Button btn = (Button) view.findViewById(R.id.sea_stu_button);
                        btn.setText("点击添加");
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String floorName = floorTxt.getText().toString();
                                if(floorName.isEmpty()){
                                    floorTxt.setError("不能为空");
                                    floorTxt.requestFocus();
                                    return;
                                }
                                RequestParams params = new RequestParams(BysjApplication.INF_ROOT_SERVER);
                                params.addParameter(I.KEY_REQUEST,I.REQUEST_ADDFLOORINTO);
                                params.addParameter(I.ADDFLOORINFO.FLOORNAME,floorName);
                                params.addParameter(I.ADDFLOORINFO.OPTUSER,I.CLIENTUSER);
                                x.http().post(params, new Callback.CommonCallback<String>() {
                                    @Override
                                    public void onSuccess(String result) {
                                        if(result!=null){
                                            ObjectMapper om = new ObjectMapper();
                                            try {
                                                Result value = om.readValue(result, Result.class);
                                                if(value.getMsg().equals("添加成功")){
                                                    dialog.dismiss();
                                                    Toast.makeText(context,"添加成功",Toast.LENGTH_SHORT).show();
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
                        });
                        builder.setView(view).create();
                        dialog = builder.show();
                        break;
                }
                return false;
            }
        });
    }

    private void initAdapter() {
        manager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new FloorAdapter(context,floors);
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        floors = BysjApplication.getInstance().getFloors();
    }

}
