package com.jrguo2.personalgesturenav.utils;

import android.content.SharedPreferences;

import com.jrguo2.personalgesturenav.actions.ActionManager;
import com.jrguo2.personalgesturenav.overlay.NavigationAreaService;

public class Configs {

    public static SharedPreferences SHARED_PREFERENCE;
    public static SharedPreferences.Editor SHARED_PREFERENCES_EDITOR;
    public static NavigationAreaService OVERLAY_SERVICE;
    public static ActionManager ACTION_MANAGER;

    public static int COLOR_PICKER_PILL = 1;
    public static int COLOR_PICKER_AREA = 2;

    public static String getString(String key, String def){
        if(SHARED_PREFERENCE == null){
            return "";
        }

        return SHARED_PREFERENCE.getString(key, def);
    }

    public static void setString(String key, String val){
        if(SHARED_PREFERENCES_EDITOR == null)
            return;

        SHARED_PREFERENCES_EDITOR.putString(key, val);
        SHARED_PREFERENCES_EDITOR.apply();
    }

    public static int getInt(String key, int def){
        if(SHARED_PREFERENCE == null){
            return -1;
        }

        return SHARED_PREFERENCE.getInt(key, def);
    }

    public static void setInt(String key, int val){
        if(SHARED_PREFERENCES_EDITOR == null)
            return;

        SHARED_PREFERENCES_EDITOR.putInt(key, val);
        SHARED_PREFERENCES_EDITOR.apply();
    }

    public static float getFloat(String key, float def){
        if(SHARED_PREFERENCE == null){
            return -1;
        }

        return SHARED_PREFERENCE.getFloat(key, def);
    }

    public static void setFloat(String key, float val){
        if(SHARED_PREFERENCES_EDITOR == null)
            return;

        SHARED_PREFERENCES_EDITOR.putFloat(key, val);
        SHARED_PREFERENCES_EDITOR.apply();
    }

    public static long getLong(String key, long def){
        if(SHARED_PREFERENCE == null){
            return -1;
        }

        return SHARED_PREFERENCE.getLong(key, def);
    }

    public static void setLong(String key, long val){
        if(SHARED_PREFERENCES_EDITOR == null)
            return;

        SHARED_PREFERENCES_EDITOR.putLong(key, val);
        SHARED_PREFERENCES_EDITOR.apply();
    }
}
