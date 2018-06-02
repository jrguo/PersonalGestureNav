package com.jrguo2.personalgesturenav.actions;

import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;

import com.jrguo2.personalgesturenav.utils.Configs;

public abstract class Action {

    protected Context context;
    private String actionType;
    private String description;

    public abstract boolean performAction();

    protected void vibrate(){
        if(context == null)
            return;

        long dur = Configs.getLong("vib_duration", 10);
        int amp = Configs.getInt("vib_amplitude", 5);

        Vibrator vibrator = context.getSystemService(Vibrator.class);

        if(vibrator != null && vibrator.hasVibrator()){
            vibrator.cancel();
            vibrator.vibrate(VibrationEffect.createOneShot(dur, amp));
        }
    }

}
