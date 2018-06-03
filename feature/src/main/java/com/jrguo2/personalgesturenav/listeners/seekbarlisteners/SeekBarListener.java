package com.jrguo2.personalgesturenav.listeners.seekbarlisteners;

import android.graphics.drawable.Drawable;
import android.widget.SeekBar;

import com.jrguo2.personalgesturenav.view.TextDrawable;

public abstract class SeekBarListener implements SeekBar.OnSeekBarChangeListener {

    protected String keyValue;
    protected TextDrawable textDrawable;

    public SeekBarListener(String keyValue){
        this.keyValue = keyValue;
        textDrawable = new TextDrawable("");
    }

    public abstract void updateKeyValue();

    public String getKeyValue(){
        return keyValue;
    }


    public Drawable getDrawableFromString(String text){
        textDrawable.setText(text);
        return textDrawable;
    }

    public TextDrawable getTextDrawable() {
         return this.textDrawable;
    }
}
