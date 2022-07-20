package com.hani.motrack.Service;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.preference.PreferenceManager;

import java.io.InputStream;
import java.util.Map;


public final class Util {
    private static Util util = new Util();
    private Context context = null;
    private ApplicationInfo applicationInfo = new ApplicationInfo();

    private Util() {
    }

    public static Util getInstance(Context context) {
        if (util.context == null) {
            util.context = context;
        }
        return util;
    }

    public void getConnectionRequest(ConnectionManager.IConnectionManager iConnectionManager, int requestCode, String url, Map<String, String> headers) {
        ConnectionManager connectionManager = new ConnectionManager(iConnectionManager, requestCode);
        connectionManager.getConnectionRequest(url, headers);
    }

    public InputStream getConnectionStream(String link, String params) throws Exception {
        return new ConnectionManager(null, 0).getConnectionStream(link, params);
    }

    public void postConnectionRequest(ConnectionManager.IConnectionManager iConnectionManager, int requestCode, String url, String jsonParams, Map<String, String> headers) {
        ConnectionManager connectionManager = new ConnectionManager(iConnectionManager, requestCode);
        connectionManager.postConnectionRequest(url, jsonParams, headers);
    }

    public int determineOperatorType(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return 0;
        }
        if (phoneNumber.startsWith("091") || phoneNumber.startsWith("9891") || phoneNumber.startsWith("099") || phoneNumber.startsWith("9899")) {
            return 2;
        } else if (phoneNumber.startsWith("093") || phoneNumber.startsWith("9893") || phoneNumber.startsWith("090") || phoneNumber.startsWith("9890")) {
            return 3;
        } else {
            return 0;
        }
    }

    public static void setPreferences(Context context, String key, String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
}
