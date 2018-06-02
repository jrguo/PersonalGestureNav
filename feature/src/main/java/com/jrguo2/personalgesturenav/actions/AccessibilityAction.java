package com.jrguo2.personalgesturenav.actions;

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

        return navService.performGlobalAction(action);

    }
}
