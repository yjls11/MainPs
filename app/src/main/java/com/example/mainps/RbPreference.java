package com.example.mainps;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;

public class RbPreference {
    private final String PREF_NAME = "com.example.mainps";

    static Context mContext;

    public RbPreference(Context c) {
        mContext = c;
    }

    public RbPreference() {

    }

    public void put(String key, String value) {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }


    public String getValue(String key, String dftValue) {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        try {
        return pref.getString(key, dftValue);
        } catch (Exception e) {
            return dftValue;
        }
    }
}
