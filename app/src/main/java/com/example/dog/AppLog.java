package com.example.dog;

import android.util.Log;

public class AppLog {
    public static final boolean DEBUG = true;
    public static final boolean DEBUG_UI = DEBUG || true;

    private static final String TAG = "umseozz] ";

    public static void v(String tag, String content) {
        if(DEBUG)
            Log.v(TAG+tag, content);
    }

    public static void d(String tag, String content) {
        if(DEBUG)
            Log.d(TAG+tag, content);
    }

    public static void i(String tag, String content) {
        if(DEBUG)
            Log.i(TAG+tag, content);
    }

    public static void w(String tag, String content) {
        if(DEBUG)
            Log.w(TAG+tag, content);
    }

    public static void e(String tag, String content) {
        if(DEBUG)
            Log.e(TAG+tag, content);
    }
}
