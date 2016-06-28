package cn.lyj.mybysj.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

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
import cn.lyj.mybysj.activity.StuListActivity;
import cn.lyj.mybysj.bean.BedRoom;
import cn.lyj.mybysj.bean.Floor;

@ContentView(R.layout.fragment_contact)
public class ContactFragment extends Fragment {
    private Context context;
    @ViewInject(R.id.floor_Spinnser)
    private Spinner floorSpinner;
    @ViewInject(R.id.area_Spinnser)
    private Spinner areaSpinner;
    @ViewInject(R.id.roomList)
    private ListView roomList;
    private ArrayList<String> roomStr;
    public ContactFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = x.view().inject(this, inflater, container);
        context = getActivity();
        initData();
        initListener();
        return view;
    }

    private void initListener() {
        areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getData();
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        floorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        roomList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String roomIntro = roomStr.get(position);
                Intent intent = new Intent(context,StuListActivity.class);
                intent.putExtra("roomIntro",roomIntro);
                startActivity(intent);
            }
        });
    }

    private void getData() {
        String floorName = floorSpinner.getSelectedItem().toString();
        if (floorName.isEmpty()){
            return;
        }
        String areaName = areaSpinner.getSelectedItem().toString();
        if (areaName.isEmpty()){
            return;
        }
        final RequestParams params = new RequestParams(BysjApplication.INF_ROOT_SERVER);
        params.addParameter(I.KEY_REQUEST,I.REQUEST_SEABEDROOMBYAREAANDFLOOR);
        params.addParameter(I.SEABEDROOMBYAREAANDFLOOR.AREA,areaName);
        params.addParameter(I.SEABEDROOMBYAREAANDFLOOR.FLOOR,floorName);
        params.addParameter(I.SEABEDROOMBYAREAANDFLOOR.OPTUSER,I.CLIENTUSER);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(result!=null){
                    Log.e("yujie","请求成功了"+params.toString()+"\n"+result);
                    ObjectMapper om = new ObjectMapper();
                    try {
                        BedRoom[] bedRooms = om.readValue(result, BedRoom[].class);
                        roomStr.clear();
                        if(bedRooms!=null){
                            for(BedRoom s:bedRooms){
                                roomStr.add(s.getIntro());
                            }
                            roomList.setAdapter(new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item,roomStr));
                        }else {
                            roomStr.add("没有更多数据");
                            roomList.setAdapter(new ArrayAdapter<String>(context,R.layout.support_simple_spinner_dropdown_item,roomStr));
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

    private void initData() {
        ArrayList<Floor> floors = BysjApplication.getInstance().getFloors();
        ArrayList<String> floorstr = new ArrayList<String>();
        for(Floor s: floors){
            floorstr.add(s.getFloor());
        }
        ArrayList<String> areaStr = new ArrayList<String>();
        areaStr.add("A区");areaStr.add("B区");areaStr.add("C区");areaStr.add("D区");
        floorSpinner.setAdapter(new ArrayAdapter<String>(context,R.layout.support_simple_spinner_dropdown_item,floorstr));
        areaSpinner.setAdapter(new ArrayAdapter<String>(context,R.layout.support_simple_spinner_dropdown_item,areaStr));
        roomStr = new ArrayList<>();
    }


}
