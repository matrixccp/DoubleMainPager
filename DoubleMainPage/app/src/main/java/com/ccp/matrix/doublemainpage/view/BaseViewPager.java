package com.ccp.matrix.doublemainpage.view;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by Administrator on 2018/1/6.
 */

public class BaseViewPager extends ViewPager {
    public OnInterceptTouchEvent onInterceptTouchEvent;

    public BaseViewPager(Context context) {
        super(context);
    }

    public BaseViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
        }
        return false;
    }

    public void setOnInterceptTouchEvent(OnInterceptTouchEvent onInterceptTouchEvent) {
        this.onInterceptTouchEvent = onInterceptTouchEvent;
    }

    public void setDefaultInterceptTouchEvent() {
        setOnInterceptTouchEvent(defaultInterceptTouchEvent);
    }

    private OnInterceptTouchEvent defaultInterceptTouchEvent = new OnInterceptTouchEvent() {
        private float last_x, last_y;
        final ViewConfiguration configuration = ViewConfiguration.get(getContext());
        float mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);

        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                last_x = ev.getX();
                last_y = ev.getY();
                (getParent()).requestDisallowInterceptTouchEvent(true);
            }
            if (ev.getAction() == MotionEvent.ACTION_MOVE) {
                float c_x = ev.getX();
                float c_y = ev.getY();
                float x_diff = Math.abs(c_x - last_x);
                float y_diff = Math.abs(c_y - last_y);
                if (x_diff > mTouchSlop || y_diff > mTouchSlop) {
                    if (x_diff > y_diff) {
                        //水平滑动时，父VIEW不拦截
                        (getParent()).requestDisallowInterceptTouchEvent(true);
                    } else {
                        //垂直滑动时，父VIEW拦截
                        (getParent()).requestDisallowInterceptTouchEvent(false);
                        return false;
                    }
                }
            }

            return true;
        }
    };

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            boolean is_down = ev.getAction() == MotionEvent.ACTION_DOWN;
            if (onInterceptTouchEvent != null) {
                boolean intercept = onInterceptTouchEvent.onInterceptTouchEvent(ev);
                if (!intercept && is_down) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                    return false;
                }
            }
            boolean b = super.onInterceptTouchEvent(ev);
            if (!b && is_down) {
                getParent().requestDisallowInterceptTouchEvent(false);
            }
            return b;
        } catch (IllegalArgumentException ex) {
        }
        return false;
    }
}
