package com.jrguo2.personalgesturenav.listeners.seekbarlisteners;

import android.widget.SeekBar;

import com.jrguo2.personalgesturenav.utils.Configs;

public class IntegerSeekBarListener extends SeekBarListener {

    private int currValue;

    public IntegerSeekBarListener(String keyValue) {
        super(keyValue);
        currValue = 0;
    }

    @Override
    public void updateKeyValue() {
        Configs.setInt(this.getKeyValue(), currValue);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        currValue = progress;

        int width = seekBar.getWidth() - seekBar.getPaddingLeft() - seekBar.getPaddingRight();
        int thumbPos =(int) (width * (1.0 * (seekBar.getProgress() - seekBar.getMin()) / (seekBar.getMax() - seekBar.getMin())));
        this.seekBarTextDrawable.setOffsets(thumbPos, 0);
        seekBar.setThumb(this.getDrawableFromString(Integer.toString((currValue))));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        updateKeyValue();
        Configs.OVERLAY_SERVICE.updateParameters();
    }
}
