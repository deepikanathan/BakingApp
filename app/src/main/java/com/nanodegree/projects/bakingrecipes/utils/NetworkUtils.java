package com.nanodegree.projects.bakingrecipes.utils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Network utility methods
 */
public class NetworkUtils {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
