package com.jrguo2.personalgesturenav.gestures;

import android.util.Log;
import android.view.View;

import com.jrguo2.personalgesturenav.actions.ActionManager;
import com.jrguo2.personalgesturenav.overlay.NavigationAreaService;
import com.jrguo2.personalgesturenav.utils.Configs;


public class NavGestureHandler {

    public static float minXDelta, minYDelta;
    public static float minDuration;
    private NavigationAreaService accessibilityService;
    private ActionManager actions;

    public NavGestureHandler(NavigationAreaService service){
        minXDelta = Configs.getFloat("minXPosMov", 100);
        minYDelta = Configs.getFloat("minYPosMov", 35);
        minDuration = Configs.getFloat("minLongHoldDuration", 500);

        this.accessibilityService = service;

        //Setup default actions
        actions = new ActionManager();
        actions.createStringToActionMap(accessibilityService);
        actions.populateActionMapWithDefaults();
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
