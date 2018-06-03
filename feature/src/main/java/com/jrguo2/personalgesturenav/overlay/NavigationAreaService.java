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

    private WindowManager windowsManager;
    public NavigationAreaView testView;
    public int xOffset;
    public int yOffset;
    public int areaWidth;
    public int areaHeight;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.i("Accessibility", "this was triggered");
    }

    @Override
    public void onInterrupt() {
        Log.i("Accessibility", "interrupt");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //Setup config files
        Configs.SHARED_PREFERENCE = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        Configs.SHARED_PREFERENCES_EDITOR = Configs.SHARED_PREFERENCE.edit();

        Configs.SHARED_PREFERENCES_EDITOR.clear().commit();

        createAndAddNavArea();

        Configs.OVERLAY_SERVICE = this;
    }

    private void createAndAddNavArea(){
        areaWidth = Configs.getInt("navAreaWidth", 650);
        areaHeight = Configs.getInt("navAreaHeight", 70);

        Log.i("Overlay", "Created overlay service display");

        //Let's create our overlay for the navigation gestures

        //Create parameters for the layout
        WindowManager.LayoutParams p = new WindowManager.LayoutParams(
                areaWidth, areaHeight,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSPARENT);

        xOffset = Configs.getInt("navAreaXOffset", 0);
        yOffset = Configs.getInt("navAreaYOffset", 20);

        p.gravity = Gravity.CENTER | Gravity.BOTTOM;
        p.x = xOffset;
        p.y = yOffset;

        if(windowsManager == null)
            windowsManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        if(testView == null){
            testView = new NavigationAreaView(this);
            testView.setOnTouchListener(new NavigationAreaTouchListener(this));
        }

        ViewGroup.LayoutParams params = testView.getLayoutParams();
        if(params == null)
            testView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        else{
            params.height = areaHeight;
            params.width = areaWidth;
            testView.setLayoutParams(params);
        }

        //Add view to the manager
        try{
            windowsManager.removeView(testView);
        }
        catch(Exception e){

        }
        windowsManager.addView(testView, p);

    }

    public void updateParamters(){
        createAndAddNavArea();
        testView.updateParameters();
        testView.showArea();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (testView != null) {
            windowsManager.removeView(testView);
            testView = null;
        }
    }

    public void moveNavbar(int amount){
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
}