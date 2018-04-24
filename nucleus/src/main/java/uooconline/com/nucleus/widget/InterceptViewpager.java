/*
 * Copyright (c) 2016. WorldGo Technology Co., Ltd
 * DO NOT DIVULGE
 */

package uooconline.com.nucleus.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

public class InterceptViewpager extends ViewPager {
    private boolean noScroll = false;
    private int mScrollSpeed = 500;

    public InterceptViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InterceptViewpager(Context context) {
        super(context);
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        //viewpager滚动在这里
        return !noScroll && super.onTouchEvent(e);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        return !noScroll && super.onInterceptTouchEvent(e);
    }

    public void setNoScroll(boolean noScroll) {
        this.noScroll = noScroll;
    }

    public void scrollSpeedFix() {
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();
            FixedSpeedScroller myScroller = new FixedSpeedScroller(getContext(), interpolator, mScrollSpeed);
            mScroller.set(this, myScroller);
        } catch (Exception ignored) {
        }
    }


    public static class FixedSpeedScroller extends Scroller {
        private int mScrollSpeed = 450;

        public FixedSpeedScroller(Context context) {
            super(context);
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator, int scrollSpeed) {
            super(context, interpolator);
            this.mScrollSpeed = scrollSpeed;
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, this.mScrollSpeed);
        }
    }
}
