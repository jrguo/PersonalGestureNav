package com.jrguo2.personalgesturenav.listeners.seekbarlisteners;

import android.graphics.drawable.Drawable;
import android.widget.SeekBar;

import com.jrguo2.personalgesturenav.view.SeekBarTextDrawable;

public abstract class SeekBarListener implements SeekBar.OnSeekBarChangeListener {

    protected String keyValue;
    protected SeekBarTextDrawable seekBarTextDrawable;

    public SeekBarListener(String keyValue){
        this.keyValue = keyValue;
        seekBarTextDrawable = new SeekBarTextDrawable("");
    }

    public abstract void updateKeyValue();

    public String getKeyValue(){
        return keyValue;
    }


    public Drawable getDrawableFromString(String text){
        seekBarTextDrawable.setText(text);
        return seekBarTextDrawable;
    }

    public SeekBarTextDrawable getSeekBarTextDrawable() {
         return this.seekBarTextDrawable;
    }
}
