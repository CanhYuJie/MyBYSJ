package cn.lyj.mybysj.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cn.lyj.mybysj.R;
import cn.lyj.mybysj.bean.Floor;

/**
 * Created by Administrator on 2016/6/25.
 */
class FloorViewHolder extends RecyclerView.ViewHolder{
    TextView floorName;
    LinearLayout floor_root_layout;
    public FloorViewHolder(View itemView) {
        super(itemView);
        floorName = (TextView) itemView.findViewById(R.id.floorName);
        floor_root_layout = (LinearLayout) itemView.findViewById(R.id.floor_Root_layout);

    }
}
public class FloorAdapter extends RecyclerView.Adapter<FloorViewHolder>{
    private Context context;
    private ArrayList<Floor> floors;
    private LayoutInflater layoutInflater;
    private OnItemActionListener onItemActionListener;
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
    public void onBindViewHolder(final FloorViewHolder holder, int position) {
        final Floor item = getItem(position);
        holder.floorName.setText(item.getFloor());
        if(onItemActionListener!=null){
            holder.floor_root_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemActionListener.onItemClickListener(v,holder.getPosition(),item);
                }
            });
        }
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

    public void removeItem(int position) {
        floors.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(Floor floor) {
        floors.add(floor);
        notifyDataSetChanged();
    }

    public interface OnItemActionListener{
        public void onItemClickListener(View v,int position,Floor floor);
    }

    public void setOnItemActionListener(OnItemActionListener onItemActionListener) {
        this.onItemActionListener = onItemActionListener;
    }
}
