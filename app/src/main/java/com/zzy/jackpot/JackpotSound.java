package com.zzy.jackpot;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 * Created by zerozhao on 2016/12/11.
 */

public class JackpotSound {

    private SoundPool soundPool;
    private  Context context;
    private int soundIdFrist;
    private int soundIdSecond;
    private int soundIdLast;
    private int soundIdPressButton;
    private int soundIdLoop;
    private int soundLoopStreamID;
    private int soundIdWin;

    public JackpotSound(Context context)
    {
        soundPool = new SoundPool(2,AudioManager.STREAM_MUSIC, 0);
        this.context = context.getApplicationContext();

//        soundIdFrist = -1;
//        soundIdSecond = -1;
//        soundIdLast =-1;
//        soundIdPressButton= -1;
//        soundIdLoop=-1;
//        soundIdWinNormal = -1;
//        soundIdWinGrand = -1;

        //load on create. if don't load, may be can't hear sound
        soundIdFrist = soundPool.load(context, R.raw.first_stop, 11);
        soundIdSecond = soundPool.load(context, R.raw.second_stop, 13);
        soundIdLast = soundPool.load(context, R.raw.last_stop, 12);
        soundIdPressButton = soundPool.load(context, R.raw.press_button, 9);
        soundIdWin = soundPool.load(context, R.raw.winning, 14);
        soundIdLoop = soundPool.load(context, R.raw.spin_loop, 10);
        soundLoopStreamID = 0;
    }

    private int playSound(int id,int res,int pro)
    {
        return playSound(id,res,pro,false);
    }

    private int playSound(int id,int res,int pro,boolean bLoop)
    {
        if(soundPool == null)
        {
            return -1;
        }

        if(id == -1)
        {
            id = soundPool.load(context, res, pro);
        }

        int streamID = 0;
        if(bLoop) {
            streamID =  soundPool.play(id, 1, 1, 0, -1, 1);
        }else{
            streamID = soundPool.play(id, 1, 1, 0, 0, 1);
        }

        if(res == R.raw.spin_loop)
        {
            soundLoopStreamID =streamID;
        }
        return id;
    }

    public void playFristStop()
    {
        soundIdFrist = playSound(soundIdFrist,R.raw.first_stop, 1);
    }

    public void playSecondStop()
    {
        soundIdSecond = playSound(soundIdSecond,R.raw.second_stop, 3);
    }

    public void playLastStop()
    {
        soundIdLast = playSound(soundIdLast,R.raw.last_stop, 2);
    }

    public void playPressSpinButton()
    {
        soundIdPressButton = playSound(soundIdPressButton,R.raw.press_button, 5);
    }

    public void playSpinLoop()
    {
        soundLoopStreamID = 0;
        soundIdLoop= playSound(soundIdLoop,R.raw.spin_loop, 4,true);
    }

    public void playWinNormal()
    {
        soundIdWin = playSound(soundIdWin,R.raw.winning, 6);
    }


    public void stopSpinLoop()
    {
        if(soundPool ==null)
        {
            return;
        }

        if(soundLoopStreamID !=0) {
            soundPool.stop(soundLoopStreamID);
        }

        soundLoopStreamID = 0;
    }

    public void stopAll()
    {
        if(soundPool ==null)
        {
            return;
        }
        soundPool.autoPause();
    }


    public void destory()
    {
        soundPool.release();
        soundPool = null;
    }

}
