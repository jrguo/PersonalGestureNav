package com.jrguo2.personalgesturenav.settings;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jaredrummler.android.colorpicker.ColorPanelView;
import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jrguo2.personalgesturenav.feature.R;
import com.jrguo2.personalgesturenav.listeners.seekbarlisteners.SeekBarListener;
import com.jrguo2.personalgesturenav.utils.Configs;
import com.jrguo2.personalgesturenav.utils.Util;

import java.lang.reflect.Constructor;

public class NavigationAreaSettingsFragment extends Fragment {

    public String color;
    public static Fragment INSTANCE;
    public static ColorPanelView pillColorPanel;
    public static ColorPanelView outlineColorPanel;

    public static Fragment getInstance() {
        if(INSTANCE != null)
            return INSTANCE;

        Bundle bundle = new Bundle();
        NavigationAreaSettingsFragment navigationAreaSettingsFragment = new NavigationAreaSettingsFragment();
        navigationAreaSettingsFragment.setArguments(bundle);
        INSTANCE = navigationAreaSettingsFragment;
        return navigationAreaSettingsFragment;
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
        createSeekBarChanger(layout , "Navigation Area Width", getString(R.string.navAreaWidthDescription),
                0, 1024, "navAreaWidth", Configs.getInt("navAreaWidth", 100),
                "com.jrguo2.personalgesturenav.listeners.seekbarlisteners.IntegerSeekBarListener");

        //Navbar area height
        createSeekBarChanger(layout, "Navigation Area Height", getString(R.string.navAreaHeightDescription),
                0, 400, "navAreaHeight", Configs.getInt("navAreaHeight", 100),
                "com.jrguo2.personalgesturenav.listeners.seekbarlisteners.IntegerSeekBarListener");

        //Nav-area x-offset
        createSeekBarChanger(layout, "Navigation Bar X-Offset", getString(R.string.navAreaXOffsetDescription),
                0, 100, "navAreaXOffset", Configs.getInt("navAreaXOffset", 100),
                "com.jrguo2.personalgesturenav.listeners.seekbarlisteners.IntegerSeekBarListener");

        //Nav-area y-offset
        createSeekBarChanger(layout, "Navigation Bar Y-Offset", getString(R.string.navAreaYOffsetDescription),
                0, 100, "navAreaYOffset", Configs.getInt("navAreaYOffset", 100),
                "com.jrguo2.personalgesturenav.listeners.seekbarlisteners.IntegerSeekBarListener");

        //Navbar height
        createSeekBarChanger(layout, "Navigation Bar Render Height", getString(R.string.navBarHeightDescription),
                0, 200, "navBarHeight", (int) Configs.getFloat("navBarHeight", 100),
                "com.jrguo2.personalgesturenav.listeners.seekbarlisteners.FloatSeekBarListener");

        //Navbar radius
        createSeekBarChanger(layout, "Navigation Bar Radius", getString(R.string.navBarRadiusDescription),
                0, 100, "navBarRadius", (int) Configs.getFloat("navBarRadius", 100),
                "com.jrguo2.personalgesturenav.listeners.seekbarlisteners.FloatSeekBarListener");

        //Vibration intensity
        createSeekBarChanger(layout, "Vibration Intensity", getString(R.string.vibrationIntensityDescription),
                0, 100, "vib_amplitude", Configs.getInt("vib_amplitude", 100),
                "com.jrguo2.personalgesturenav.listeners.seekbarlisteners.IntegerSeekBarListener");

        //Vibration length
        createSeekBarChanger(layout, "Vibration Length", getString(R.string.vibrationLengthDescription),
                0, 500, "vib_duration", (int) Configs.getLong("vib_duration", 100),
                "com.jrguo2.personalgesturenav.listeners.seekbarlisteners.LongSeekBarListener");

        //Fade duration length
        createSeekBarChanger(layout, "Time Before Fade", getString(R.string.timeDurationBeforeFadeDescription),
                0, 5000, "timeBeforeFadeDuration", (int) Configs.getLong("timeBeforeFadeDuration", 100),
                "com.jrguo2.personalgesturenav.listeners.seekbarlisteners.LongSeekBarListener");

        pillColorPanel = createColorChanger(layout, "Navigation Area Primary Color", getString(R.string.navAreaPrimaryColorDescription),
                "pillColor", Configs.COLOR_PICKER_AREA);

        outlineColorPanel = createColorChanger(layout, "Navigation Area Secondary Color", getString(R.string.navAreaSecondaryColorDescription),
                "outlineColor", Configs.COLOR_PICKER_PILL);

        createSeekBarChanger(layout, "Navigation Bar Thickness", getString(R.string.timeDurationBeforeFadeDescription),
                0, 10, "navBarOutlineThickness", (int) Configs.getLong("navBarOutlineThickness", 5),
                "com.jrguo2.personalgesturenav.listeners.seekbarlisteners.FloatSeekBarListener");
    }

    private ColorPanelView createColorChanger(LinearLayout layout, String prefix, String description, String keyValue, final int id){
        ViewGroup.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, // Width of TextView
                ViewGroup.LayoutParams.WRAP_CONTENT);

        addTextArea(layout, keyValue, "TitleTextArea", prefix, R.style.SettingsSeekBarTitleTextAreaSettings, lp, 15,5,15,5);
        addTextArea(layout, keyValue, "DescriptionTextArea", description, R.style.SettingsSeekBarDescriptionTextAreaSettings, lp, 30,5,15,15);

        final ColorPanelView colorPanelView = new ColorPanelView(getActivity().getApplicationContext());
        colorPanelView.setColor(Color.parseColor(Configs.getString(keyValue, "#FFFFFF")));
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(200, ViewGroup.LayoutParams.WRAP_CONTENT);
        p.gravity = Gravity.CENTER;
        colorPanelView.setLayoutParams(p);
        colorPanelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPickerDialog.newBuilder()
                        .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                        .setAllowPresets(false)
                        .setDialogId(id)
                        .setColor(colorPanelView.getColor())
                        .setShowAlphaSlider(true)
                        .show(getActivity());
            }
        });

        layout.addView(colorPanelView);
        addDivider(layout, Util.dpToPixels(30));

        return colorPanelView;
    }

    private void createSeekBarChanger(LinearLayout layout, String prefix, String description, int min, int max,
                                      String keyValue, int currentValue, String className) {
        try{
            Class<?> classNameInstance = Class.forName(className);
            Constructor<?> cons = classNameInstance.getConstructor(String.class);
            SeekBarListener seekBarListener = (SeekBarListener) cons.newInstance(keyValue);

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
            seekBar.setOnSeekBarChangeListener(seekBarListener);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

            int width =  displayMetrics.widthPixels - seekBar.getPaddingLeft() - seekBar.getPaddingRight();
            int thumbPos = (int) (width * (1.0 * (seekBar.getProgress() - seekBar.getMin()) / (seekBar.getMax() - seekBar.getMin())));
            seekBarListener.getSeekBarTextDrawable().setOffsets(thumbPos, 0);
            seekBar.setThumb(seekBarListener.getDrawableFromString(Integer.toString(currentValue)));
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
