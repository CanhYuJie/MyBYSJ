package cn.lyj.mybysj.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import cn.lyj.mybysj.adapter.RoomAdapter;
import cn.lyj.mybysj.bean.BedRoom;
import cn.lyj.mybysj.bean.Floor;
import cn.lyj.mybysj.bean.Result;
import cn.lyj.mybysj.utils.Utils;

@ContentView(R.layout.fragment_room_manager)
public class RoomManagerFragment extends Fragment {
    private Context context;
    private ArrayList<BedRoom> bedRooms;
    private StaggeredGridLayoutManager manager;
    @ViewInject(R.id.roomToolBar)
    private Toolbar toolbar;
    @ViewInject(R.id.roomRefresh)
    private SwipeRefreshLayout refreshLayout;
    @ViewInject(R.id.roomRecycleView)
    private RecyclerView recyclerView;
    private AlertDialog dialog;
    private RoomAdapter adapter;
    public RoomManagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = x.view().inject(this, inflater, container);
        context = getActivity();
        initData();
        initToolbar();
        initAdapter();
        initRefresh();
        return view;
    }

    private void initRefresh() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getBedRoomInfo();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void getBedRoomInfo() {
        RequestParams params = new RequestParams(BysjApplication.INF_ROOT_SERVER);
        params.addParameter(I.KEY_REQUEST,I.REQUEST_GETBEDROOMINFO);
        params.addParameter(I.GETBEDROOMINFO.OPTUSER,I.CLIENTUSER);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(result!=null){
                    ObjectMapper om = new ObjectMapper();
                    try {
                        BedRoom[] bedRooms = om.readValue(result, BedRoom[].class);
                        ArrayList<BedRoom> bedRoomArrayList = Utils.array2List(bedRooms);
                        Log.e("yujie",bedRoomArrayList.toString());
                        adapter.refresh(bedRoomArrayList);
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

    private void initToolbar() {
        toolbar.setTitle("查看寝室信息");
        toolbar.setNavigationIcon(R.drawable.roommanager);
        toolbar.inflateMenu(R.menu.floor_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.add_floor:
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        View view = LayoutInflater.from(context).inflate(R.layout.add_bedroom_dialog, null);
                        final Spinner spinner_bFloor = (Spinner) view.findViewById(R.id.add_room_bfloor);
                        final Spinner spinner_bArea = (Spinner) view.findViewById(R.id.add_room_barea);
                        final EditText roomIntro = (EditText) view.findViewById(R.id.add_room_roomintro);
                        ArrayList<Floor> floors = BysjApplication.getInstance().getFloors();
                        ArrayList<String> floorstr = new ArrayList<String>();
                        for(Floor s: floors){
                            floorstr.add(s.getFloor());
                        }
                        ArrayList<String> areaStr = new ArrayList<String>();
                        areaStr.add("A区");areaStr.add("B区");areaStr.add("C区");areaStr.add("D区");
                        spinner_bFloor.setAdapter(new ArrayAdapter<String>(context,R.layout.support_simple_spinner_dropdown_item,floorstr));
                        spinner_bArea.setAdapter(new ArrayAdapter<String>(context,R.layout.support_simple_spinner_dropdown_item,areaStr));
                        Button btn = (Button) view.findViewById(R.id.sea_stu_button);
                        btn.setText("点击添加");
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final String intro = roomIntro.getText().toString();
                                if(intro.isEmpty()){
                                    roomIntro.setError("不能为空");
                                    roomIntro.requestFocus();
                                    return;
                                }
                                final String bFloor = spinner_bFloor.getSelectedItem().toString();
                                final String bArea = spinner_bArea.getSelectedItem().toString();
                                RequestParams params = new RequestParams(BysjApplication.INF_ROOT_SERVER);
                                params.addParameter(I.KEY_REQUEST,I.REQUEST_ADDBEDROOMINFO);
                                params.addParameter(I.ADDBEDROOMINFO.B_FLOOR,bFloor);
                                params.addParameter(I.ADDBEDROOMINFO.B_AREA,bArea);
                                params.addParameter(I.ADDBEDROOMINFO.INTRO,intro);
                                params.addParameter(I.ADDBEDROOMINFO.OPTUSER,I.CLIENTUSER);
                                Log.e("yujie",params.getUri()+"\n"+params.toString());
                                x.http().post(params, new Callback.CommonCallback<String>() {
                                    @Override
                                    public void onSuccess(String result) {
                                        if(result!=null){
                                            ObjectMapper om = new ObjectMapper();
                                            try {
                                                Result value = om.readValue(result, Result.class);
                                                if(value.getMsg().equals("添加成功")){
                                                    dialog.dismiss();
                                                    adapter.addItem(new BedRoom(bFloor,bArea,intro));
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
        adapter = new RoomAdapter(context,bedRooms);
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        bedRooms = BysjApplication.getInstance().getBedRooms();
    }

}
