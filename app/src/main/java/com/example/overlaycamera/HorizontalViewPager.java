package com.example.overlaycamera;

import android.content.Context;
import android.util.AttributeSet;

import androidx.viewpager.widget.ViewPager;

/**
 * Created by mohit on 1/1/17.
 */

public class HorizontalViewPager extends ViewPager {



    public HorizontalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizontalViewPager(Context context) {
        super(context);
    }

    @Override
    public boolean canScrollVertically(int direction) {
        return false;
    }

    @Override
    public boolean canScrollHorizontally(int direction) {
        return false;
    }



}
