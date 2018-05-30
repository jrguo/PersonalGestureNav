package com.jrguo2.personalgesturenav.gestures;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class NavGestureListener {

    public static float minXDelta, minYDelta;
    public static float minDuration;

    public NavGestureListener(){
        minXDelta = 100;
        minYDelta = 100;
        minDuration = 450f;
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
        switch (action){
            case SHORT_LEFT:{
                Toast.makeText(v.getContext(), "Short Left", Toast.LENGTH_SHORT).show();
                return;
            }
            case LONG_LEFT:{
                Toast.makeText(v.getContext(), "Long Left", Toast.LENGTH_SHORT).show();
                return;
            }
            case SHORT_RIGHT:{
                Toast.makeText(v.getContext(), "Short Right", Toast.LENGTH_SHORT).show();
                return;
            }
            case LONG_RIGHT:{
                Toast.makeText(v.getContext(), "Long Right", Toast.LENGTH_SHORT).show();
                return;
            }
            case SHORT_UP: {
                Toast.makeText(v.getContext(), "Short Up", Toast.LENGTH_SHORT).show();
                return;
            }
            case LONG_UP:{
                Toast.makeText(v.getContext(), "Long Up", Toast.LENGTH_SHORT).show();
                return;
            }
            case SHORT_DOWN:{
                Toast.makeText(v.getContext(), "Short Down", Toast.LENGTH_SHORT).show();
                return;
            }
            case LONG_DOWN:{
                Toast.makeText(v.getContext(), "Long Down", Toast.LENGTH_SHORT).show();
                return;
            }
            case NONE:{
                Toast.makeText(v.getContext(), "Not recognized", Toast.LENGTH_SHORT).show();
                return;
            }
        }

    }

}
