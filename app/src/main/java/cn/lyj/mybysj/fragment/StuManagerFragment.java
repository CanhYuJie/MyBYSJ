package cn.lyj.mybysj.fragment;

import android.app.ProgressDialog;
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
import android.widget.RadioButton;
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
import cn.lyj.mybysj.adapter.StudentAdapter;
import cn.lyj.mybysj.bean.BedRoom;
import cn.lyj.mybysj.bean.ClassObj;
import cn.lyj.mybysj.bean.Department;
import cn.lyj.mybysj.bean.Result;
import cn.lyj.mybysj.bean.Student;
import cn.lyj.mybysj.utils.Utils;

@ContentView(R.layout.fragment_stu_manager)
public class StuManagerFragment extends Fragment {
    private Context context;
    @ViewInject(R.id.readStuInfoToolBar)
    private Toolbar toolbar;
    @ViewInject(R.id.readStuRefresh)
    private SwipeRefreshLayout refreshLayout;
    @ViewInject(R.id.readStuRecycleView)
    private RecyclerView recyclerView;
    private StudentAdapter adapter;
    private RequestParams params;
    private StaggeredGridLayoutManager manager;
    private LayoutInflater inflaters;
    private AlertDialog dialog;

    private ArrayList<Student> students;
    public StuManagerFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        View view = x.view().inject(this, inflater, container);
        inflaters = LayoutInflater.from(context);
        initToolBar();
        initData();
        initAdapter();
        initRefreshListener();
        return view;
    }

    private void initRefreshListener() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        if(result!=null){
                            ObjectMapper om = new ObjectMapper();
                            try {
                                Student[] studentarr = om.readValue(result, Student[].class);
                                students = Utils.array2List(studentarr);
                                adapter.cleanItems();
                                adapter.addItems(students);
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
                        refreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }

    private void initAdapter() {
        adapter = new StudentAdapter(context,students);
        recyclerView.setAdapter(adapter);
        manager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter.setItemActionListener(new StudentAdapter.OnItemActionListener() {
            @Override
            public void onItemClickListener(View v, int position, Student student) {
                showModStuDialog(student);
            }
        });
    }

    private void showModStuDialog(final Student stu) {
        View view = inflaters.inflate(R.layout.add_stu_dialog,null);
        final EditText add_stu_name = (EditText) view.findViewById(R.id.add_stu_name);
        final EditText add_stu_uid = (EditText) view.findViewById(R.id.add_stu_uid);
        final RadioButton chose_boy_Rbtn = (RadioButton) view.findViewById(R.id.chose_boy_Rbtn);
        RadioButton chose_girl_Rbtn = (RadioButton) view.findViewById(R.id.chose_girl_Rbtn);
        final Spinner add_stu_department_Spinner = (Spinner) view.findViewById(R.id.add_stu_department_Spinner);
        final Spinner add_stu_class_Spinner = (Spinner) view.findViewById(R.id.add_stu_class_Spinner);
        final Spinner add_stu_bedroom_Spinner = (Spinner) view.findViewById(R.id.add_stu_bedroom_Spinner);
        final EditText add_stu_remark = (EditText) view.findViewById(R.id.add_stu_remark);
        Button add_stu_button = (Button) view.findViewById(R.id.add_stu_button);
        add_stu_button.setText("点击修改");
        final ProgressDialog pd = new ProgressDialog(context);

        ArrayList<Department> departmentList = BysjApplication.getInstance().getDepartments();
        ArrayList<ClassObj> classObjsList = BysjApplication.getInstance().getClassObjs();
        ArrayList<BedRoom> bedRoomsList = BysjApplication.getInstance().getBedRooms();
        ArrayList<String> departments = new ArrayList<>();
        ArrayList<String> classObjs = new ArrayList<>();
        ArrayList<String> bedRooms = new ArrayList<>();
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
        ArrayAdapter<String> departmentAdapter = new ArrayAdapter<String>(context,R.layout.support_simple_spinner_dropdown_item,departments);
        departmentAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        add_stu_department_Spinner.setAdapter(departmentAdapter);

        ArrayAdapter<String> classAdapter = new ArrayAdapter<String>(context,R.layout.support_simple_spinner_dropdown_item,classObjs);
        classAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        add_stu_class_Spinner.setAdapter(classAdapter);

        ArrayAdapter<String> bedroomAdapter = new ArrayAdapter<String>(context,R.layout.support_simple_spinner_dropdown_item,bedRooms);
        bedroomAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        add_stu_bedroom_Spinner.setAdapter(bedroomAdapter);
        if (stu!=null){
            add_stu_name.setText(stu.getName());
            add_stu_uid.setText(stu.getUid());
            if (stu.getSex().equals("男")){
                chose_boy_Rbtn.setChecked(true);
            }else {
                chose_girl_Rbtn.setChecked(true);
            }
            for(int a1=0;a1<departments.size();a1++){
                if(stu.getB_department().equals(departments.get(a1))){
                    add_stu_department_Spinner.setSelection(a1,true);
                    break;
                }
            }
            for(int a2=0;a2<classObjs.size();a2++){
                if(stu.getB_class().equals(classObjs.get(a2))){
                    add_stu_class_Spinner.setSelection(a2,true);
                    break;
                }
            }
            for (int a3=0;a3<bedRooms.size();a3++){
                if(stu.getB_bedroom().equals(bedRooms.get(a3))){
                    add_stu_bedroom_Spinner.setSelection(a3,true);
                    break;
                }
            }
            add_stu_remark.setText(stu.getRemark());
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        add_stu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = add_stu_name.getText().toString();
                if(name.isEmpty()){
                    add_stu_name.setError("名字不能为空");
                    add_stu_name.requestFocus();
                    return;
                }
                String uid = add_stu_uid.getText().toString();
                if(uid.isEmpty()){
                    add_stu_uid.setError("学号不能为空");
                    add_stu_uid.requestFocus();
                    return;
                }
                String sex = chose_boy_Rbtn.isChecked()?"男":"女";
                String department = add_stu_department_Spinner.getSelectedItem().toString();
                String className = add_stu_class_Spinner.getSelectedItem().toString();
                String bedroom = add_stu_bedroom_Spinner.getSelectedItem().toString();
                String remark = add_stu_remark.getText().toString();
                if(remark.isEmpty()){
                    add_stu_remark.setError("登录密码不能为空");
                    add_stu_remark.requestFocus();
                    return;
                }
                pd.setMessage("正在修改...");
                pd.show();
                RequestParams params = new RequestParams(BysjApplication.INF_ROOT_SERVER);
                params.addParameter(I.KEY_REQUEST,I.REQUEST_MODSTUINFO);
                params.addParameter(I.MODSTUINFO.NAME,name);
                params.addParameter(I.MODSTUINFO.UID,uid);
                params.addParameter(I.MODSTUINFO.SEX,sex);
                params.addParameter(I.MODSTUINFO.BDEPARTMENT,department);
                params.addParameter(I.MODSTUINFO.BCLASS,className);
                params.addParameter(I.MODSTUINFO.BBEDROOM,bedroom);
                params.addParameter(I.MODSTUINFO.REMARK,remark);
                params.addParameter(I.MODSTUINFO.OPTUSER,I.CLIENTUSER);
                params.addParameter(I.MODSTUINFO.MARK,stu.getUid());
                Log.e("yujie",params.getUri()+"\n"+params.toString());
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.e("yujie",result);
                        if(result!=null){
                            ObjectMapper om = new ObjectMapper();
                            try {
                                Result result1 = om.readValue(result, Result.class);
                                if(result1.getMsg().equals("修改成功")){
                                    Toast.makeText(context,"修改成功",Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    initRefreshListener();
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
                        pd.dismiss();
                    }
                });
            }
        });
        builder.setView(view).create();
        dialog = builder.show();
    }

    private void initData() {
        students = new ArrayList<>();
        params = new RequestParams(BysjApplication.INF_ROOT_SERVER);
        params.addParameter(I.KEY_REQUEST,I.REQUEST_GETSTUINFO);
        params.addParameter(I.GETSTUINFO.OPTUSER,I.CLIENTUSER);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(result!=null){
                    ObjectMapper om = new ObjectMapper();
                    try {
                        Student[] studentarr = om.readValue(result, Student[].class);
                        students = Utils.array2List(studentarr);
                        adapter.cleanItems();
                        adapter.addItems(students);
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

    private void initToolBar() {
        toolbar.setTitle("查看学生信息");
        toolbar.setNavigationIcon(R.drawable.read_stus);
        toolbar.inflateMenu(R.menu.read_stu_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.add_stu:
                        showStuDialog();
                        break;
                    case R.id.sea_stu:
                        showSeaStuDialog();
                        break;
                }
                return false;
            }
        });
    }

    private void showSeaStuDialog() {
        View view = inflaters.inflate(R.layout.sea_stu_dialog,null);
        final EditText stu_uid = (EditText) view.findViewById(R.id.sea_stu_uid);
        Button sea_btn = (Button) view.findViewById(R.id.sea_stu_button);
        sea_btn.setText("点击搜索");
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        sea_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = stu_uid.getText().toString();
                RequestParams params = new RequestParams(BysjApplication.INF_ROOT_SERVER);
                params.addParameter(I.KEY_REQUEST,I.REQUEST_GETONSTUINFO);
                params.addParameter(I.GETONESTUINFO.UID,uid);
                params.addParameter(I.GETONESTUINFO.OPTUSER,I.CLIENTUSER);
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        if(result!=null){
                            ObjectMapper om = new ObjectMapper();
                            try {
                                Student student = om.readValue(result, Student.class);
                                dialog.dismiss();
                                adapter.cleanItems();
                                adapter.addItem(student);
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

    private void showStuDialog() {
        View view = inflaters.inflate(R.layout.add_stu_dialog,null);
        final EditText add_stu_name = (EditText) view.findViewById(R.id.add_stu_name);
        final EditText add_stu_uid = (EditText) view.findViewById(R.id.add_stu_uid);
        final RadioButton chose_boy_Rbtn = (RadioButton) view.findViewById(R.id.chose_boy_Rbtn);
        RadioButton chose_girl_Rbtn = (RadioButton) view.findViewById(R.id.chose_girl_Rbtn);
        final Spinner add_stu_department_Spinner = (Spinner) view.findViewById(R.id.add_stu_department_Spinner);
        final Spinner add_stu_class_Spinner = (Spinner) view.findViewById(R.id.add_stu_class_Spinner);
        final Spinner add_stu_bedroom_Spinner = (Spinner) view.findViewById(R.id.add_stu_bedroom_Spinner);
        final EditText add_stu_remark = (EditText) view.findViewById(R.id.add_stu_remark);
        Button add_stu_button = (Button) view.findViewById(R.id.add_stu_button);
        final ProgressDialog pd = new ProgressDialog(context);

        ArrayList<Department> departmentList = BysjApplication.getInstance().getDepartments();
        ArrayList<ClassObj> classObjsList = BysjApplication.getInstance().getClassObjs();
        ArrayList<BedRoom> bedRoomsList = BysjApplication.getInstance().getBedRooms();
        ArrayList<String> departments = new ArrayList<>();
        ArrayList<String> classObjs = new ArrayList<>();
        ArrayList<String> bedRooms = new ArrayList<>();
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
        ArrayAdapter<String> departmentAdapter = new ArrayAdapter<String>(context,R.layout.support_simple_spinner_dropdown_item,departments);
        departmentAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        add_stu_department_Spinner.setAdapter(departmentAdapter);

        ArrayAdapter<String> classAdapter = new ArrayAdapter<String>(context,R.layout.support_simple_spinner_dropdown_item,classObjs);
        classAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        add_stu_class_Spinner.setAdapter(classAdapter);

        ArrayAdapter<String> bedroomAdapter = new ArrayAdapter<String>(context,R.layout.support_simple_spinner_dropdown_item,bedRooms);
        bedroomAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        add_stu_bedroom_Spinner.setAdapter(bedroomAdapter);

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        add_stu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = add_stu_name.getText().toString();
                if(name.isEmpty()){
                    add_stu_name.setError("名字不能为空");
                    add_stu_name.requestFocus();
                    return;
                }
                String uid = add_stu_uid.getText().toString();
                if(uid.isEmpty()){
                    add_stu_uid.setError("学号不能为空");
                    add_stu_uid.requestFocus();
                    return;
                }
                String sex = chose_boy_Rbtn.isChecked()?"男":"女";
                String department = add_stu_department_Spinner.getSelectedItem().toString();
                String className = add_stu_class_Spinner.getSelectedItem().toString();
                String bedroom = add_stu_bedroom_Spinner.getSelectedItem().toString();
                String remark = add_stu_remark.getText().toString();
                if(remark.isEmpty()){
                    add_stu_remark.setError("登录密码不能为空");
                    add_stu_remark.requestFocus();
                    return;
                }
                pd.setMessage("正在添加...");
                pd.show();
                RequestParams params = new RequestParams(BysjApplication.INF_ROOT_SERVER);
                params.addParameter(I.KEY_REQUEST,I.REQUEST_ADDSTUINFO);
                params.addParameter(I.ADDSTUINFO.NAME,name);
                params.addParameter(I.ADDSTUINFO.UID,uid);
                params.addParameter(I.ADDSTUINFO.SEX,sex);
                params.addParameter(I.ADDSTUINFO.BDEPARTMENT,department);
                params.addParameter(I.ADDSTUINFO.BCLASS,className);
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
                                if(result1.getMsg().equals("添加成功")){
                                    Toast.makeText(context,"添加成功",Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    initRefreshListener();
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
                        pd.dismiss();
                    }
                });
            }
        });
        builder.setView(view).create();
        dialog = builder.show();
    }

}
