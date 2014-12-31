package com.example.cityrally.app.engine.controller;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import com.example.cityrally.app.engine.controller.location.ReceiveTransitionsIntentService;
import com.example.cityrally.app.engine.controller.location.SimpleGeofence;
import com.example.cityrally.app.engine.model.Challenge;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.*;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class LocationController implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener, LocationListener, LocationClient.OnAddGeofencesResultListener, LocationClient.OnRemoveGeofencesResultListener{

    private static final long UPDATE_INTERVAL = 3 * 1000;
    private static final long FASTEST_INTERVAL = 2 * 1000;

    private LocationClient mLocationClient;
    private final LocationRequest mLocationRequest;
    private Location mLastLocation;

    private GoogleMap mMap;
    private boolean mCenterOnLocation;

    private boolean mMoveCamera;
    private double mMoveLatitude;
    private double mMoveLongitude;

    private HashMap<String, Marker> mMarkers;
    private Runnable mAddMarkerAction;

    private PendingIntent mTransitionPendingIntent;

    public LocationController() {
        super();

        mLastLocation = null;
        mCenterOnLocation = false;
        mMoveCamera = false;
        mMoveLatitude = 0.0;
        mMoveLongitude = 0.0;
        mLocationClient = new LocationClient(Controller.getActivity(), this, this);
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mMarkers = new HashMap<String, Marker>();
        mAddMarkerAction = null;

        mTransitionPendingIntent = getTransitionPendingIntent();
    }

    public void onCreate() {

    }

    public void connect() {
        mLocationClient.connect();
    }

    public void disconnect() {
        stopLocationUpdate();

        mLocationClient.disconnect();
    }

    public void startLocationUpdate() {
        mLocationClient.requestLocationUpdates(mLocationRequest, this);
    }

    public void stopLocationUpdate() {
        if (mLocationClient.isConnected()) {
            mLocationClient.removeLocationUpdates(this);
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationClient.setMockMode(true);
        startLocationUpdate();
        startGeofencesUpdate();
    }

    @Override
    public void onDisconnected() {
        mLocationClient.removeGeofences(mTransitionPendingIntent, this);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(Controller.getActivity(), 9000);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.v("location", "error: " + connectionResult.getErrorCode());
        }
    }

    @Override
    public void onLocationChanged(final Location location) {
        final String msg = "Updated Location: " + Double.toString(location.getLatitude()) + "," + Double.toString(location.getLongitude());
                Log.v("location test", msg);

        mLastLocation = location;

                if(mMoveCamera&&mMap!=null)

                {
                    mMoveCamera = false;
                    mCenterOnLocation = false;

                    moveCamera(mMoveLatitude, mMoveLongitude);
                }

                else if(mCenterOnLocation&&mMap!=null)

                {
                    mCenterOnLocation = false;
                    moveCamera(location);
                }

                if(mAddMarkerAction!=null)

                {
                    mAddMarkerAction.run();
                    mAddMarkerAction = null;
                }
    }

    public GoogleMap getMap() {
        return mMap;
    }

    public void setMap(GoogleMap mMap) {
        this.mMap = mMap;
    }

    public void moveCamera(double latitude, double longitude) {
        moveCamera(latitude, longitude, false);
    }

    public void moveCamera(Location location) {
        moveCamera(location.getLatitude(), location.getLongitude(), false);
    }

    public void moveCamera(double latitude, double longitude, boolean set) {
        if (mMap != null) {
            CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude));
            final CameraUpdate zoom = CameraUpdateFactory.zoomTo(10);
            mMap.stopAnimation();
            if (set) {
                mMap.moveCamera(center);
                mMap.moveCamera(zoom);
            } else {
                mMap.animateCamera(center, new GoogleMap.CancelableCallback() {
                    @Override
                    public void onFinish() {
                        if (mMap != null) {
                            mMap.animateCamera(zoom);
                        }
                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }
        } else {
            mMoveCamera = true;
            mMoveLatitude = latitude;
            mMoveLongitude = longitude;
        }
    }

    private void startGeofencesUpdate() {
        List<Geofence> geofences = new ArrayList<Geofence>();
        for (SimpleGeofence geofence : Controller.getGame().getGeofences()) {
            geofences.add(geofence.toGeofence());
        }
        mLocationClient.addGeofences(geofences, mTransitionPendingIntent, this);
    }

    @Override
    public void onRemoveGeofencesByRequestIdsResult(int i, String[] strings) {

    }

    @Override
    public void onAddGeofencesResult(int statusCode, String[] strings) {
        if (LocationStatusCodes.SUCCESS == statusCode) {
            Log.e("geofences", "success");
        } else {
            Log.e("geofences", "error " + statusCode);
        }
    }

    private PendingIntent getTransitionPendingIntent() {
        Intent intent = new Intent(Controller.getActivity(), ReceiveTransitionsIntentService.class);

        return PendingIntent.getService(Controller.getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void centerOnLocation() {
        if (mLastLocation != null) {
            moveCamera(mLastLocation);
        } else {
            mCenterOnLocation = true;
        }
    }

    @Override
    public void onRemoveGeofencesByPendingIntentResult(int i, PendingIntent pendingIntent) {

    }

    public void onGeofenceEnter(Geofence geofence) {
        Log.e("enter", geofence.getRequestId());

        Challenge challenge = Controller.getGame().getChallengesMap().get(geofence.getRequestId());
        Controller.getGame().onPosition(challenge);

    }

    public void onGeofenceExit(Geofence geofence) {
        Log.e("exit", geofence.getRequestId());
    }

    public void setDefaultLocation() {
        moveCamera(49.503052, 5.940890, true);
    }

    public void removeMarker(String id) {
        Marker marker = mMarkers.get(id);
        if (marker != null) {
            marker.remove();
            mMarkers.remove(id);
        }
    }

    public Location createLocation(double lat, double lng) {
        Location newLocation = new Location("flp");
        newLocation.setLatitude(lat);
        newLocation.setLongitude(lng);
        newLocation.setTime(new Date().getTime());
        newLocation.setAccuracy(3.0f);
        //newLocation.setElapsedRealtimeNanos(System.nanoTime());
        return newLocation;
    }

    public void setMockLocation(double latitude, double longitude) {
        Location location = createLocation(latitude, longitude);
        mLocationClient.setMockLocation(location);
    }

    public void startTest1() {
        new Thread() {
            public void run() {
                try {
                    mLastLocation = createLocation(45.503052, 5.940890);
                    sleep(10000);
                    setMockLocation(45.503052, 5.940890);
                    sleep(10000);
                    setMockLocation(49.503052, 5.940890);
                    mLastLocation = createLocation(42.503052, 5.940890);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void startTest2() {
        new Thread() {
            public void run() {
                try {
                    mLastLocation = createLocation(48.126486, 6.159343);
                    sleep(10000);
                    setMockLocation(48126486, 6.159343);
                    sleep(6000);
                    setMockLocation(49.626486, 6.159343);
                    mLastLocation = createLocation(49.626486, 6.159343);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
    public void startTest3() {
        new Thread() {
            public void run() {
                try {
                    mLastLocation = createLocation(48.132978, 6.214220);
                    sleep(10000);
                    setMockLocation(48.132978, 6.214220);
                    sleep(6000);
                    setMockLocation(49.632978, 6.214220);
                    mLastLocation = createLocation(49.632978, 6.214220);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    public void startTest4() {
        new Thread() {
            public void run() {
                try {
                    mLastLocation = createLocation(48.132978, 6.214220);
                    setMockLocation(48.132978, 6.214220);
                    sleep(6000);
                    setMockLocation(49.611888, 6.132521);
                    mLastLocation = createLocation(49.611888, 6.132521);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    public Location getmLastLocation(){
        return mLastLocation;
    }

}