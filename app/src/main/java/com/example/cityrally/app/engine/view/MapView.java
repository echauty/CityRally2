package com.example.cityrally.app.engine.view;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.cityrally.app.MyActivity;
import com.example.cityrally.app.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;

/**
 * Created by eric on 25/11/14.
 */

public class MapView extends Fragment {
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MapView newInstance(int sectionNumber) {
        MapView fragment = new MapView();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    public MapView() {
        super();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my, container, false);
        setUpMapIfNeeded();
        return rootView;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }
    private void setUpMapIfNeeded() {
// Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
// Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map))
                    .getMap();
// Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }
    private void setUpMap() {
        mMap.setMyLocationEnabled(true);
//mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        mMap.addMarker( new MarkerOptions()
                .position( new LatLng(49.503052, 5.940890) )
                .title("Fence 1")
                .snippet("Radius: " + 500) ).showInfoWindow();
//Instantiates a new CircleOptions object + center/radius
        CircleOptions circleOptions = new CircleOptions()
                .center( new LatLng(49.503052, 5.940890) )
                .radius( 500 )
                .fillColor(0x40ff0000)
                .strokeColor(Color.TRANSPARENT)
                .strokeWidth(2);
// Get back the mutable Circle
        Circle circle = mMap.addCircle(circleOptions);
// more operations on the circle...
    }
}