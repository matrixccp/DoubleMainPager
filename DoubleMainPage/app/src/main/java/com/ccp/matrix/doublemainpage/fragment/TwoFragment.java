package com.ccp.matrix.doublemainpage.fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ccp.matrix.doublemainpage.R;

/**
 * Created by Administrator on 2018/1/6.
 */

public class TwoFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        TextView tvName = view.findViewById(R.id.tv_name);
        tvName.setTextColor(Color.parseColor("#ffffff"));
        tvName.setText("TwoFragment");
        FrameLayout rlParent = view.findViewById(R.id.rl_parent);
        rlParent.setBackgroundColor(Color.parseColor("#333333"));
        return view;
    }
}
