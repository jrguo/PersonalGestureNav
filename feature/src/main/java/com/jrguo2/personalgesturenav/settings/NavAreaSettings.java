package com.jrguo2.personalgesturenav.settings;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jrguo2.personalgesturenav.feature.R;
import com.jrguo2.personalgesturenav.settings.utils.SeekerListener;
import com.jrguo2.personalgesturenav.utils.Configs;
import com.jrguo2.personalgesturenav.utils.Util;

import java.lang.reflect.Constructor;

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

        LinearLayout layout = view.findViewById(R.id.baseLinearLayout);

        //Navbar area width
        createAndAddSeekBarListener(layout , "Navigation Area Width", getString(R.string.navAreaWidthDescription),
                0, 1024, "navAreaWidth", Configs.getInt("navAreaWidth", 100),
                "com.jrguo2.personalgesturenav.settings.utils.IntSeekerListener");

        //Navbar area height
        createAndAddSeekBarListener(layout, "Navigation Area Height", getString(R.string.navAreaHeightDescription),
                0, 400, "navAreaHeight", Configs.getInt("navAreaHeight", 100),
                "com.jrguo2.personalgesturenav.settings.utils.IntSeekerListener");

        //Nav-area x-offset
        createAndAddSeekBarListener(layout, "Navigation Bar X-Offset", getString(R.string.navAreaXOffsetDescription),
                0, 100, "navAreaXOffset", Configs.getInt("navAreaXOffset", 100),
                "com.jrguo2.personalgesturenav.settings.utils.IntSeekerListener");

        //Nav-area y-offset
        createAndAddSeekBarListener(layout, "Navigation Bar Y-Offset", getString(R.string.navAreaYOffsetDescription),
                0, 100, "navAreaYOffset", Configs.getInt("navAreaYOffset", 100),
                "com.jrguo2.personalgesturenav.settings.utils.IntSeekerListener");

        //Navbar height
        createAndAddSeekBarListener(layout, "Navigation Bar Render Height", getString(R.string.navBarHeightDescription),
                0, 200, "navBarHeight", (int) Configs.getFloat("navBarHeight", 100),
                "com.jrguo2.personalgesturenav.settings.utils.FloatSeekBarListener");

        //Navbar radius
        createAndAddSeekBarListener(layout, "Navigation Bar Radius", getString(R.string.navBarRadiusDescription),
                0, 100, "navBarRadius", (int) Configs.getFloat("navBarRadius", 100),
                "com.jrguo2.personalgesturenav.settings.utils.FloatSeekBarListener");

        //Vibration intensity
        createAndAddSeekBarListener(layout, "Vibration Intensity", getString(R.string.vibrationIntensityDescription),
                0, 100, "vib_amplitude", Configs.getInt("vib_amplitude", 100),
                "com.jrguo2.personalgesturenav.settings.utils.IntSeekerListener");

        //Vibration length
        createAndAddSeekBarListener(layout, "Vibration Length", getString(R.string.vibrationLengthDescription),
                0, 500, "vib_duration", (int) Configs.getLong("vib_duration", 100),
                "com.jrguo2.personalgesturenav.settings.utils.LongSeekBarListener");
    }

    private void createAndAddSeekBarListener(LinearLayout layout, String prefix, String description, int min, int max,
                                             String keyValue, int currentValue, String className) {
        try{
            Class<?> classNameInstance = Class.forName(className);
            Constructor<?> cons = classNameInstance.getConstructor(String.class);
            SeekerListener seekerListener = (SeekerListener) cons.newInstance(keyValue);

            ViewGroup.LayoutParams lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, // Width of TextView
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            addTextArea(layout, keyValue, "TitleTextArea", prefix, R.style.SettingsSeekBarTitleTextAreaSettings, lp, 15,5,15,5);
            addTextArea(layout, keyValue, "DescriptionTextArea", description, R.style.SettingsSeekBarDescriptionTextAreaSettings, lp, 30,5,15,15);
            SeekBar seekBar = addSeekBar(layout, lp, keyValue, R.style.SettingsSeekBarStyle, 30,30,30,50);
            seekBar.setMin(min);
            seekBar.setMax(max);
            seekBar.setProgress(currentValue);
            addDivider(layout, Util.dpToPixels(30));
            seekBar.setOnSeekBarChangeListener(seekerListener);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

            int width =  displayMetrics.widthPixels - seekBar.getPaddingLeft() - seekBar.getPaddingRight();
            int thumbPos = (int) (width * (1.0 * (seekBar.getProgress() - seekBar.getMin()) / (seekBar.getMax() - seekBar.getMin())));
            seekerListener.getTextDrawable().setOffsets(thumbPos, 0);
            seekBar.setThumb(seekerListener.getDrawableFromString(Integer.toString(currentValue)));
        }
        catch(Exception e){
            Log.e("Settings UI", "Error setting up seekbar listeners.\n" + e.toString());
        }
    }

    private TextView addTextArea(LinearLayout layout, String name, String type, String text, int style, ViewGroup.LayoutParams lp, int...padding){
        TextView textView = new TextView(getActivity().getApplicationContext());
        textView.setLayoutParams(lp);
        textView.setTextAppearance(style);
        textView.setId((name + type).hashCode());
        textView.setText(text);
        layout.addView(textView);

        textView.setScrollBarStyle(style);

        Util.SCALE = (int) getResources().getDisplayMetrics().density;

        if(padding.length > 0)
            textView.setPadding(Util.dpToPixels(padding[0]),Util.dpToPixels(padding[1]), Util.dpToPixels(padding[2]),Util.dpToPixels(padding[3]));

        return textView;
    }

    private SeekBar addSeekBar(LinearLayout layout, ViewGroup.LayoutParams lp, String name, int style, int...padding){
        SeekBar seekBar = new SeekBar(getActivity().getApplicationContext());
        seekBar.setLayoutParams(lp);
        seekBar.setId((name + "SeekBar").hashCode());
        seekBar.setHapticFeedbackEnabled(true);
        seekBar.setScrollBarStyle(style);

        if(padding.length > 0)
            seekBar.setPadding(Util.dpToPixels(padding[0]),Util.dpToPixels(padding[1]), Util.dpToPixels(padding[2]),Util.dpToPixels(padding[3]));
        layout.addView(seekBar);
        return seekBar;
    }

    private void addDivider(LinearLayout layout, int height){
        View viewDivider = new View(getActivity().getApplicationContext());
        viewDivider.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        layout.addView(viewDivider);

    }
}
