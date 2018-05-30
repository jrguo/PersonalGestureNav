package com.jrguo2.personalgesturenav.listeners;

import android.view.MotionEvent;
import android.view.View;

import com.jrguo2.personalgesturenav.gestures.GesturesTypes;
import com.jrguo2.personalgesturenav.gestures.NavGestureListener;

public class TouchListener implements View.OnTouchListener {

    //Holds start positions for gestures
    private float prevX, prevY;
    private long prevTime;
    private NavGestureListener gestureHandler;

    public TouchListener(){
        prevX = 0f;
        prevY = 0f;
        prevTime = 0;

        gestureHandler = new NavGestureListener();

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();

        switch (action){
            case MotionEvent.ACTION_DOWN: {
                prevX = event.getX();
                prevY = event.getY();
                prevTime = System.currentTimeMillis();
                return true;
            }
            case MotionEvent.ACTION_UP:{
                float currX = event.getX();
                float currY = event.getY();
                float dur = System.currentTimeMillis() - prevTime;

                float deltaX = currX - prevX;
                float deltaY = currY - prevY;

                GesturesTypes gesture = gestureHandler.getGestureType(deltaX, deltaY, dur);
                gestureHandler.handleGesture(v, gesture);
                return true;
            }
        }
        return false;
    }
}
