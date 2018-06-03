package com.jrguo2.personalgesturenav.actions.defaultactions;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;

import com.jrguo2.personalgesturenav.overlay.NavigationAreaService;

public class MoveNavbar extends AccessibilityAction {
    public MoveNavbar(NavigationAreaService service) {
        super(service, AccessibilityService.GLOBAL_ACTION_RECENTS);
    }

    @Override
    public boolean performAction() {
        navService.moveNavBar(-200);
        Log.i("Navbar", "Trying to move the navbar");
        return true;
    }
}
