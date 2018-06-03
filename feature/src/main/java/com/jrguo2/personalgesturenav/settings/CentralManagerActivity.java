package com.jrguo2.personalgesturenav.settings;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;
import com.jrguo2.personalgesturenav.feature.R;
import com.jrguo2.personalgesturenav.utils.Configs;

public class CentralManagerActivity extends AppCompatActivity implements ColorPickerDialogListener{

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onColorSelected(int dialogId, int color) {
        Log.i("Color", Integer.toHexString(color));
        if(dialogId == Configs.COLOR_PICKER_AREA){
            Configs.setString("pillColor", "#" + Integer.toHexString(color));
            Configs.OVERLAY_SERVICE.updateParameters();
            NavigationAreaSettingsFragment.pillColorPanel.setColor(color);
        }
        if(dialogId == Configs.COLOR_PICKER_PILL){
            Configs.setString("outlineColor","#" +  Integer.toHexString(color));
            Configs.OVERLAY_SERVICE.updateParameters();
            NavigationAreaSettingsFragment.outlineColorPanel.setColor(color);
        }
    }

    @Override
    public void onDialogDismissed(int dialogId) {

    }
}