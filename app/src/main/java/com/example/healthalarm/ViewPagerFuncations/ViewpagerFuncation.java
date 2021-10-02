package com.example.healthalarm.ViewPagerFuncations;

import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import java.util.Timer;
import java.util.TimerTask;

public class ViewpagerFuncation {

    private static final float MIN_SCALE = 0.85f;
    private static final float MIN_ALPHA = 0.5f;
    final long DELAY_MS = 1000;
    final long PERIOD_MS = 4000;
    int currentPage = 0;
    Timer timer;

    public void setviewpager(ViewPager viewPager) {

        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());

        final Handler handler = new Handler();
        final Runnable Update = () -> {
            if (currentPage == 7) {
                currentPage = 0;
            }
            viewPager.setCurrentItem(currentPage++, true);
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
    }

    private static class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        @Override
        public void transformPage(@NonNull View page, float position) {

            int pageWidth = page.getWidth();
            int pageHeight = page.getHeight();

            if (position < -1) {
                page.setAlpha(0);
            } else if (position <= 1) {

                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;

                if (position < 0) {
                    page.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    page.setTranslationX(-horzMargin + vertMargin / 2);
                }
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);

                page.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else {
                page.setAlpha(0);
            }
        }
    }

}
