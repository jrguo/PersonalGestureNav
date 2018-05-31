package com.jrguo2.personalgesturenav.listeners;

import android.accessibilityservice.AccessibilityService;
import android.view.MotionEvent;
import android.view.View;

import com.jrguo2.personalgesturenav.gestures.GesturesTypes;
import com.jrguo2.personalgesturenav.gestures.NavGestureListener;
import com.jrguo2.personalgesturenav.overlay.OverlayService;

public class TouchListener implements View.OnTouchListener {

    //Holds start positions for gestures
    private float prevX, prevY;
    private long prevTime;
    private NavGestureListener gestureHandler;
    private OverlayService accessibilityService;

    public TouchListener(OverlayService accessibilityService){
        prevX = 0f;
        prevY = 0f;
        prevTime = 0;

        this.accessibilityService = accessibilityService;
        gestureHandler = new NavGestureListener(accessibilityService);
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
            case MotionEvent.ACTION_UP: {
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
