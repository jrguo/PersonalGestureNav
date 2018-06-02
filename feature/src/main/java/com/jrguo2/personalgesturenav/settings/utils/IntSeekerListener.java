package com.jrguo2.personalgesturenav.settings.utils;

import android.widget.SeekBar;
import android.widget.TextView;

import com.jrguo2.personalgesturenav.utils.Configs;

public class IntSeekerListener extends SeekerListener {

    private int minValue;
    private int maxValue;
    private int currValue;

    public IntSeekerListener(String prefix, TextView view, String keyValue, int minValue, int maxValue) {
        super(prefix, view, keyValue);
        this.minValue = minValue;
        this.maxValue = maxValue;

        currValue = 0;
    }

    @Override
    public void updateKeyValue() {
        Configs.setInt(this.getKeyValue(), currValue);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        currValue = (int)((progress * 1.0 / 100 * (maxValue - minValue)) + minValue);
        textView.setText(this.prefix + ":\t" + currValue);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        updateKeyValue();
        Configs.OVERLAY_SERVICE.updateParamters();
    }
}
