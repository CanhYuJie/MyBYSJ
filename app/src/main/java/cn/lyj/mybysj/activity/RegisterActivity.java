package cn.lyj.mybysj.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import org.codehaus.jackson.map.ObjectMapper;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;

import cn.lyj.mybysj.BysjApplication;
import cn.lyj.mybysj.I;
import cn.lyj.mybysj.R;
import cn.lyj.mybysj.bean.BedRoom;
import cn.lyj.mybysj.bean.ClassObj;
import cn.lyj.mybysj.bean.Department;
import cn.lyj.mybysj.bean.Result;
import cn.lyj.mybysj.utils.Utils;

@ContentView(R.layout.activity_register)
public class RegisterActivity extends AppCompatActivity {
    private Context context;
    private ArrayList<String> departments;
    private ArrayList<String> classObjs;
    private ArrayList<String> bedRooms;
    @ViewInject(R.id.register_stu_name)
    private EditText register_stu_name;
    @ViewInject(R.id.register_stu_uid)
    private EditText register_stu_uid;
    @ViewInject(R.id.sex_boy)
    private RadioButton radio_sex_boy;
    @ViewInject(R.id.sex_girl)
    private RadioButton radio_sex_girl;
    @ViewInject(R.id.department_Spinner)
    private Spinner spinner_department;
    @ViewInject(R.id.class_Spinner)
    private Spinner spinner_class;
    @ViewInject(R.id.bedRoom_Spinner)
    private Spinner spinner_bedRoom;
    @ViewInject(R.id.register_stu_remark)
    private EditText regiter_remark;
    @ViewInject(R.id.register_button)
    private Button register_button;
    @ViewInject(R.id.backToLogin)
    private TextView backToLogin;
    private ProgressDialog pd;

    private String name;
    private String uid;
    private String sex;
    private String department;
    private String classObj;
    private String bedroom;
    private String remark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        context = this;
        pd = new ProgressDialog(context);
        initData();
        initAdapter();
    }
    @Event(type = View.OnClickListener.class,value = R.id.backToLogin)
    private void setBackToLogin(View view){
        Intent intent = new Intent(context,StuLoginActivity.class);
        startActivity(intent);
    }


    @Event(type = View.OnClickListener.class,value = R.id.register_button)
    private void register_stu(View view){
        name = validNotEmpty(register_stu_name);
        if (name==null){
            return;
        }
        uid = validNotEmpty(register_stu_uid);
        if(uid==null){
            return;
        }
        sex = radio_sex_boy.isChecked()?"男":"女";
        if(sex==null){
            return;
        }
        department = spinner_department.getSelectedItem().toString();
        classObj = spinner_class.getSelectedItem().toString();
        bedroom = spinner_bedRoom.getSelectedItem().toString();
        remark = validNotEmpty(regiter_remark);
        if(remark==null){
            regiter_remark.setError("请输入密码");
            regiter_remark.requestFocus();
            return;
        }
        pd.setMessage("正在注册");
        pd.show();
        RequestParams params = new RequestParams(BysjApplication.INF_ROOT_SERVER);
        params.addParameter(I.KEY_REQUEST,I.REQUEST_ADDSTUINFO);
        params.addParameter(I.ADDSTUINFO.NAME,name);
        params.addParameter(I.ADDSTUINFO.UID,uid);
        params.addParameter(I.ADDSTUINFO.SEX,sex);
        params.addParameter(I.ADDSTUINFO.BDEPARTMENT,department);
        params.addParameter(I.ADDSTUINFO.BCLASS,classObj);
        params.addParameter(I.ADDSTUINFO.BBEDROOM,bedroom);
        params.addParameter(I.ADDSTUINFO.REMARK,remark);
        params.addParameter(I.ADDSTUINFO.OPTUSER,I.CLIENTUSER);
        Log.e("yujie",params.getUri()+"\n"+params.toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("yujie",result);
                if(result!=null){
                    ObjectMapper om = new ObjectMapper();
                    try {
                        Result result1 = om.readValue(result, Result.class);
                        Log.e("yujie",result1.toString());
                        if(result1.getMsg().equals("添加成功")){
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        EMClient.getInstance().createAccount(uid,remark);
                                        pd.dismiss();
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(context,getResources().getString(R.string.register_success),Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(context,StuLoginActivity.class);
                                                intent.putExtra("register_just",uid);
                                                startActivity(intent);
                                            }
                                        });
                                    } catch (HyphenateException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
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

    private String validNotEmpty(EditText view){
        String str = view.getText().toString();
        if (str.isEmpty()){
            view.setError("没有输入内容");
            view.requestFocus();
            return null;
        }else {
            return str;
        }
    }

    private void initAdapter() {
        ArrayAdapter<String> departmentAdapter = new ArrayAdapter<String>(context,R.layout.support_simple_spinner_dropdown_item,departments);
        departmentAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_department.setAdapter(departmentAdapter);

        ArrayAdapter<String> classAdapter = new ArrayAdapter<String>(context,R.layout.support_simple_spinner_dropdown_item,classObjs);
        classAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_class.setAdapter(classAdapter);

        ArrayAdapter<String> bedroomAdapter = new ArrayAdapter<String>(context,R.layout.support_simple_spinner_dropdown_item,bedRooms);
        bedroomAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_bedRoom.setAdapter(bedroomAdapter);
    }

    private void initData() {
        ArrayList<Department> departmentList = BysjApplication.getInstance().getDepartments();
        ArrayList<ClassObj> classObjsList = BysjApplication.getInstance().getClassObjs();
        ArrayList<BedRoom> bedRoomsList = BysjApplication.getInstance().getBedRooms();
        departments = new ArrayList<>();
        classObjs = new ArrayList<>();
        bedRooms = new ArrayList<>();
        //获取系部字符串集合
        for(Department s:departmentList){
            departments.add(s.getDepartment());
        }
        //获取班级集合
        for(int i=0;i<departments.size();i++){
            for(int j=0;j<classObjsList.size();j++){
                if(classObjsList.get(j).getbDepartment().equals(departments.get(i))){
                    classObjs.add(classObjsList.get(j).getClassName());
                }
            }
        }
        //获取寝室集合
        for(BedRoom b:bedRoomsList){
            bedRooms.add(b.getIntro());
        }
    }


}
