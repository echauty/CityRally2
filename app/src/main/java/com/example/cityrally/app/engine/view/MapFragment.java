package com.example.cityrally.app.engine.view;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.cityrally.app.R;
import com.example.cityrally.app.directions.route.Route;
import com.example.cityrally.app.directions.route.Routing;
import com.example.cityrally.app.directions.route.RoutingListener;
import com.example.cityrally.app.engine.controller.Controller;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.*;

public class MapFragment extends Fragment{

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    public LatLng start;
    public LatLng end;

    public MapFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my, container, false);
        setUpMapIfNeeded();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MyActivity) activity).onSectionAttached(1);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();

            Controller.getLocation().setMap(mMap);
            Controller.getLocation().setDefaultLocation();
            Controller.getLocation().centerOnLocation();

            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        mMap.setMyLocationEnabled(true);
        Controller.getLocation().setMap(mMap);
    }

}