package com.zzy.jackpot;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

/**
 * Created by zerozhao on 2016/12/11.
 */

public class jackpotRecyclerView extends RecyclerView {
    public jackpotRecyclerView(Context context) {
        super(context);
    }

    public jackpotRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public jackpotRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        DisplayMetrics dm = getResources().getDisplayMetrics();

        int iWidth = 254*dm.widthPixels/(160+154+254*3);
        int iHeight = getMeasuredHeight();

        setMeasuredDimension(iWidth, iHeight);
    }
}
