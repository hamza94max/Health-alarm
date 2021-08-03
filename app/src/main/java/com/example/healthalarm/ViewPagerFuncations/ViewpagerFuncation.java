package com.example.healthalarm.ViewPagerFuncations;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import com.tmall.ultraviewpager.UltraViewPager;

public class ViewpagerFuncation {


    public void setviewpager(UltraViewPager viewPager){

        viewPager.setInfiniteLoop(true);
        viewPager.setAutoScroll(5500);

        viewPager.initIndicator();
        viewPager.getIndicator()
                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusColor(Color.GREEN)
                .setNormalColor(Color.WHITE)
                .setRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5,viewPager.getResources().getDisplayMetrics()));

        viewPager.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        viewPager.getIndicator().build();

    }













}
