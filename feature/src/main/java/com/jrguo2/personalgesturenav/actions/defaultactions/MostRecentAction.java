package com.jrguo2.personalgesturenav.actions.defaultactions;

import android.accessibilityservice.AccessibilityService;

import com.jrguo2.personalgesturenav.overlay.NavigationAreaService;

public class MostRecentAction extends AccessibilityAction {

    public MostRecentAction(NavigationAreaService service) {
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
