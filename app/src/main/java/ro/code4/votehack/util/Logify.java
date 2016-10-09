package ro.code4.votehack.util;

import android.util.Log;

public class Logify {
    private static boolean enabled = true;

    public static void d(String tag, String msg) {
        if (enabled) {
            Log.d(tag, msg);
        }
    }
}
