package cn.lyj.mybysj.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.codehaus.jackson.map.ObjectMapper;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;

import cn.lyj.mybysj.BysjApplication;
import cn.lyj.mybysj.I;
import cn.lyj.mybysj.R;
import cn.lyj.mybysj.bean.BedRoom;
import cn.lyj.mybysj.bean.Floor;
import cn.lyj.mybysj.bean.Result;
import cn.lyj.mybysj.fragment.FloorManagerFragment;

/**
 * Created by Administrator on 2016/6/27.
 */
class RoomViewHolder extends RecyclerView.ViewHolder{
    TextView roomIntro;
    TextView bFloor;
    TextView bArea;
    LinearLayout rootLayout;
    public RoomViewHolder(View itemView) {
        super(itemView);
        roomIntro = (TextView) itemView.findViewById(R.id.roomName);
        bFloor = (TextView) itemView.findViewById(R.id.bFloor);
        bArea = (TextView) itemView.findViewById(R.id.bArea);
        rootLayout = (LinearLayout) itemView.findViewById(R.id.room_Root_layout);
    }
}
public class RoomAdapter extends RecyclerView.Adapter<RoomViewHolder>{
    private Context context;
    private LayoutInflater inflaters;
    private ArrayList<BedRoom> bedRooms;
    private AlertDialog dialog;
    public RoomAdapter(Context context, ArrayList<BedRoom> bedRooms) {
        this.context = context;
        inflaters = LayoutInflater.from(context);
        this.bedRooms = bedRooms;
    }

    @Override
    public RoomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = inflaters.inflate(R.layout.item_bedroom, parent, false);
        RoomViewHolder holder = new RoomViewHolder(inflate);
        holder.setIsRecyclable(true);
        return holder;
    }

    @Override
    public void onBindViewHolder(RoomViewHolder holder, final int position) {
        final BedRoom item = getItem(position);
        holder.bFloor.setText(item.getB_floor());
        holder.bArea.setText(item.getB_area());
        holder.roomIntro.setText(item.getIntro());
        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                for(int a1=0;a1<floorstr.size();a1++){
                    if (item.getB_floor().equals(floorstr.get(a1))){
                        spinner_bFloor.setSelection(a1);
                        break;
                    }
                }
                for(int a2=0;a2<areaStr.size();a2++){
                    if(item.getB_area().equals(areaStr.get(a2))){
                        spinner_bArea.setSelection(a2);
                        break;
                    }
                }
                roomIntro.setText(item.getIntro());
                btn.setText("点击修改");
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
                        params.addParameter(I.KEY_REQUEST,I.REQUEST_MODBEDROOMINFO);
                        params.addParameter(I.MODBEDROOMINFO.B_FLOOR,bFloor);
                        params.addParameter(I.MODBEDROOMINFO.B_AREA,bArea);
                        params.addParameter(I.MODBEDROOMINFO.INTRO,intro);
                        params.addParameter(I.MODBEDROOMINFO.MARK,item.getIntro());
                        params.addParameter(I.MODBEDROOMINFO.OPTUSER,I.CLIENTUSER);
                        Log.e("yujie",params.getUri()+"\n"+params.toString());
                        x.http().get(params, new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                if(result!=null){
                                    ObjectMapper om = new ObjectMapper();
                                    try {
                                        Result value = om.readValue(result, Result.class);
                                        if(value.getMsg().equals("修改成功")){
                                            bedRooms.get(position).setB_area(bArea);
                                            bedRooms.get(position).setB_floor(bFloor);
                                            bedRooms.get(position).setIntro(intro);
                                            dialog.dismiss();
                                            notifyDataSetChanged();
                                            Toast.makeText(context,"修改成功",Toast.LENGTH_SHORT).show();
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
            }
        });

        holder.rootLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("删除楼宇信息")
                        .setMessage("确定删除吗?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                RequestParams params = new RequestParams(BysjApplication.INF_ROOT_SERVER);
                                params.addParameter(I.KEY_REQUEST,I.REQUEST_DELBEDROOMINFO);
                                params.addParameter(I.DELBEDROOMINFO.INTRO,item.getIntro());
                                params.addParameter(I.DELBEDROOMINFO.OPTUSER,I.CLIENTUSER);
                                x.http().get(params, new Callback.CommonCallback<String>() {
                                    @Override
                                    public void onSuccess(String result) {
                                        if(result!=null){
                                            ObjectMapper om = new ObjectMapper();
                                            try {
                                                Result value = om.readValue(result, Result.class);
                                                if(value.getMsg().equals("删除成功")){
                                                    removeItem(position);
                                                    Toast.makeText(context,"删除成功",Toast.LENGTH_SHORT).show();
                                                }else {
                                                    notifyDataSetChanged();
                                                    Toast.makeText(context,"删除失败",Toast.LENGTH_SHORT).show();
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
                        })
                        .setNegativeButton("退出",null)
                        .create().show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return bedRooms==null?0:bedRooms.size();
    }

    public BedRoom getItem(int position){
        return bedRooms.get(position);
    }

    public void removeItem(int position){
        bedRooms.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(BedRoom bedRoom) {
        bedRooms.add(bedRoom);
        notifyDataSetChanged();
    }

    public void refresh(ArrayList<BedRoom> roomArrayList){
        bedRooms.clear();
        bedRooms.addAll(roomArrayList);
        notifyDataSetChanged();
    }
}
