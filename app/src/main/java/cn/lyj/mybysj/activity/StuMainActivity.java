package cn.lyj.mybysj.activity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

import cn.lyj.mybysj.BysjApplication;
import cn.lyj.mybysj.R;
import cn.lyj.mybysj.bean.BedRoom;
import cn.lyj.mybysj.bean.Floor;
import cn.lyj.mybysj.fragment.ChatFragment;
import cn.lyj.mybysj.fragment.ContactFragment;

@ContentView(R.layout.activity_stu_main)
public class StuMainActivity extends AppCompatActivity {
    private Context context;
    private ArrayList<Floor> floors;
    private ArrayList<String> areas;
    private ArrayList<BedRoom> bedRooms;
    @ViewInject(R.id.stuMainMsgRadioBtn)
    private RadioButton stuMainMsgRadioBtn;
    @ViewInject(R.id.stuMainContactRadioBtn)
    private RadioButton stuMainContactRadioBtn;
    @ViewInject(R.id.msgCount)
    private TextView msgCout;
    @ViewInject(R.id.stuMainViewPager)
    private ViewPager stuMainViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        context = this;
        initData();
        initViewPagerAdapter();
        setMoveListener();
    }

    private void setMoveListener() {
        stuMainContactRadioBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    stuMainMsgRadioBtn.setChecked(false);
                    stuMainViewPager.setCurrentItem(1);
                }
            }
        });

        stuMainMsgRadioBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    stuMainContactRadioBtn.setChecked(false);
                    stuMainViewPager.setCurrentItem(0);
                }
            }
        });
    }

    private void initViewPagerAdapter() {
        stuMainViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 0:
                        return new ChatFragment();
                    case 1:
                        return new ContactFragment();
                }
                return null;
            }

            @Override
            public int getCount() {
                return 2;
            }
        });
    }

    private void initData() {
        floors = BysjApplication.getInstance().getFloors();
        bedRooms = BysjApplication.getInstance().getBedRooms();
        areas = new ArrayList<>();
        areas.add("A区");
        areas.add("B区");
        areas.add("C区");
        areas.add("D区");
    }

    //  ┏┓　　　┏┓
    // ┏┛┻━━━┛┻┓
    // ┃　　　　　　　┃ 　
    // ┃　　　━　　　┃
    // ┃　┳┛　┗┳　┃
    // ┃　　　　　　　┃
    // ┃　　　┻　　　┃
    // ┃　　　　　　　┃
    // ┗━┓　　　┏━┛
    //    ┃　　　┃ 神兽保佑
    //    ┃　　　┃ 代码无BUG！
    //    ┃　　　┗━━━┓
    //    ┃　　　　　　　┣┓
    //    ┃　　　　　　　┏┛
    //    ┗┓┓┏━┳┓┏┛
    //     ┃┫┫　┃┫┫
    //     ┗┻┛　┗┻┛
}
