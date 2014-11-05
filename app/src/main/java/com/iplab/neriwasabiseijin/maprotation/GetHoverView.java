package com.iplab.neriwasabiseijin.maprotation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by neriwasabiseijin on 2014/11/05.
 */
public class GetHoverView extends View implements View.OnHoverListener{
    public GetHoverView(Context context) {
        super(context);
        this.setOnHoverListener(this);
    }

    public GetHoverView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnHoverListener(this);
    }

    public GetHoverView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOnHoverListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //MapsActivity.rotateMap(90f);
        return false;
    }

    @Override
    public boolean onHover(View view, MotionEvent motionEvent) {
        return false;
    }
}
