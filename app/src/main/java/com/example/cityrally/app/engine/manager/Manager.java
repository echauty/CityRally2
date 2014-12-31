package com.example.cityrally.app.engine.manager;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.util.Log;
import com.example.cityrally.app.MyActivity;
import com.example.cityrally.app.engine.manager.GameManager;
import com.example.cityrally.app.engine.manager.LocationManager;
import com.google.android.gms.plus.PlusClient;

import java.util.LinkedList;
import java.util.List;

public class Manager {
    private static MyActivity mMainActivity;
    private static LocationManager mLocationManager;
    private static GameManager mGameManager;

    public Manager() {
        super();
    }

    public static void onCreate(MyActivity mainActivity) {
        Manager.mMainActivity = mainActivity;
        Manager.mLocationManager = new LocationManager();
        Manager.mGameManager = new GameManager();

        Manager.mLocationManager.onCreate();
    }

    public static void onStart() {
        Manager.mLocationManager.connect();
        Log.e("location", "connect");
    }

    public static void onStop() {
        if(Manager.mLocationManager != null) {
            Manager.mLocationManager.disconnect();
        }
        Log.e("location", "disconnect");
    }

    public static void onDestroy() {
        Manager.mLocationManager.disconnect();

        Manager.mMainActivity = null;
        Manager.mLocationManager = null;
        Manager.mGameManager = null;
    }

    public static MyActivity activity() {
        return Manager.mMainActivity;
    }

    public static LocationManager location() {
        return Manager.mLocationManager;
    }

    public static GameManager game() {
        return Manager.mGameManager;
    }

}