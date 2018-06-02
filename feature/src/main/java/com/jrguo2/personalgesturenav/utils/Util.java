package com.jrguo2.personalgesturenav.utils;

public class Util {

    public static int SCALE = 1;

    public static int dpToPixels(int dp){
        int dpAsPixels = (int) (dp * SCALE + 0.5f);
        return dpAsPixels;
    }
}
