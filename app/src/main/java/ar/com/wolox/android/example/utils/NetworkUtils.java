package ar.com.wolox.android.example.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 *
 */
public final class NetworkUtils {

    private NetworkUtils() { }

    public static boolean isNetworkAvailable(Context mApplication) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mApplication.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
