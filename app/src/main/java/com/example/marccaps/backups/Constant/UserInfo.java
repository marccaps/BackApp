package com.example.marccaps.backups.Constant;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by MarcCaps on 16/4/17.
 */

public class UserInfo {

    private static final String mUserInfo = "userinfo";
    private static final String mUsername = "username";
    private static final String mPassword = "password";
    private static final String mIsConnected = "isConnected";

    public static String getUsername(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(mUserInfo, Context.MODE_PRIVATE);
        return sharedPreferences.getString(mUsername,null);
    }

    public static String getPassword(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(mUserInfo, Context.MODE_PRIVATE);
        return sharedPreferences.getString(mPassword,null);
    }

    public static boolean isConnected(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(mUserInfo, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(mIsConnected,false);
    }

    public static void setUsername(Context context,String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(mUserInfo, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(mUsername,username);
        editor.apply();
    }

    public static void setPassword(Context context,String password) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(mUserInfo, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(mPassword,password);
        editor.apply();
    }

    public static void setIsConnected(Context context, boolean isConnected) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(mUserInfo, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(mIsConnected,isConnected);
        editor.apply();
    }
}
