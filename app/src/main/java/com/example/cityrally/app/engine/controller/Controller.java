package com.example.cityrally.app.engine.controller;

import android.util.Log;
import com.example.cityrally.app.engine.view.MyActivity;
import com.example.cityrally.app.engine.view.MapFragment;
import com.example.cityrally.app.engine.view.MysteryFragment;

public class Controller {
    private static GameController mGameController;
    private static LocationController mLocationController;
    private static MysteryFragment mMysteryFragment;
    private static MapFragment mMapFragment;
    private static MyActivity mMyActivity;



    public Controller() {
        super();
    }

    public static void onCreate(MyActivity myActivity) {
        Controller.mMyActivity = myActivity;
        Controller.mMysteryFragment = new MysteryFragment();
        Controller.mGameController = new GameController();
        Controller.mMapFragment = new MapFragment();
        Controller.mLocationController = new LocationController();
        Controller.mLocationController.onCreate();

    }

    public static void onStart() {
        Controller.mLocationController.connect();
        Log.e("location", "connect");
    }

    public static void onStop() {
        if(Controller.mLocationController != null) {
            Controller.mLocationController.disconnect();
        }
        Log.e("location", "disconnect");
    }

    public static void onDestroy() {
        Controller.mLocationController.disconnect();
        Controller.mMyActivity = null;
        Controller.mLocationController = null;
        Controller.mGameController = null;
    }

    public static MysteryFragment getmMysteryFragment(){
        return  Controller.mMysteryFragment;
    }

    public static MapFragment getMapView(){
        return Controller.mMapFragment;
    }

    public static MyActivity getActivity() {
        return Controller.mMyActivity;
    }

    public static LocationController getLocation() {
        return Controller.mLocationController;
    }

    public static GameController getGame() {
        return Controller.mGameController;
    }

}