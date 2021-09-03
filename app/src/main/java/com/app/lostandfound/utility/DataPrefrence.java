package com.app.lostandfound.utility;

import android.content.Context;
import android.content.SharedPreferences;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/**
 * Created by Vikas Sharma on 14/11/16.
 */


public class DataPrefrence {

    public static final String PREFRENCE_NAME = "Data_Prefs";
    public static boolean isFacebook = false;

    public static void setPref(Context c, String pref, String val) {
        SharedPreferences.Editor e = c.getSharedPreferences(PREFRENCE_NAME, Context.MODE_PRIVATE).edit();
        e.putString(pref, val);
        e.commit();
    }

    public static String getPref(Context c, String pref, String val) {
        return c.getSharedPreferences(PREFRENCE_NAME, Context.MODE_PRIVATE).getString(pref, val);
    }

    public static void setPref(Context c, String pref, Integer val) {
        SharedPreferences.Editor e = c.getSharedPreferences(PREFRENCE_NAME, Context.MODE_PRIVATE).edit();
        e.putInt(pref, val);
        e.commit();
    }

    public static Integer getPref(Context c, String pref, Integer val) {
        return c.getSharedPreferences(PREFRENCE_NAME, Context.MODE_PRIVATE).getInt(pref, val);
    }

    public static void setPref(Context c, String pref, Boolean val) {
        SharedPreferences.Editor e = c.getSharedPreferences(PREFRENCE_NAME, Context.MODE_PRIVATE).edit();
        e.putBoolean(pref, val);
        e.commit();
    }

    public static boolean getPref(Context c, String pref, Boolean val) {
        return c.getSharedPreferences(PREFRENCE_NAME, Context.MODE_PRIVATE).getBoolean(pref, val);
    }

    public static void deletePrefs(Context c) {
        c.getSharedPreferences(PREFRENCE_NAME, Context.MODE_PRIVATE).edit().clear().commit();
    }

    public static void deleteKey(Context activity, String key) {
        SharedPreferences prefs = activity.getSharedPreferences(PREFRENCE_NAME,
                Context.MODE_PRIVATE);
        prefs.edit().remove(key).commit();
    }


//
//    public static void setUserAddress(Context context, String key, UserAddress value) {
//
//        try {
//
//            SharedPreferences settings = context.getSharedPreferences(PREFRENCE_NAME, 0);
//            SharedPreferences.Editor editor = settings.edit();
//            Gson gson = new Gson();
//            String json = gson.toJson(value);
//            editor.putString(key, json);
//            editor.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//    public static UserAddress getUserAddress(Context context, String key)
//    {
//        UserAddress userData = new UserAddress();
//        SharedPreferences prefs = context.getSharedPreferences(PREFRENCE_NAME, 0);
//        Gson gson = new Gson();
//        String json = prefs.getString(key, "");
//        userData = gson.fromJson(json,
//                new TypeToken<UserAddress>() {
//                }.getType());
//        return userData;
//    }


//    public static void setHobbies(Context context, String key, LinkedList<Hobbies> value) {
//
//        try {
//            SharedPreferences settings = context.getSharedPreferences(PREFRENCE_NAME, 0);
//            SharedPreferences.Editor editor = settings.edit();
//            Gson gson = new Gson();
//            String json = gson.toJson(value);
//            editor.putString(key, json);
//            editor.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//    public static LinkedList<Hobbies> getHobbies(Context context, String key) {
//        LinkedList<Hobbies> hobies = new LinkedList<>();
//        SharedPreferences prefs = context.getSharedPreferences(PREFRENCE_NAME, 0);
//        Gson gson = new Gson();
//        String json = prefs.getString(key, "");
//        hobies = gson.fromJson(json,
//                new TypeToken<LinkedList<Hobbies>>() {
//                }.getType());
//        return hobies;
//    }


}
