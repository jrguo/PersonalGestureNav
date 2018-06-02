package com.jrguo2.personalgesturenav.gestures;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jrguo2.personalgesturenav.actions.ActionManager;
import com.jrguo2.personalgesturenav.overlay.OverlayService;


public class NavGestureListener {

    public static float minXDelta, minYDelta;
    public static float minDuration;
    private OverlayService accessibilityService;
    private ActionManager actions;

    public NavGestureListener(OverlayService service){
        minXDelta = 100;
        minYDelta = 100;
        minDuration = 500f;

        this.accessibilityService = service;

        //Setup default actions
        actions = new ActionManager();
        actions.populateActionMapWithDefaults(accessibilityService);
    }

    public GesturesTypes getGestureType(float deltaX, float deltaY, float duration){
        Log.v("Gesture Recognition", "dx: " + deltaX + "\tdy: " + deltaY + "\tt: " + duration);
        //Vertical Movement
        if (Math.abs(deltaX) < Math.abs(deltaY)){
            if(Math.abs(deltaY) >= minYDelta){
                //Down
                if(deltaY > 0){
                    return duration < minDuration ? GesturesTypes.SHORT_DOWN : GesturesTypes.LONG_DOWN;
                }
                //Up
                else{
                    return duration < minDuration ? GesturesTypes.SHORT_UP : GesturesTypes.LONG_UP;
                }
            }
        }
        //Horizontal Movement
        else{
            if(Math.abs(deltaX) >= minXDelta){
                //Right
                if(deltaX > 0){
                    return duration < minDuration ? GesturesTypes.SHORT_RIGHT : GesturesTypes.LONG_RIGHT;
                }
                //Left
                else{
                    return duration < minDuration ? GesturesTypes.SHORT_LEFT : GesturesTypes.LONG_LEFT;
                }
            }

        }

        return GesturesTypes.NONE;
    }


    public void handleGesture(View v, GesturesTypes action){
        actions.getGestureAction(action).performAction();
    }

}
