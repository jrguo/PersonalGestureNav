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
    private NavGestureHandler gestureHandler;
    private NavigationAreaService accessibilityService;

    public NavigationAreaTouchListener(NavigationAreaService accessibilityService){
        prevX = 0f;
        prevY = 0f;
        prevTime = 0;

        timeBeforeFade = Configs.getLong("timeBeforeFadeDuration", 1000);

        this.accessibilityService = accessibilityService;
        gestureHandler = new NavGestureHandler(accessibilityService);
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

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        accessibilityService.navigationAreaView.hide();
                    }
                }, timeBeforeFade);
                return true;
            }
        }
        return false;
    }
}
