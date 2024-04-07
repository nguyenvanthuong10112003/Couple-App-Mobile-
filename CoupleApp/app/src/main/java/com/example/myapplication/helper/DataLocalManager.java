package com.example.myapplication.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myapplication.define.DefineSharedPreferencesUserAuthen;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class DataLocalManager {
    private static DataLocalManager instance;
    private SharedPreferencesHelper sharedPreferences;

    public static void init(Context context) {
        DataLocalManager.instance = new DataLocalManager();
        instance.sharedPreferences = new SharedPreferencesHelper(context);
    }

    public static DataLocalManager getInstance() {
        if (instance == null)
            instance = new DataLocalManager();
        return instance;
    }

    public void saveData(String pathSave, String key, String value) {
        sharedPreferences.saveData(pathSave,key, value);
    }

    public void saveDatas(String pathSave, HashMap<String, String> dataSaves) {
        sharedPreferences.saveDatas(pathSave, dataSaves);
    }

    public String getData(String pathSave, String key) {
        return sharedPreferences.getData(pathSave, key);
    }

    public String[] getDatas(String pathSave, String[] key) {
        return sharedPreferences.getDatas(pathSave, key);
    }

    public void removeDatas(String pathSave) {
        sharedPreferences.removeDatas(pathSave);
    }
}
