package cn.lyj.mybysj.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.lyj.mybysj.BysjApplication;
import cn.lyj.mybysj.R;
import cn.lyj.mybysj.activity.CatLogActivity;
import cn.lyj.mybysj.activity.LoginActivity;
import cn.lyj.mybysj.activity.ResetPwdActivity;

@ContentView(R.layout.fragment_setting)
public class SettingFragment extends Fragment {
    @ViewInject(R.id.reset_pwd)
    private RelativeLayout reset_pwd;
    @ViewInject(R.id.cat_log)
    private RelativeLayout cat_log;
    @ViewInject(R.id.safe_exit)
    private RelativeLayout safe_exit;
    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return x.view().inject(this,inflater,container);
    }

    @Event(value = R.id.reset_pwd)
    private void setReset_pwd(View view){
        Intent intent = new Intent(getContext(),ResetPwdActivity.class);
        startActivity(intent);
    }
    @Event(value = R.id.cat_log)
    private void setCat_log(View view){
        Intent intent = new Intent(getContext(),CatLogActivity.class);
        startActivity(intent);
    }
    @Event(value = R.id.safe_exit)
    private void setSafe_exit(View view){
        BysjApplication.getInstance().setCurrentUser(null);
        Intent intent = new Intent(getContext(),LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
