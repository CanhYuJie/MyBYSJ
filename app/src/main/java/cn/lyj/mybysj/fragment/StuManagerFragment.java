package cn.lyj.mybysj.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import cn.lyj.mybysj.R;
@ContentView(R.layout.fragment_stu_manager)
public class StuManagerFragment extends Fragment {

    public StuManagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return x.view().inject(this,inflater,container);
    }

}
