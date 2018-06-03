package com.jrguo2.personalgesturenav.actions;

import android.accessibilityservice.AccessibilityService;

import com.jrguo2.personalgesturenav.actions.defaultactions.AccessibilityAction;
import com.jrguo2.personalgesturenav.actions.defaultactions.BlankAction;
import com.jrguo2.personalgesturenav.actions.defaultactions.MostRecentAction;
import com.jrguo2.personalgesturenav.actions.defaultactions.MoveNavbar;
import com.jrguo2.personalgesturenav.gestures.GesturesTypes;
import com.jrguo2.personalgesturenav.overlay.NavigationAreaService;

import java.util.HashMap;
import java.util.Map;

public class ActionManager {

    private Map<GesturesTypes, Action> actions;

    public ActionManager(){
        actions = new HashMap<>();
    }

    public void populateActionMapWithDefaults(NavigationAreaService navService){
        Action home = new AccessibilityAction(navService, AccessibilityService.GLOBAL_ACTION_HOME);
        Action back = new AccessibilityAction(navService, AccessibilityService.GLOBAL_ACTION_BACK);
        Action recent = new AccessibilityAction(navService, AccessibilityService.GLOBAL_ACTION_RECENTS);
        Action notifications = new AccessibilityAction(navService, AccessibilityService.GLOBAL_ACTION_NOTIFICATIONS);
        Action quickSettings = new AccessibilityAction(navService, AccessibilityService.GLOBAL_ACTION_QUICK_SETTINGS);
        Action splitScreen = new AccessibilityAction(navService, AccessibilityService.GLOBAL_ACTION_TOGGLE_SPLIT_SCREEN);
        Action power = new AccessibilityAction(navService, AccessibilityService.GLOBAL_ACTION_POWER_DIALOG);
        Action mostRecent = new MostRecentAction(navService);
        Action navbar = new MoveNavbar(navService);
        Action none = new BlankAction();

        actions.put(GesturesTypes.SHORT_UP, home);
        actions.put(GesturesTypes.SHORT_RIGHT, recent);
        actions.put(GesturesTypes.SHORT_LEFT, back);
        actions.put(GesturesTypes.SHORT_DOWN, notifications);
        actions.put(GesturesTypes.LONG_RIGHT, mostRecent);
        actions.put(GesturesTypes.LONG_UP, none);
        actions.put(GesturesTypes.LONG_DOWN, none);
        actions.put(GesturesTypes.LONG_LEFT, none);
        actions.put(GesturesTypes.NONE, none);
    }

    public void setGestureAction(GesturesTypes gesture, Action act){
        actions.put(gesture, act);
    }

    public Action getGestureAction(GesturesTypes gesture){
        return actions.get(gesture);
    }

}
