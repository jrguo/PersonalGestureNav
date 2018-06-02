package com.jrguo2.personalgesturenav.settings;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jrguo2.personalgesturenav.feature.R;
import com.jrguo2.personalgesturenav.settings.utils.FloatSeekBarListener;
import com.jrguo2.personalgesturenav.settings.utils.IntSeekerListener;
import com.jrguo2.personalgesturenav.settings.utils.LongSeekBarListener;
import com.jrguo2.personalgesturenav.utils.Configs;

public class NavAreaSettings extends Fragment {

    public static Fragment getInstance() {
        Bundle bundle = new Bundle();
        NavAreaSettings navAreaSettings = new NavAreaSettings();
        navAreaSettings.setArguments(bundle);
        return navAreaSettings;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_navarea, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = view.findViewById(R.id.navarea_width_text);

        //Navbar area width
        SeekBar navAreaWidthSeekBar = view.findViewById(R.id.navAreaWidthSeekBar);
        int min = 10;
        int max = 1248;
        int progress = (int)(((double)Configs.getInt("navAreaWidth", 100) * 100) / (max - min));
        textView.setText("Nav Area Width:\t" + Configs.getInt("navAreaWidth", 100));
        navAreaWidthSeekBar.setProgress(progress);
        navAreaWidthSeekBar.setOnSeekBarChangeListener(new IntSeekerListener("Nav Area Width", textView, "navAreaWidth", min, max));

        //Navbar area height
        textView = view.findViewById(R.id.navarea_height_text);
        SeekBar seekBar = view.findViewById(R.id.navAreaHeightSeekBar);
        min = 0;
        max = 200;
        progress = (int)(((double)Configs.getInt("navAreaHeight", 100) * 100) / (max - min));
        seekBar.setProgress(progress);
        textView.setText("Nav Area Height:\t" + Configs.getInt("navAreaHeight", 100));
        seekBar.setOnSeekBarChangeListener(new IntSeekerListener("Nav Area Height", textView, "navAreaHeight", min, max));

        //Navbar height
        textView = view.findViewById(R.id.navBarHeightText);
        seekBar= view.findViewById(R.id.navBarHeightSeekBar);
        min = 0;
        max = 100;
        progress = (int)(((double)Configs.getFloat("navBarHeight", 100) * 100) / (max - min));
        seekBar.setProgress(progress);
        textView.setText("Nav Bar Height:\t" + Configs.getFloat("navBarHeight", 100));
        seekBar.setOnSeekBarChangeListener(new FloatSeekBarListener("Nav Bar Height", textView, "navBarHeight", min, max));

        //Navbar radius
        textView = view.findViewById(R.id.navBarRadiusText);
        seekBar= view.findViewById(R.id.navBarRadiusSeekBar);
        min = 0;
        max = 100;
        progress = (int)(((double)Configs.getFloat("navBarRadius", 100) * 100) / (max - min));
        seekBar.setProgress(progress);
        textView.setText("Nav Bar Height:\t" + Configs.getFloat("navBarHeight", 100));
        seekBar.setOnSeekBarChangeListener(new FloatSeekBarListener("Nav Bar Radius", textView, "navBarRadius", min, max));

        //Nav-area x-offset
        textView = view.findViewById(R.id.navAreaXOffsetText);
        seekBar= view.findViewById(R.id.navAreaXOffsetSeekbar);
        min = 0;
        max = 50;
        progress = (int)(((double)Configs.getInt("navAreaXOffset", 100) * 100) / (max - min));
        seekBar.setProgress(progress);
        textView.setText("Nav Area X-Offset:\t" + Configs.getInt("navAreaXOffset", 100));
        seekBar.setOnSeekBarChangeListener(new IntSeekerListener("Nav Area X-Offset", textView, "navAreaXOffset", min, max));

        //Nav-area y-offset
        textView = view.findViewById(R.id.navAreaYOffsetText);
        seekBar= view.findViewById(R.id.navAreaYOffsetSeekbar);
        min = 0;
        max = 50;
        progress = (int)(((double)Configs.getInt("navAreaYOffset", 100) * 100) / (max - min));
        seekBar.setProgress(progress);
        textView.setText("Nav Area X-Offset:\t" + Configs.getInt("navAreaYOffset", 100));
        seekBar.setOnSeekBarChangeListener(new IntSeekerListener("Nav Area Y-Offset", textView, "navAreaYOffset", min, max));

        //Vibration intensity
        textView = view.findViewById(R.id.vibrateIntensityText);
        seekBar= view.findViewById(R.id.vibrateIntensitySeekbar);
        min = 0;
        max = 100;
        progress = (int)(((double)Configs.getInt("vib_amplitude", 5) * 100) / (max - min));
        seekBar.setProgress(progress);
        textView.setText("Vibration Intensity:\t" + Configs.getInt("vib_amplitude", 100));
        seekBar.setOnSeekBarChangeListener(new IntSeekerListener("Vibration Intensity", textView, "vib_amplitude", min, max));

        //Vibration length
        textView = view.findViewById(R.id.vibrateLengthText);
        seekBar= view.findViewById(R.id.vibrateLengthSeekbar);
        min = 0;
        max = 500;
        progress = (int)(((double)Configs.getInt("vib_duration", 5) * 100) / (max - min));
        seekBar.setProgress(progress);
        textView.setText("Vibration Length:\t" + Configs.getInt("vib_duration", 100));
        seekBar.setOnSeekBarChangeListener(new LongSeekBarListener("Vibration Length", textView, "vib_duration", min, max));

    }


}
