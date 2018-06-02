package com.jrguo2.personalgesturenav.settings.utils;

import android.widget.SeekBar;
import android.widget.TextView;

import com.jrguo2.personalgesturenav.utils.Configs;

public class LongSeekBarListener extends SeekerListener {

    private long minValue;
    private long maxValue;
    private long currValue;

    public LongSeekBarListener(String prefix, TextView view, String keyValue, long minValue, long maxValue) {
        super(prefix, view, keyValue);
        this.minValue = minValue;
        this.maxValue = maxValue;

        currValue = 0;
    }

    @Override
    public void updateKeyValue() {
        Configs.setLong(this.getKeyValue(), currValue);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        currValue = (long)((progress * 1.0 / 100 * (maxValue - minValue)) + minValue);
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
