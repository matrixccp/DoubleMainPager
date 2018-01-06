package com.ccp.matrix.doublemainpage.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.ccp.matrix.doublemainpage.R;
import com.ccp.matrix.doublemainpage.adapter.HomePagerAdapter;
import com.ccp.matrix.doublemainpage.view.MyViewPager;
import com.ccp.matrix.doublemainpage.view.Receiver;

public class MainActivity extends AppCompatActivity implements Receiver {

    private float mLastX;
    private int mTouchSlop;
    private float mLastY;
    private MyViewPager viewPager;
    private HomePagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(this));
        setContentView(R.layout.activity_main);
        findView();
    }

    private void findView() {
        viewPager = findViewById(R.id.view_page);
        viewPager.receiver = this;
        FragmentManager fm = getFragmentManager();
        adapter = new HomePagerAdapter(fm);
        viewPager.setAdapter(adapter);
    }


    @Override
    public void onReceive(String dataType, Object object) {
        if ("onInterceptTouchEvent".equals(dataType)) {
            MyViewPager.Item item = (MyViewPager.Item) object;
            MotionEvent motionEvent = item.motionEvent;
            boolean intercept = item.intercept;
            int action = motionEvent.getAction();
            if (action == MotionEvent.ACTION_DOWN) {
                mLastX = motionEvent.getX();
                mLastY = motionEvent.getY();

                int currentItem = viewPager.getCurrentItem();
                if (currentItem == 0) {
                    if (adapter != null && adapter.oneMainFragment != null) {
                        adapter.oneMainFragment.slide();
                    }
                }
            } else if (action == MotionEvent.ACTION_MOVE) {
//                float currentX = motionEvent.getX();
//                float currentY = motionEvent.getY();
//                float xDiff = Math.abs(currentX - mLastX);
//                float yDiff = Math.abs(currentY - mLastY);
//
//                if (intercept && xDiff > mTouchSlop && xDiff * 0.5f > yDiff) {
//                    if (currentX > mLastX) {
//                        //向右滑
//                        int currentItem = viewPager.getCurrentItem();
//                        if (currentItem == 0) {
//                            if (adapter != null && adapter.oneMainFragment != null) {
//                                adapter.oneMainFragment.slide();
//                            }
//                        }
//                    }
//                }
            }
        }
    }
}
