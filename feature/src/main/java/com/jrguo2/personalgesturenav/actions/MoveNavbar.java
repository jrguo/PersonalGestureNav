package com.jrguo2.personalgesturenav.actions;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;

import com.jrguo2.personalgesturenav.overlay.OverlayService;

public class MoveNavbar extends AccessibilityAction {
    public MoveNavbar(OverlayService service) {
        super(service, AccessibilityService.GLOBAL_ACTION_RECENTS);
    }

    @Override
    public boolean performAction() {
        navService.moveNavbar(-200);
        Log.i("Navbar", "Trying to move the navbar");
        return true;
    }
}
