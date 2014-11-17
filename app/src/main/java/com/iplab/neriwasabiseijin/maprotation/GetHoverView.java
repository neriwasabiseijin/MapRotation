package com.iplab.neriwasabiseijin.maprotation;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by neriwasabiseijin on 2014/11/05.
 */
public class GetHoverView extends View implements View.OnHoverListener{
    private final long THRESHOLD = 600;

    private long startTime;
    private boolean isFirstHover;
    private boolean isFirstClossing;
    private MyTimerTask timerTask;
    Timer mTimer;
    private Handler mHandler;

    public GetHoverView(Context context) {
        super(context);
        init(context);
    }

    public GetHoverView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GetHoverView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.setOnHoverListener(this);

        isFirstHover = true;
        timerTask = null;
        mTimer = null;
        mHandler = new Handler();

    }

    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            mHandler.post(new Runnable(){
                public void run(){Shift();}
            });
        }
    }

    void Shift() {

        MapsActivity.rotateMap(90f);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        isFirstClossing = false;//タッチイベントが起こったら終了
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.v("onTouchEvent", "ACTION_DOWN");
                if(mTimer != null) {
                    mTimer.cancel(); //shiftさせない
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.v("onTouchEvent","ACTION_UP");
                isFirstHover = false;//タッチアップ後はホバーが起こってもshift命令は飛ばさない
                break;
        }

        return false;
    }

    @Override
    public boolean onHover(View view, MotionEvent motionEvent) {
        switch(motionEvent.getAction()) {
            case MotionEvent.ACTION_HOVER_ENTER:
                if(isFirstHover) {
                    startTime = System.currentTimeMillis();
                    isFirstHover = false;
                }
                break;
            case MotionEvent.ACTION_HOVER_MOVE:
                if(isFirstHover){
                    startTime = System.currentTimeMillis();
                    isFirstHover = false;
                }
                break;
            case MotionEvent.ACTION_HOVER_EXIT:
                long stopTime = System.currentTimeMillis();
                long b = stopTime - startTime;
                Log.v("onHover", "Action_Hover_Exit" + "stopTime-startTime :" + b);
                if(THRESHOLD > b){
                    if(isFirstClossing){
                        //タイマーの初期化処理
                        timerTask = new MyTimerTask();
                        mTimer = new Timer(true);
                        //0.05秒後に実行．その前にタッチイベントが起こればキャンセルさせるため．
                        mTimer.schedule(timerTask,50);
                    }
                    isFirstClossing = true;
                    //タイマーの初期化処理
                    //timerTask = new MyTimerTask();
                    //mTimer = new Timer(true);
                    //0.05秒後に実行．その前にタッチイベントが起こればキャンセルさせるため．
                    //mTimer.schedule(timerTask,50);

                    //フツーに実行
                    //Shift();
                }
                startTime = 0;
                isFirstHover = true;
                break;
        }
        return false;
    }
}
