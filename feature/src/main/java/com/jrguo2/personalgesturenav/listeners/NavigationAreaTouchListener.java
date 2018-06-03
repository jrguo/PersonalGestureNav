package com.jrguo2.personalgesturenav.listeners;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.jrguo2.personalgesturenav.gestures.GesturesTypes;
import com.jrguo2.personalgesturenav.gestures.NavGestureHandler;
import com.jrguo2.personalgesturenav.overlay.NavigationAreaService;
import com.jrguo2.personalgesturenav.utils.Configs;

public class NavigationAreaTouchListener implements View.OnTouchListener {

    //Holds start positions for gestures
    private float prevX, prevY;
    private long timeBeforeFade;
    private long prevTime;

    private Handler longCheck;
    private boolean actionCheck;
    private boolean startHoldTimer;
    private GesturesTypes prevGesture;

    private NavGestureHandler gestureHandler;
    private NavigationAreaService accessibilityService;

    public NavigationAreaTouchListener(NavigationAreaService accessibilityService){
        prevX = 0f;
        prevY = 0f;
        prevTime = 0;

        actionCheck = false;
        startHoldTimer = false;

        timeBeforeFade = Configs.getLong("timeBeforeFadeDuration", 1000);

        longCheck = new Handler();

        this.accessibilityService = accessibilityService;
        gestureHandler = new NavGestureHandler(accessibilityService);
    }

    public void setMinHoldDuration(float length){
        this.gestureHandler.minDuration = length;
    }

    public void setTimeBeforeFade(long value){
        this.timeBeforeFade = value;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();

        switch (action){
            case MotionEvent.ACTION_DOWN: {
                prevX = event.getX();
                prevY = event.getY();
                prevTime = System.currentTimeMillis();

                //Make the pill visible
                accessibilityService.navigationAreaView.showArea();
                actionCheck = true;

                //Check again in

                return true;
            }
            case MotionEvent.ACTION_MOVE:{
                if(!actionCheck)
                    return false;

                float currX = event.getX();
                float currY = event.getY();

                float deltaX = currX - prevX;
                float deltaY = currY - prevY;

                if(Math.abs(deltaX) >= NavGestureHandler.minXDelta || Math.abs(deltaY) >= NavGestureHandler.minYDelta && !startHoldTimer){
                    startHoldTimer = true;
                    prevTime = System.currentTimeMillis();
                    checkLongHold(v, deltaX, deltaY);
                    prevGesture = gestureHandler.getGestureType(deltaX, deltaY, 0);
                }
                else if (Math.abs(deltaX) >= NavGestureHandler.minXDelta || Math.abs(deltaY) >= NavGestureHandler.minYDelta
                        && startHoldTimer){
                    GesturesTypes currGesture = gestureHandler.getGestureType(deltaX, deltaY, 0);
                    if(currGesture != prevGesture){
                        longCheck.removeCallbacksAndMessages(null);
                        checkLongHold(v, deltaX, deltaY);
                    }
                    prevTime = System.currentTimeMillis();
                }

                float dur = System.currentTimeMillis() - prevTime;

                GesturesTypes gesture = gestureHandler.getGestureType(deltaX, deltaY, dur);

                if(GesturesTypes.LONG_LEFT == gesture || GesturesTypes.LONG_UP == gesture ||
                        GesturesTypes.LONG_RIGHT == gesture || GesturesTypes.LONG_DOWN == gesture){
                    gestureHandler.handleGesture(v, gesture);
                    startHoldTimer = false;
                    actionCheck = false;
                    hideNavBar();
                    return false;
                }

                return true;

            }
            case MotionEvent.ACTION_UP: {
                if(longCheck != null){
                    longCheck.removeCallbacksAndMessages(null);
                }

                if(!actionCheck)
                    return true;

                float currX = event.getX();
                float currY = event.getY();
                float dur = System.currentTimeMillis() - prevTime;

                float deltaX = currX - prevX;
                float deltaY = currY - prevY;

                GesturesTypes gesture = gestureHandler.getGestureType(deltaX, deltaY, dur);
                gestureHandler.handleGesture(v, gesture);

                startHoldTimer = false;
                actionCheck = false;
                hideNavBar();

                return true;
            }
        }
        return false;
    }

    private void checkLongHold(final View v, final float deltaX, final float deltaY){
        longCheck.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!actionCheck)
                    return;

                GesturesTypes gesture = gestureHandler.getGestureType(deltaX, deltaY, 1000000);
                gestureHandler.handleGesture(v, gesture);

                startHoldTimer = false;
                actionCheck = false;
                hideNavBar();
            }
        }, (long) NavGestureHandler.minDuration);

        return;
    }

    private void hideNavBar(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                accessibilityService.navigationAreaView.hide();
            }
        }, timeBeforeFade);
    }
}
