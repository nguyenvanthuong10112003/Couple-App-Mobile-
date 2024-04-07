package com.example.myapplication.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class SharedPreferencesHelper {
    private Context _context;
    public SharedPreferencesHelper(Context context) {
        _context = context;
    }

    public void saveData(String path, String key, String value) {
        SharedPreferences shared = _context.getSharedPreferences(path, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(path + key, value);
        editor.apply();
    }

    public void saveDatas(String path, HashMap<String, String> dataSaves) {
        if (dataSaves == null || dataSaves.isEmpty())
            return;
        SharedPreferences shared = _context.getSharedPreferences(path, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        dataSaves.forEach((key, value) -> {
            if (value != null)
                editor.putString(path + key, value);
        });
        editor.apply();
    }

    public String getData(String path, String key) {
        SharedPreferences shared = _context.getSharedPreferences(path, Context.MODE_PRIVATE);
        return shared.getString(path + key, null);
    }

    public String[] getDatas(String path, String[]key) {
        SharedPreferences shared = _context.getSharedPreferences(path, Context.MODE_PRIVATE);
        String[]result = new String[key.length];
        for (int i = 0; i < key.length; i++)
            result[i] = shared.getString(path + key[i], null);
        return result;
    }

    public void removeData(String path, String key) {
        SharedPreferences shared = _context.getSharedPreferences(path, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.remove(key);
        editor.apply();
    }

    public void removeDatas(String path) {
        SharedPreferences shared = _context.getSharedPreferences(path, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.clear();
        editor.apply();
    }
}
