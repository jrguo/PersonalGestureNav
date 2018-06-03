package com.jrguo2.personalgesturenav.actions;

import android.accessibilityservice.AccessibilityService;

import com.jrguo2.personalgesturenav.actions.defaultactions.AccessibilityAction;
import com.jrguo2.personalgesturenav.actions.defaultactions.BlankAction;
import com.jrguo2.personalgesturenav.actions.defaultactions.MostRecentAction;
import com.jrguo2.personalgesturenav.actions.defaultactions.MoveNavbar;
import com.jrguo2.personalgesturenav.gestures.GesturesTypes;
import com.jrguo2.personalgesturenav.overlay.NavigationAreaService;
import com.jrguo2.personalgesturenav.utils.Configs;

import java.util.HashMap;
import java.util.Map;

public class ActionManager {

    public static final String ACTION_HOME = "home";
    public static final String ACTION_BACK = "back";
    public static final String ACTION_RECENTS = "recents";
    public static final String ACTION_NOTIFICATIONS = "notifications";
    public static final String ACTION_QUICK_SETTINGS = "settings";
    public static final String ACTION_SPLIT_SCREEN = "splitscreen";
    public static final String ACTION_POWER = "power";
    public static final String ACTION_MOST_RECENT = "mostrecent";
    public static final String ACTION_NONE = "none";

    private Map<GesturesTypes, String> gesturesToString;
    private Map<String, Action> stringToAction;

    public ActionManager(){
        gesturesToString = new HashMap<>();
        stringToAction = new HashMap<>();
    }

    public void createStringToActionMap(NavigationAreaService navService){
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

        stringToAction.put(ACTION_HOME, home);
        stringToAction.put(ACTION_BACK, back);
        stringToAction.put(ACTION_RECENTS, recent);
        stringToAction.put(ACTION_NOTIFICATIONS, notifications);
        stringToAction.put(ACTION_QUICK_SETTINGS, quickSettings);
        stringToAction.put(ACTION_SPLIT_SCREEN, splitScreen);
        stringToAction.put(ACTION_POWER, power);
        stringToAction.put(ACTION_MOST_RECENT, mostRecent);
        stringToAction.put(ACTION_NONE, none);
    }

    public void populateActionMapWithDefaults(){
        gesturesToString.put(GesturesTypes.SHORT_UP, Configs.getString("shortUpAction", ACTION_HOME));
        gesturesToString.put(GesturesTypes.SHORT_RIGHT, Configs.getString("shortRightAction", ACTION_MOST_RECENT));
        gesturesToString.put(GesturesTypes.SHORT_LEFT, Configs.getString("shortLeftAction", ACTION_BACK));
        gesturesToString.put(GesturesTypes.SHORT_DOWN, Configs.getString("shortDownAction", ACTION_NONE));
        gesturesToString.put(GesturesTypes.LONG_RIGHT, Configs.getString("longRightAction", ACTION_RECENTS));
        gesturesToString.put(GesturesTypes.LONG_UP, Configs.getString("longUpAction", ACTION_NOTIFICATIONS));
        gesturesToString.put(GesturesTypes.LONG_DOWN, Configs.getString("longDownAction", ACTION_NONE));
        gesturesToString.put(GesturesTypes.LONG_LEFT, Configs.getString("longLeftAction", ACTION_SPLIT_SCREEN));
        gesturesToString.put(GesturesTypes.NONE, Configs.getString("noneAction", ACTION_NONE));
    }

    public void setGestureAction(GesturesTypes gesture, String act){
        gesturesToString.put(gesture, act);
    }

    public Action getGestureAction(GesturesTypes gesture){
        return stringToAction.get(gesturesToString.get(gesture));
    }

}
