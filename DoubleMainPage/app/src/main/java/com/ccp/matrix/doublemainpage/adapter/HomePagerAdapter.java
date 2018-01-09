package com.ccp.matrix.doublemainpage.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import com.ccp.matrix.doublemainpage.fragment.FourFragment;
import com.ccp.matrix.doublemainpage.fragment.OneMainFragment;
import com.ccp.matrix.doublemainpage.fragment.ThreeFragment;
import com.ccp.matrix.doublemainpage.fragment.TwoFragment;

/**
 * Created by Tony on 2018/1/6.
 */

public class HomePagerAdapter extends BaseAdapter {
    public static final int oneIndex = 0;
    public static final int twoIndex = 1;
    public static final int threeIndex = 2;
    public static final int fourIndex = 3;
    public Fragment twoFragment, threeFragment, fourFragment;
    public OneMainFragment oneMainFragment;

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == oneIndex) {
            if (oneMainFragment == null) {
                oneMainFragment = new OneMainFragment();
            }
            return oneMainFragment;
        }
        if (position == twoIndex) {
            if (twoFragment == null) {
                twoFragment = new TwoFragment();
            }
            return twoFragment;
        }
        if (position == threeIndex) {
            if (threeFragment == null) {
                threeFragment = new ThreeFragment();
            }
            return threeFragment;
        }
        if (position == fourIndex) {
            if (fourFragment == null) {
                fourFragment = new FourFragment();
            }
            return fourFragment;
        }
        return fourFragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }
}
