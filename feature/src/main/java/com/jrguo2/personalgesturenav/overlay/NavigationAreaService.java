package com.jrguo2.personalgesturenav.overlay;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;

import com.jrguo2.personalgesturenav.feature.R;
import com.jrguo2.personalgesturenav.listeners.NavigationAreaTouchListener;
import com.jrguo2.personalgesturenav.utils.Configs;
import com.jrguo2.personalgesturenav.view.NavigationAreaView;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class NavigationAreaService extends AccessibilityService {

    //WM that holds our overlay
    private WindowManager windowsManager;

    //View that we draw
    public NavigationAreaView navigationAreaView;

    //On touch listener
    private NavigationAreaTouchListener navigationAreaTouchListener;

    //Nav area variables
    public int navAreaXOffset;
    public int navAreaYOffset;
    public int navAreaWidth;
    public int navAreaHeight;


    @Override
    public void onCreate() {
        super.onCreate();

        //Setup config files
        Configs.SHARED_PREFERENCE = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        Configs.SHARED_PREFERENCES_EDITOR = Configs.SHARED_PREFERENCE.edit();

        //Configs.SHARED_PREFERENCES_EDITOR.clear().commit();

        createAndAddNavArea();

        Configs.OVERLAY_SERVICE = this;
    }

    private void createAndAddNavArea(){
        navAreaWidth = Configs.getInt("navAreaWidth", 650);
        navAreaHeight = Configs.getInt("navAreaHeight", 70);
        navAreaXOffset = Configs.getInt("navAreaXOffset", 0);
        navAreaYOffset = Configs.getInt("navAreaYOffset", 20);

        //Let's create our overlay for the navigation gestures

        //Create parameters for the layout
        WindowManager.LayoutParams p = new WindowManager.LayoutParams(
                navAreaWidth, navAreaHeight,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSPARENT);

        p.gravity = Gravity.CENTER | Gravity.BOTTOM;
        p.x = navAreaXOffset;
        p.y = navAreaYOffset;

        if(windowsManager == null)
            windowsManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        if(navigationAreaView == null){
            navigationAreaView = new NavigationAreaView(this);
            navigationAreaTouchListener = new NavigationAreaTouchListener(this);
            navigationAreaView.setOnTouchListener(navigationAreaTouchListener);
        }
        else{
            long duration = Configs.getLong("timeBeforeFadeDuration", 1000);
            float holdDuration = Configs.getFloat("minLongHoldDuration", 1000);
            navigationAreaTouchListener.setMinHoldDuration(holdDuration);
            navigationAreaTouchListener.setTimeBeforeFade(duration);
        }

        ViewGroup.LayoutParams params = navigationAreaView.getLayoutParams();

        if(params == null) {
            navigationAreaView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        }

        //Try to remove existing view
        try{
            windowsManager.removeView(navigationAreaView);
        }
        catch(Exception e){ }

        //Add updated view to the WM
        windowsManager.addView(navigationAreaView, p);

        Log.i("Overlay", "Created overlay service display");
    }

    public void updateParameters(){
        createAndAddNavArea();
        navigationAreaView.updateParameters();
        navigationAreaView.showArea();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (navigationAreaView != null) {
            windowsManager.removeView(navigationAreaView);
            navigationAreaView = null;
        }
    }

    public void moveNavBar(int amount){
        try{

            Process result = Runtime.getRuntime().exec("ls");
            execCmd("whoami");
            Log.i("Navbar", "Overscan values:\t" );
        }
        catch(Exception e){
            Log.e("Navbar", "Error trying to move navbar; probably lacking permission granted.\n" + e.toString());
        }
    }

    public void execCmd(String cmd) throws java.io.IOException {
        Runtime rt = Runtime.getRuntime();
        Process proc = rt.exec(cmd);

        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(proc.getErrorStream()));

        // read the output from the command
        System.out.println("Here is the standard output of the command:\n");
        String s = null;
        while ((s = stdInput.readLine()) != null) {
            Log.v("Navbar", s);
        }

        // read any errors from the attempted command
        System.out.println("Here is the standard error of the command (if any):\n");
        while ((s = stdError.readLine()) != null) {
            Log.v("Navbar", s);
        }
        return;
    }


    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.i("Accessibility", "this was triggered");
    }

    @Override
    public void onInterrupt() {
        Log.i("Accessibility", "interrupt");
    }
}