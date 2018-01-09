package com.ccp.matrix.doublemainpage.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Tony on 2018/1/6.
 */

public class MyViewPager extends BaseViewPager {
    public Receiver receiver;

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean b = super.onInterceptTouchEvent(ev);
        if (receiver != null) {
            receiver.onReceive("onInterceptTouchEvent", new Item(ev, b));
        }
        return b;
    }

    public class Item {
        public MotionEvent motionEvent;
        public boolean intercept;

        public Item(MotionEvent motionEvent, boolean intercept) {
            this.motionEvent = motionEvent;
            this.intercept = intercept;
        }
    }
}
