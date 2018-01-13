package com.roix.inhype;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by roix on 27.06.2017.
 */

public class SquareViewPager extends ViewPager {

    public SquareViewPager(Context context) {
        super(context);
    }
    public SquareViewPager(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onMeasure(int width, int height) {
        super.onMeasure(width, height);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        if (measuredWidth > measuredHeight) {
            setMeasuredDimension(measuredWidth, measuredWidth);
        } else {
            setMeasuredDimension(measuredHeight, measuredHeight);

        }

    }

}
