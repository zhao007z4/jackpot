package com.zzy.jackpot;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ImageView;

/**
 * Created by zerozhao on 2017/12/19.
 */

public class JackpotImage extends ImageView {
    public JackpotImage(Context context) {
        super(context);
    }

    public JackpotImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JackpotImage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        DisplayMetrics dm = getResources().getDisplayMetrics();

        int iWidth = (dm.heightPixels*630)/(1920*3);
        int iNewWidth = dm.widthPixels*254/1080;

        iWidth = iWidth>iNewWidth?iNewWidth:iWidth;

        setMeasuredDimension(iWidth, iWidth);
    }
}
