package com.zzy.jackpot;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunfusheng.marqueeview.MarqueeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.OnTouch;

import static com.zzy.jackpot.JackpotDialog.INVALID_RES_ID;


/**
 * Created by zerozhao on 2016/12/11.
 */

public class JackpotActivity extends Activity {

    RecyclerView recycler1;
    RecyclerView recycler2;
    RecyclerView recycler3;
    ImageView btnPlay;
    MarqueeView tvScroll;
    TextView tvSpinNum;

    private static final int MSG_CODE_REQUEST= 0;
    private static final int MSG_CODE_WIN = 1;

    private JackpotDialog dialog;
    private boolean bPlay;
    private boolean bSucc;
    private JackpotResult winRetInfo;
    private int iSpinTimes = 100;
    private JackpotSound sound =  null;

    private JackpotAdapter[] autoScrollAdapter = new JackpotAdapter[3];
    JackpotLinearLayoutManger[] scrollSpeedLinearLayoutManger = new JackpotLinearLayoutManger[3];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jackpot_main_new);

        recycler1 = findViewById(R.id.recyler1);
        recycler2= findViewById(R.id.recyler2);
        recycler3= findViewById(R.id.recyler3);
        btnPlay= findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iSpinTimes < 1) {
                    //
                } else {
                    if (!bPlay) {
                        sound.playPressSpinButton();
                        bPlay = true;
                        playGame();
                        requestJackpot();

                        sound.playSpinLoop();
                    }
                }
            }
        });
        tvScroll= findViewById(R.id.tvScroll);
        tvSpinNum= findViewById(R.id.tvSpinNum);

        sound = new JackpotSound(this);

        initView();
        InitMarquee();
        setSpinNum(iSpinTimes);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        sound.stopAll();

        if(isFinishing()) {
            recycler1.stopScroll();
            recycler2.stopScroll();
            recycler3.stopScroll();
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        sound.destory();
        handler.removeMessages(MSG_CODE_WIN);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if(bPlay) {
                return true;
            }
            return super.onKeyUp(keyCode, event);
        }
        return super.onKeyUp(keyCode, event);
    }

    private void setSpinNum(int spinNum)
    {
        tvSpinNum.setText(String.valueOf(spinNum) + " Spins" );
    }

    public void InitMarquee() {
        List<String> info = new ArrayList<>();
        info.add("xiaoming Mark has won 20 Sms");
        info.add("xiaohong Mark has won 1GB data");
        info.add("xiaohua Mark has won 1 iPhoneX");
        info.add("xiaocao Mark has won 20 Sms");
        info.add("xiaoniu Mark has won 20 Sms");
        info.add("xiaoya Mark has won 1 xiaomi phone");
        tvScroll.startWithList(info);

        //tvScroll.startWithList(info, R.anim.anim_bottom_in, R.anim.anim_top_out);
    }

    public void initView() {

        for (int i = 0; i < scrollSpeedLinearLayoutManger.length; i++) {
            scrollSpeedLinearLayoutManger[i] = new JackpotLinearLayoutManger(this);
        }
        scrollSpeedLinearLayoutManger[0].setSpeed(2.0f);
        scrollSpeedLinearLayoutManger[1].setSpeed(3.0f);
        scrollSpeedLinearLayoutManger[2].setSpeed(1.0f);

        recycler1.setLayoutManager(scrollSpeedLinearLayoutManger[0]);
        recycler2.setLayoutManager(scrollSpeedLinearLayoutManger[1]);
        recycler3.setLayoutManager(scrollSpeedLinearLayoutManger[2]);

        for (int i = 0; i < 3; i++) {
            autoScrollAdapter[i] = new JackpotAdapter( JackpotActivity.this);
        }

        recycler1.setAdapter(autoScrollAdapter[0]);
        recycler2.setAdapter(autoScrollAdapter[1]);
        recycler3.setAdapter(autoScrollAdapter[2]);

        recycler2.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        sound.stopSpinLoop();
                        if (bSucc) {
                            popSucc();
                        }else{
                            sound.playSecondStop();
                        }
                        bPlay = false;
                        break;
                }
            }
        });

        recycler1.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        sound.playFristStop();
                        break;
                }
            }
        });


        recycler3.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        sound.playLastStop();
                        break;
                }
            }
        });
    }

    @OnTouch(R.id.llback)
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }

    private void playGame() {
        recycler1.smoothScrollToPosition(Integer.MAX_VALUE);
        recycler2.smoothScrollToPosition(Integer.MAX_VALUE);
        recycler3.smoothScrollToPosition(Integer.MAX_VALUE);
    }

    private int Rnd(int Min, int Max) {
        Random random = null;
        random = new Random();
        return Min + random.nextInt(Max - Min);
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what)
            {
                case MSG_CODE_WIN:
                    dealSpinAndWin(winRetInfo);
                    break;
                case MSG_CODE_REQUEST:

                    int succ = Rnd(0,2);
                    if(succ==0) {
                        winRetInfo = new JackpotResult();
                        winRetInfo.id = Rnd(0,7);
                        if(winRetInfo.id==JackpotAdapter.MAX_SIZE) {
                            winRetInfo.productName = "IphoneX";
                        }else{
                            winRetInfo.productName= "A free git";
                        }
                        iSpinTimes--;
                        setSpinNum(iSpinTimes);
                        handler.sendEmptyMessageDelayed(MSG_CODE_WIN,5000);
                    }else{
                        winRetInfo = null;
                        iSpinTimes--;
                        setSpinNum(iSpinTimes);
                        handler.sendEmptyMessageDelayed(MSG_CODE_WIN,5000);
                    }

                    break;
            }
            return false;
        }
    });

    public void requestJackpot() {
        handler.sendEmptyMessageDelayed(MSG_CODE_REQUEST,5000);
    }

    private  int getIndex(JackpotResult winRetInfo)
    {
        List<JackpotProduct> list = JackpotAdapter.getData();
        int count = list.size();
        if(winRetInfo.isGrandPrize)
        {
            return count-1;
        }


        for(int i=0;i<count;i++) {
            if (winRetInfo.id == list.get(i).getId())
            {
                return i;
            }
        }
        return 0;
    }

    private void dealSpinAndWin(JackpotResult winRetInfo)
    {
        int iCount = JackpotAdapter.getProductCount();
        int pos1 = scrollSpeedLinearLayoutManger[0].findFirstVisibleItemPosition();
        int pos2 = scrollSpeedLinearLayoutManger[1].findFirstVisibleItemPosition();
        int pos3 = scrollSpeedLinearLayoutManger[2].findFirstVisibleItemPosition();

        if(pos2<iCount)
        {
            handler.sendEmptyMessageDelayed(MSG_CODE_WIN,500);
            return;
        }

        int index = 0;
        if(winRetInfo==null)
        {
            bSucc = false;
            index = Rnd(0,iCount);
        }
        else
        {
            bSucc = true;
            index = getIndex(winRetInfo);
        }

        int newpos1 = scrollSpeedLinearLayoutManger[0].findFirstVisibleItemPosition() % iCount;
        int newpos2 = scrollSpeedLinearLayoutManger[1].findFirstVisibleItemPosition() % iCount;
        int newpos3 = scrollSpeedLinearLayoutManger[2].findFirstVisibleItemPosition() % iCount;

        if (newpos1 > index) {
            newpos1 = pos1 - (newpos1 - index);
        } else {
            newpos1 = pos1 - (iCount + (newpos1 - index));
        }

        if (newpos3 > index) {
            newpos3 = pos3 - (newpos3 - index);
        } else {
            newpos3 = pos3 - (iCount + (newpos3 - index));
        }


        if (bSucc) {

            if (newpos2 > index) {
                newpos2 = pos2 - (newpos2 - index);
            } else {
                newpos2 = pos2 - (iCount + (newpos2 - index));
            }

        } else {

            if (newpos2 > index) {
                newpos2 = pos2 - (newpos2 - index) - 1;
            } else {
                newpos2 = pos2 - (iCount + (newpos2 - index)) + 1;
            }
        }

        recycler1.smoothScrollToPosition(newpos1 - 1);
        recycler2.smoothScrollToPosition(newpos2 - 1);
        recycler3.smoothScrollToPosition(newpos3 - 1);
    }

    public void popSucc()
    {
        if(winRetInfo==null)
        {
            sound.playSecondStop();
            return;
        }

        if (dialog == null) {
            dialog = new JackpotDialog(this);
            dialog.setOnBUttonClickListen(new JackpotDialog.OnButtonClickListen() {
                @Override
                public boolean onButtonClick(boolean isCancel) {

                    //....
                    return true;
                }
        });
        }

        dialog.setBackground(R.mipmap.popup_winning_panel2);
        sound.playWinNormal();

        dialog.setPopuOkBg(R.mipmap.buttom_claim_now);
        dialog.setPopuCancelbg(INVALID_RES_ID);


        if(TextUtils.isEmpty(winRetInfo.productImage)) {
            dialog.setPopuIcon( JackpotAdapter.getData().get(getIndex(winRetInfo)).getiRes());
        }else {

//            GlideApp.with(this)
//                    .load(winRetInfo.productImage)
//                    .into(dialog.getPopuIcon());
        }
        dialog.setPopuText(winRetInfo.productName);
        dialog.show();
    }
}
