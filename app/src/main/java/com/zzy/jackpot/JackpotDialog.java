package com.zzy.jackpot;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Created by zerozhao on 2016/12/11.
 */

public class JackpotDialog extends Dialog implements View.OnClickListener {

    public final static int INVALID_RES_ID = -1;
    private ImageView imgvPopuIcon;
    private TextView tvPopuText;
    private Button btnPopuOk;
    private Button btnPopuCancel;
    private LinearLayout llPopuParent;
    private OnButtonClickListen onButtonClickListen;

    public JackpotDialog(Context context)
    {
        this(context, R.style.CustomDialog);
    }

    public JackpotDialog(Context context, int theme) {
        super(context, theme);

        DisplayMetrics ds = context.getResources().getDisplayMetrics();
        float fDpi = ds.density;

        int statusBarHeight = -1;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }

        View view = LayoutInflater.from(context).inflate(R.layout.jackpot_pop, null);
        int iWidth = (int)(ds.widthPixels-40*fDpi);
        int iHeight = ds.heightPixels-statusBarHeight;
        ViewGroup.LayoutParams lp= new ViewGroup.LayoutParams(iWidth, iHeight);
        setContentView(view,lp);


        imgvPopuIcon = view.findViewById(R.id.imgvPopuIcon);
        tvPopuText = view.findViewById(R.id.tvPopuText);
        btnPopuCancel = view.findViewById(R.id.btnPopuCancel);
        btnPopuCancel.setOnClickListener(this);
        btnPopuOk = view.findViewById(R.id.btnPopuOk);
        btnPopuOk.setOnClickListener(this);
        llPopuParent = view.findViewById(R.id.llPopuParent);
    }

    public void setOnBUttonClickListen(OnButtonClickListen onButtonClickListen)
    {
        this.onButtonClickListen = onButtonClickListen;
    }

    public void setBackground(int iRes)
    {
        llPopuParent.setBackgroundResource(iRes);
    }

    public void setPopuOkBg(int iRes)
    {
        if(iRes !=INVALID_RES_ID) {
            btnPopuOk.setBackgroundResource(iRes);
            btnPopuOk.setVisibility(View.VISIBLE);
        }else
        {
            btnPopuOk.setVisibility(View.GONE);
        }
    }

    public void setPopuCancelbg(int iRes)
    {
        if(iRes !=INVALID_RES_ID) {
            btnPopuCancel.setBackgroundResource(iRes);
            btnPopuCancel.setVisibility(View.VISIBLE);
        }else
        {
            btnPopuCancel.setVisibility(View.GONE);
        }
    }

    public void setPopuIcon(Bitmap bitmap)
    {
        imgvPopuIcon.setImageBitmap(bitmap);
    }

    public void setPopuIcon(int res)
    {
        imgvPopuIcon.setImageResource(res);
    }

    public void setPopuText(int iRes)
    {
        tvPopuText.setText(iRes);
    }

    public void setPopuText(String str)
    {
        tvPopuText.setText(str);
    }

    public ImageView getPopuIcon()
    {
        return imgvPopuIcon;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnPopuOk:
                boolean bRet = false;
                if(onButtonClickListen !=null)
                {
                    if(onButtonClickListen.onButtonClick(false))
                    {
                        dismiss();
                    }
                }
                break;
            case R.id.btnPopuCancel:
                if(onButtonClickListen !=null)
                {
                    if(onButtonClickListen.onButtonClick(true))
                    {
                        dismiss();
                    }
                }
                break;
            default:
                break;
        }
    }

    public interface OnButtonClickListen
    {
        public boolean onButtonClick(boolean bCancel);
    }

}
