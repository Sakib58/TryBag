package com.example.overlaycamera;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class VerticalViewPager extends ViewPager {
    public VerticalViewPager(@NonNull Context context) {
        super(context);
    }

    public VerticalViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setPageTransformer(true,new VerticalPage());
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    private MotionEvent getIntercambioXY(MotionEvent me){
        float width = getWidth();
        float height = getHeight();

        float newX = (me.getY() / height) * width;
        float newY = (me.getX() / width) * height;

        me.setLocation(newX,newY);
        return me;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = super.onInterceptTouchEvent(getIntercambioXY(ev));
        getIntercambioXY(ev);
        return  intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(getIntercambioXY(ev));
    }

    private class VerticalPage implements ViewPager.PageTransformer{

        @Override
        public void transformPage(@NonNull View page, float position) {
            if (position < -1){
                page.setAlpha(0);
            }else if (position <= 1){
                page.setAlpha(1);
                page.setTranslationX(page.getWidth() * -position);
                float yPosition = position * page.getHeight();
                page.setTranslationY(yPosition);
            }else{
                page.setAlpha(0);
            }
        }
    }
}
