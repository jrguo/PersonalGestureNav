package com.jrguo2.personalgesturenav.actions;

import android.util.Log;

import com.jrguo2.personalgesturenav.overlay.OverlayService;

public class AccessibilityAction extends Action {

    protected OverlayService navService;
    private int action;

    public AccessibilityAction(OverlayService service, int action){
        this.navService = service;
        this.action = action;

        this.context = navService.getApplicationContext();
    }

    @Override
    public boolean performAction() {
        if (navService == null)
            return false;

        this.vibrate();
        boolean result = navService.performGlobalAction(action);
        Log.i("Action", "Performing action: " + action + "\t" + result);

        return result;

    }
}
