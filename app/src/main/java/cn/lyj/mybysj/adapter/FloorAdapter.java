package cn.lyj.mybysj.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cn.lyj.mybysj.R;
import cn.lyj.mybysj.bean.Floor;

/**
 * Created by Administrator on 2016/6/25.
 */
class FloorViewHolder extends RecyclerView.ViewHolder{
    TextView floorName;
    public FloorViewHolder(View itemView) {
        super(itemView);
        floorName = (TextView) itemView.findViewById(R.id.floorName);
    }
}
public class FloorAdapter extends RecyclerView.Adapter<FloorViewHolder>{
    private Context context;
    private ArrayList<Floor> floors;
    private LayoutInflater layoutInflater;

    public FloorAdapter(Context context, ArrayList<Floor> floors) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.floors = floors;
    }

    @Override
    public FloorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_floor, parent, false);
        FloorViewHolder viewHolder = new FloorViewHolder(view);
        viewHolder.setIsRecyclable(true);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FloorViewHolder holder, int position) {
        Floor item = getItem(position);
        holder.floorName.setText(item.getFloor());
    }

    @Override
    public int getItemCount() {
        return floors==null?0:floors.size();
    }

    public Floor getItem(int position){
        return floors.get(position);
    }

    public void refresh(ArrayList<Floor> floorArrayList){
        floors.clear();
        floors.addAll(floorArrayList);
        notifyDataSetChanged();
    }
}
