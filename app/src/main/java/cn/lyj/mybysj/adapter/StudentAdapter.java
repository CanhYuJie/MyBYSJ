package cn.lyj.mybysj.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;

import cn.lyj.mybysj.R;
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
    public StudentViewHolder(View itemView) {
        super(itemView);
        stuAvatar = (CircleTextImageView) itemView.findViewById(R.id.stuAvatar);
        stuName = (TextView) itemView.findViewById(R.id.stuName);
        stuBedRoom = (TextView) itemView.findViewById(R.id.stuBedroom);
        stuDepartment = (TextView) itemView.findViewById(R.id.stuDepartment);
        stuClass = (TextView) itemView.findViewById(R.id.stuclass);
        stuUid = (TextView) itemView.findViewById(R.id.stuUid);
    }
}
public class StudentAdapter  extends RecyclerView.Adapter<StudentViewHolder>{
    private Context context;
    private ArrayList<Student> students;
    private LayoutInflater inflater;

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
    public void onBindViewHolder(StudentViewHolder holder, int position) {
        Student item = getItem(position);
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
}
