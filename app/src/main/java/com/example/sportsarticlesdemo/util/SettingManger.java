package com.example.sportsarticlesdemo.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

public class SettingManger {

    private SharedPreferences pref;
    SharedPreferences.Editor editor;
    private Context context;


    public SettingManger(Context context) {
        this.context = context;
        pref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean getTheme(){
        return pref.getBoolean("isDark", false);
    }
    public void setTheme(boolean isDark){
        editor.putBoolean("isDark", isDark);
        editor.apply();
    }


    public boolean IsConnectedToInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo anInfo : info) {
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED)
                        return true;
                }
            }
        }
        return false;
    }

}
