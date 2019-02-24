package com.android.project.abcappen.shared;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static Context mContext;
    private static SharedPrefManager mInstance;

    public static final String SHARED_PREFS = "shared";
    public static final String PROFILE_ID_KEY = "profileIdKey";

    private SharedPrefManager(Context context) {
        mContext = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public void storeId(String id) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PROFILE_ID_KEY, String.valueOf(id));
        editor.apply();
    }

    public String getId() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFS,
                Context.MODE_PRIVATE);
        return sharedPreferences.getString(PROFILE_ID_KEY, null);
    }
}
