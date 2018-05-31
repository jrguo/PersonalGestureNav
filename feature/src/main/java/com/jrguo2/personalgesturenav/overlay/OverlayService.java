package com.jrguo2.personalgesturenav.overlay;

import android.accessibilityservice.AccessibilityService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Button;
import android.widget.Toast;

import com.jrguo2.personalgesturenav.gestures.NavGestureListener;
import com.jrguo2.personalgesturenav.listeners.TouchListener;
import com.jrguo2.personalgesturenav.view.AreaView;

public class OverlayService extends AccessibilityService {

    public static OverlayService instance;
    private WindowManager windowsManager;
    private AreaView testView;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.i("Accessibility", "this was triggered");
    }

    @Override
    public void onInterrupt() {
        Log.i("Accessibility", "interrupt");
    }

    public boolean performNavAction(int action){
        return this.performGlobalAction(action);
    }


    @Override
    public void onCreate() {
        super.onCreate();

        Log.i("Overlay", "Created overlay service display");

        //Let's create our overlay for the navigation gestures

        //Create parameters for the layout
        WindowManager.LayoutParams p = new WindowManager.LayoutParams(
                600, 50,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSPARENT);


        p.gravity = Gravity.CENTER | Gravity.BOTTOM;
        p.x = 0;
        p.y = 0;

        windowsManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        testView = new AreaView(this);
        //testView.setOnClickListener(this);
        testView.setOnTouchListener(new TouchListener(this));

        //Add view to the manager
        windowsManager.addView(testView, p);

        this.performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (testView != null) {
            windowsManager.removeView(testView);
            testView = null;
        }
    }

}