package cn.lyj.mybysj.activity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.lyj.mybysj.R;
import cn.lyj.mybysj.fragment.FloorManagerFragment;
import cn.lyj.mybysj.fragment.RoomManagerFragment;
import cn.lyj.mybysj.fragment.SettingFragment;
import cn.lyj.mybysj.fragment.StuManagerFragment;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    private Context context;
    @ViewInject(R.id.RbtnGroup)
    private RadioGroup RbtnGroup;
    @ViewInject(R.id.userMainViewPager)
    private ViewPager mainViewPager;
    @ViewInject(R.id.mainStuManagerRbtn)
    private RadioButton mainStuManagerRbtn;
    @ViewInject(R.id.mainFloorManagerRbtn)
    private RadioButton mainFloorManagerRbtn;
    @ViewInject(R.id.mainRoomManagerRbtn)
    private RadioButton mainRoomManagerRbtn;
    @ViewInject(R.id.mainSettingRbtn)
    private RadioButton mainSettingRbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initAdapter();
        setListener();
        mainStuManagerRbtn.setChecked(true);
    }

    private void setListener() {
        mainViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        mainStuManagerRbtn.setChecked(true);
                        break;
                    case 1:
                        mainFloorManagerRbtn.setChecked(true);
                        break;
                    case 2:
                        mainRoomManagerRbtn.setChecked(true);
                        break;
                    case 3:
                        mainSettingRbtn.setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        RbtnGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.mainStuManagerRbtn:
                        mainViewPager.setCurrentItem(0);
                        break;
                    case R.id.mainFloorManagerRbtn:
                        mainViewPager.setCurrentItem(1);
                        break;
                    case R.id.mainRoomManagerRbtn:
                        mainViewPager.setCurrentItem(2);
                        break;
                    case R.id.mainSettingRbtn:
                        mainViewPager.setCurrentItem(3);
                        break;
                }
            }
        });
    }

    private void initAdapter() {
        mainViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 0:
                        return new StuManagerFragment();
                    case 1:
                        return new FloorManagerFragment();
                    case 2:
                        return new RoomManagerFragment();
                    case 3:
                        return new SettingFragment();
                }
                return null;
            }

            @Override
            public int getCount() {
                return 4;
            }
        });

    }
}
