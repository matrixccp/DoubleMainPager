package com.ccp.matrix.doublemainpage.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ccp.matrix.doublemainpage.R;
import com.ccp.matrix.doublemainpage.view.Receiver;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Tony on 2018/1/6.
 */

public class OneMainFragment extends Fragment implements Receiver{

    private TextView tvName;
    private CustomDiscoverFragment discoverFragment;
    private BeforeFragmentCustom customDiscoverFragment;
    private boolean slideFinish = true;
    private View discover_main;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        tvName = view.findViewById(R.id.tv_name);
        discover_main = view.findViewById(R.id.rl_parent);
        discover_main.setBackgroundColor(Color.parseColor("#ff7f00"));
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FragmentManager fragmentManager = getFragmentManager();

        synchronized (getActivity()) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            initFragment();
            fragmentTransaction.add(R.id.discover_main, discoverFragment);
            fragmentTransaction.add(R.id.discover_main, customDiscoverFragment);
            fragmentTransaction.hide(discoverFragment).show(customDiscoverFragment);
            try {
                fragmentTransaction.commit();
            } catch (Exception e) {
                try {
                    fragmentTransaction.commitAllowingStateLoss();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }

    }

    private void slideDiscoverFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        int enter = R.animator.card_flip_horizontal_left_in;
        int exit = R.animator.card_flip_horizontal_right_out;
        int popEnter = R.animator.card_flip_horizontal_left_in;
        int popExit = R.animator.card_flip_horizontal_right_out;
        fragmentTransaction.setCustomAnimations(enter, exit, popEnter, popExit);
        initFragment();
        boolean discoverFragmentAdded = discoverFragment.isAdded();
        if (!discoverFragmentAdded) {
            //没有添加则添加
            fragmentTransaction.hide(customDiscoverFragment).add(R.id.discover_main, discoverFragment);
        } else if (discoverFragment.isHidden()) {
            //隐藏则显示
            setSlideFinish(false);
            fragmentTransaction.hide(customDiscoverFragment).show(discoverFragment);
        }
        fragmentTransaction.commit();
    }

    private void slideCustomDiscoverFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        int enter = R.animator.card_flip_horizontal_left_in;
        int exit = R.animator.card_flip_horizontal_right_out;
        int popEnter = R.animator.card_flip_horizontal_left_in;
        int popExit = R.animator.card_flip_horizontal_right_out;
        fragmentTransaction.setCustomAnimations(enter, exit, popEnter, popExit);
        initFragment();
        boolean customDiscoverFragmentAdded = customDiscoverFragment.isAdded();
        if (!customDiscoverFragmentAdded) {
            //没有添加则添加
            fragmentTransaction.hide(discoverFragment).add(R.id.discover_main, customDiscoverFragment);
        } else if (customDiscoverFragment.isHidden()) {
            //隐藏则显示
            setSlideFinish(false);
            fragmentTransaction.hide(discoverFragment).show(customDiscoverFragment);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        invokeFragmentManagerNoteStateNotSaved();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void invokeFragmentManagerNoteStateNotSaved() {
        /**
         * For post-Honeycomb devices
         */
        if (Build.VERSION.SDK_INT < 11) {
            return;
        }
        try {
            Class cls = getClass();
            do {
                cls = cls.getSuperclass();
            } while (!"Activity".equals(cls.getSimpleName()));
            Field fragmentMgrField = cls.getDeclaredField("mFragments");
            fragmentMgrField.setAccessible(true);

            Object fragmentMgr = fragmentMgrField.get(this);
            cls = fragmentMgr.getClass();

            Method noteStateNotSavedMethod = cls.getDeclaredMethod("noteStateNotSaved", new Class[]{});
            noteStateNotSavedMethod.invoke(fragmentMgr, new Object[]{});
            System.err.println("Successful call for noteStateNotSaved!!!");
        } catch (Exception ex) {
            System.err.println("Exception on worka FM.noteStateNotSaved");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (needSlide) {
            needSlide = false;
            discover_main.postDelayed(new Runnable() {
                @Override
                public void run() {
                    slide(false);
                }
            }, 500);
        }
    }

    private synchronized void initFragment() {
        if (customDiscoverFragment == null) {
            customDiscoverFragment = new BeforeFragmentCustom();
            customDiscoverFragment.receiver = this;
        }
        if (discoverFragment == null) {
            discoverFragment = new CustomDiscoverFragment();
            discoverFragment.receiver = this;
        }
    }

    public void slide() {
        if (slideFinish) {
            slide(true);
        }
    }

    public synchronized void slide(boolean slide) {
        initFragment();
        if (slide) {
            if (customDiscoverFragment.isVisible()) {
                slideDiscoverFragment();
            } else {
                slideCustomDiscoverFragment();
            }
        } else {
            boolean question_complete = true;
            boolean hasAnswer = false;
            if (question_complete || hasAnswer) {
                slideCustomDiscoverFragment();
            } else {
                slideDiscoverFragment();
            }
        }
    }


    private boolean needSlide = false;


    @Override
    public void onReceive(String dataType, Object object) {
        if ("onHiddenChanged".equals(dataType)) {
            tvName.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setSlideFinish(true);
                }
            }, 800);
        }
    }

    public void setSlideFinish(boolean slideFinish) {
        this.slideFinish = slideFinish;
    }
}
