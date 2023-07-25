package com.example.myapplication.sharedPreferencesHelper;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class SharedPreferencesHelper {

    private static final String PREF_NAME = "MyPreferences";

    private SharedPreferences sharedPreferences;
    private Gson gson;

    public SharedPreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void saveObject(String key, Object object) {
        String serializedObject = gson.toJson(object);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, serializedObject);
        editor.apply();
    }

    public <T> T getObject(String key, Class<T> classType) {
        String serializedObject = sharedPreferences.getString(key, null);
        if (serializedObject != null) {
            return gson.fromJson(serializedObject, classType);
        }
        return null;
    }

}
