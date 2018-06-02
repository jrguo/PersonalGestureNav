package com.jrguo2.personalgesturenav.settings.utils;

import android.graphics.drawable.Drawable;
import android.widget.SeekBar;
import android.widget.TextView;

public abstract class SeekerListener implements SeekBar.OnSeekBarChangeListener {

    protected String keyValue;
    protected TextDrawable textDrawable;

    public SeekerListener(String keyValue){
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
