package com.jrguo2.personalgesturenav.listeners.seekbarlisteners;

import android.widget.SeekBar;

import com.jrguo2.personalgesturenav.utils.Configs;

public class FloatSeekBarListener extends SeekBarListener {

    private float currValue;

    public FloatSeekBarListener(String keyValue) {
        super(keyValue);
        currValue = 0;
    }

    @Override
    public void updateKeyValue() {
        Configs.setFloat(this.getKeyValue(), currValue);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        currValue = progress;
        int width = seekBar.getWidth() - seekBar.getPaddingLeft() - seekBar.getPaddingRight();
        int thumbPos = (int) ( width * (1.0 * (seekBar.getProgress() - seekBar.getMin()) / (seekBar.getMax() - seekBar.getMin())));
        this.textDrawable.setOffsets(thumbPos, 0);
        seekBar.setThumb(this.getDrawableFromString(Integer.toString((int) currValue)));
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
