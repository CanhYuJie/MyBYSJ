package cn.lyj.mybysj.activity;

import android.content.Intent;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;


import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import cn.lyj.mybysj.R;
import cn.lyj.mybysj.adapter.GuideViewPagerAdapter;
import cn.lyj.mybysj.animations.RokeAnimation;
@ContentView(R.layout.activity_guide)
public class GuideActivity extends AppCompatActivity {
    @ViewInject(R.id.guideViewPager)
    private ViewPager vp;
    private GuideViewPagerAdapter viewPagerAdapter;
    private ArrayList<View> viewArrayList;
    private ImageView[] dots;
    private int[] ids = {R.id.iv1,R.id.iv2,R.id.iv3};
    private Button goMainBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initView();
        initDots();
        initAdapter();
        initOne();
        initThree();
    }

    private void initOne() {

    }


    /**
     * 初始化引导页3
     */
    private void initThree() {
        goMainBtn = (Button) viewArrayList.get(2).findViewById(R.id.goMainBtn);
        goMainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GuideActivity.this,StuLoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    /**
     * 初始化底部导航点
     */
    private void initDots() {
        dots = new ImageView[viewArrayList.size()];
        for (int i=0;i<viewArrayList.size();i++){
            dots[i] = (ImageView) findViewById(ids[i]);
        }
    }

    /**
     * 设置ViewPager适配器及滑屏事件监听
     */
    private void initAdapter() {
        viewPagerAdapter = new GuideViewPagerAdapter(viewArrayList,this);
        vp.setAdapter(viewPagerAdapter);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i=0;i<ids.length;i++){
                    if(position==i){
                        dots[i].setBackgroundResource(R.drawable.point_select);
                    }else {
                        dots[i].setBackgroundResource(R.drawable.point_normal);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 初始化视图
     */
    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        viewArrayList = new ArrayList<>();
        viewArrayList.add(inflater.inflate(R.layout.guide_img_one,null));
        viewArrayList.add(inflater.inflate(R.layout.guide_img_two,null));
        viewArrayList.add(inflater.inflate(R.layout.guide_img_three,null));
        vp = (ViewPager) findViewById(R.id.guideViewPager);
    }
}
