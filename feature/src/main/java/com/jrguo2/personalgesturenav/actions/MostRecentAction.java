package com.jrguo2.personalgesturenav.actions;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.os.Vibrator;

import com.jrguo2.personalgesturenav.overlay.OverlayService;

public class MostRecentAction extends AccessibilityAction {

    public MostRecentAction(OverlayService service) {
        super(service, AccessibilityService.GLOBAL_ACTION_RECENTS);
    }

    @Override
    public boolean performAction(){
        this.vibrate();

        boolean result1 = this.navService.performGlobalAction(AccessibilityService.GLOBAL_ACTION_RECENTS);
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean result2 = this.navService.performGlobalAction(AccessibilityService.GLOBAL_ACTION_RECENTS);
        return result1 && result2;
    }
}
