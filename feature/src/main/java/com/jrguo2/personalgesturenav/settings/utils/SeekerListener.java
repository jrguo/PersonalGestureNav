package com.jrguo2.personalgesturenav.settings.utils;

import android.widget.SeekBar;
import android.widget.TextView;

public abstract class SeekerListener implements SeekBar.OnSeekBarChangeListener {

    protected String prefix;
    protected TextView textView;
    protected String keyValue;

    public SeekerListener(String prefix, TextView view, String keyValue){
        this.prefix = prefix;
        this.textView = view;
        this.keyValue = keyValue;
        textView.setText(prefix + ":\t");
    }

    public abstract void updateKeyValue();

    public String getKeyValue(){
        return keyValue;
    }


}
