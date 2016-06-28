package cn.lyj.mybysj.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import cn.lyj.mybysj.bean.Student;
import cn.lyj.mybysj.utils.Utils;

@ContentView(R.layout.activity_stu_list)
public class StuListActivity extends AppCompatActivity {
    private Context context;
    @ViewInject(R.id.stuList)
    private ListView listView;
    private ArrayList<String> stuList;
    private ArrayList<Student> students;
    private AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        context = this;
        initStu();
        initListener();
    }

    private void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Student student = students.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view1 = LayoutInflater.from(context).inflate(R.layout.stu_deail, null);
                final TextView stuName = (TextView) view1.findViewById(R.id.stuName);
                final TextView stuBedroom = (TextView) view1.findViewById(R.id.stuBedroom);
                final TextView stuDepartment = (TextView) view1.findViewById(R.id.stuDepartment);
                final TextView stuClass = (TextView) view1.findViewById(R.id.stuclass);
                final Button sureBtn = (Button) view1.findViewById(R.id.okBtn);
                final Button sMsgBtn = (Button) view1.findViewById(R.id.sendMsgBtn);
                sureBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                sMsgBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("yujie","你即将要和"+student.getName()+"聊天");
                    }
                });
                stuName.setText(student.getName());
                stuBedroom.setText(student.getB_bedroom());
                stuDepartment.setText(student.getB_department());
                stuClass.setText(student.getB_class());
                builder.setView(view1).create();

                dialog = builder.show();
            }
        });
    }

    private void initStu() {
        stuList = new ArrayList<>();
        students = new ArrayList<>();
        Intent intent = getIntent();
        String roomIntro = intent.getStringExtra("roomIntro");
       if(roomIntro!=null){
           RequestParams params = new RequestParams(BysjApplication.INF_ROOT_SERVER);
           params.addParameter(I.KEY_REQUEST,I.REQUEST_SEASTUBYROOM);
           params.addParameter(I.SEASTUBYROOM.OPTUSER,I.CLIENTUSER);
           params.addParameter(I.SEASTUBYROOM.ROOMINTRO,roomIntro);
           x.http().get(params, new Callback.CommonCallback<String>() {
               @Override
               public void onSuccess(String result) {
                   if(result!=null){
                       ObjectMapper om = new ObjectMapper();
                       try {
                           Student[] value = om.readValue(result, Student[].class);
                           students = Utils.array2List(value);
                           stuList.clear();
                           for(Student s:value){
                               stuList.add(s.getName());
                           }
                           listView.setAdapter(new ArrayAdapter<String>(context,R.layout.support_simple_spinner_dropdown_item,stuList));
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
    }
}
