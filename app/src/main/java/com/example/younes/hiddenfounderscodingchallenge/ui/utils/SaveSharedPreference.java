package com.example.younes.hiddenfounderscodingchallenge.ui.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.example.younes.hiddenfounderscodingchallenge.ui.utils.AppConstants.VIEW_MODE_IN_REF;

/**
 * Created by younes on 8/20/2018.
 */

public class SaveSharedPreference {
    static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
    // Values for Shared Prefrences

    /**
     * Set the Login Status
     * @param context
     * @param loggedIn
     */
    public static void setViewMode(Context context, int loggedIn) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putInt(VIEW_MODE_IN_REF, loggedIn);
        editor.apply();
    }


    /**
     * Get the Login Status
     * @param context
     * @return boolean: login status
     */
    public static int getViewMode(Context context) {
        return getPreferences(context).getInt(VIEW_MODE_IN_REF, AppConstants.VERTICAL_LINEAR_VIEW);
    }
}