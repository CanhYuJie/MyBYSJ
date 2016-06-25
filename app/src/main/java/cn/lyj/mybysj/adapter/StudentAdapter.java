package cn.lyj.mybysj.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import cn.lyj.mybysj.bean.Result;
import cn.lyj.mybysj.bean.Student;
import cn.lyj.mybysj.views.CircleTextImageView;

/**
 * Created by Administrator on 2016/6/24.
 */
class StudentViewHolder extends RecyclerView.ViewHolder{
    public CircleTextImageView stuAvatar;
    public TextView stuName;
    public TextView stuUid;
    public TextView stuBedRoom;
    public TextView stuDepartment;
    public TextView stuClass;
    public CardView item_root_layout;
    public StudentViewHolder(View itemView) {
        super(itemView);
        stuAvatar = (CircleTextImageView) itemView.findViewById(R.id.stuAvatar);
        stuName = (TextView) itemView.findViewById(R.id.stuName);
        stuBedRoom = (TextView) itemView.findViewById(R.id.stuBedroom);
        stuDepartment = (TextView) itemView.findViewById(R.id.stuDepartment);
        stuClass = (TextView) itemView.findViewById(R.id.stuclass);
        stuUid = (TextView) itemView.findViewById(R.id.stuUid);
        item_root_layout = (CardView) itemView.findViewById(R.id.item_root_layout);
    }
}
public class StudentAdapter  extends RecyclerView.Adapter<StudentViewHolder>{
    private Context context;
    private ArrayList<Student> students;
    private LayoutInflater inflater;
    private OnItemActionListener itemActionListener;

    public StudentAdapter(Context context, ArrayList<Student> students) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.students = students;
    }

    @Override
    public StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.stu_item,parent,false);
        StudentViewHolder viewHolder = new StudentViewHolder(view);
        viewHolder.setIsRecyclable(true);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final StudentViewHolder holder, final int position) {
        final Student item = getItem(position);
        holder.stuAvatar.setTextString(item.getName().substring(1,item.getName().length()));
        if(item.getSex().equals("男")){
            holder.stuAvatar.setImageDrawable(context.getResources().getDrawable(R.drawable.boy_icon));
        }else {
            holder.stuAvatar.setImageDrawable(context.getResources().getDrawable(R.drawable.girl_icon));
        }
        holder.stuName.setText(item.getName());
        holder.stuUid.setText(item.getUid());
        holder.stuBedRoom.setText(item.getB_bedroom());
        holder.stuDepartment.setText(item.getB_department());
        holder.stuClass.setText(item.getB_class());
        if(itemActionListener!=null){
            holder.item_root_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemActionListener.onItemClickListener(v,holder.getPosition(),item);
                }
            });
        }
        holder.item_root_layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("删除学生信息")
                        .setMessage("确定删除吗?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                RequestParams params = new RequestParams(BysjApplication.INF_ROOT_SERVER);
                                params.addParameter(I.KEY_REQUEST,I.REQUEST_DELSTUINFO);
                                params.addParameter(I.DELSTUINFO.UID,item.getUid());
                                params.addParameter(I.DELSTUINFO.OPTUSER,I.CLIENTUSER);
                                x.http().get(params, new Callback.CommonCallback<String>() {
                                    @Override
                                    public void onSuccess(String result) {
                                        if(result!=null){
                                            ObjectMapper om = new ObjectMapper();
                                            try {
                                                Result value = om.readValue(result, Result.class);
                                                if(value.getMsg().equals("删除成功")){
                                                    students.remove(position);
                                                    notifyDataSetChanged();
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
        return students==null?0:students.size();
    }

    public Student getItem(int position){
        return students.get(position);
    }

    public void initData(ArrayList<Student> studentList){
        this.students.clear();
        this.students.addAll(studentList);
        notifyDataSetChanged();
    }

    public void addItems(ArrayList<Student> addStuList){
        this.students.addAll(addStuList);
        notifyDataSetChanged();
    }

    public void addItem(Student student){
        this.students.add(student);
        notifyDataSetChanged();
    }

    public void cleanItems(){
        this.students.clear();
        notifyDataSetChanged();
    }

    public interface OnItemActionListener{
        public void onItemClickListener(View v,int position,Student student);
    }

    public void setItemActionListener(OnItemActionListener itemActionListener) {
        this.itemActionListener = itemActionListener;
    }
}
