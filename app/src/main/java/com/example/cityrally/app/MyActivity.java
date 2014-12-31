package com.example.cityrally.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
<<<<<<< HEAD
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.cityrally.app.engine.games.PhotoGame;
import com.example.cityrally.app.engine.games.puzzle.SlidePuzzleMain;
import com.example.cityrally.app.engine.games.question.Question;
=======
import com.example.cityrally.app.engine.challenges.FlappyGame;
import com.example.cityrally.app.engine.challenges.PhotoGame;
import com.example.cityrally.app.engine.challenges.PuzzleGame;
import com.example.cityrally.app.engine.challenges.QuestionGame;
import com.example.cityrally.app.engine.challenges.flappy.FlappyMainActivity;
import com.example.cityrally.app.engine.challenges.question.Question;
>>>>>>> all challenges working
import com.example.cityrally.app.engine.manager.Manager;
import com.example.cityrally.app.engine.view.ChallengesFragment;
import com.example.cityrally.app.engine.view.GeoActivity;
import com.example.cityrally.app.engine.view.MapView;
import com.example.cityrally.app.engine.view.MyActivityF;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;



public class MyActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private GeoActivity geoActivity;
    private ChallengesFragment challenges;
    private static final int PUZZLE_CODE = 2;
    private static final int QUESTION_CODE = 3;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Manager.onCreate(this);

        setContentView(R.layout.activity_my);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        geoActivity = new GeoActivity();

        challenges = new ChallengesFragment();


        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        initLocation();

        checkLocation();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (position) {
            case 0:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new MapView())
                        .commit();
                break;
            case 1:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, geoActivity)
                        .commit();
                break;
            case 2:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, challenges)
                        .commit();
                break;
            case 3:
                Intent intent = new Intent(this, Question.class);
                startActivityForResult(intent,QUESTION_CODE);
                break;
            case 4:
                Intent intent2 = new Intent(this, Question.class);
                startActivityForResult(intent2,QUESTION_CODE);
                break;

            default :
                fragmentManager.beginTransaction()
                        .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                        .commit();
                break;
        }

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
            case 5:
                mTitle = getString(R.string.title_section5);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.my, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
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
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
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
            ((MyActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
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
            mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        }


    }

    public void onRegisterClicked(View view){
        geoActivity.onRegisterClicked(view);
    }

    private boolean initLocation() {
        // Check that Google Play services is available
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (ConnectionResult.SUCCESS == resultCode) {
            Log.e("Location Updates", "Google Play services is available.");
            return true;
        } else {
            Log.e("Location Updates", "Google Play services is NOT available." + resultCode);

            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, this, 9000);
            if (dialog != null) {
                dialog.show();
            }

            return false;
        }
    }

    private void checkLocation() {
        LocationManager locationManager = null;
        boolean gps = false;
        boolean network = false;

        if(locationManager == null) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }

        try {
            gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {

        }

        Log.e("location", "enable : " + gps);

        try {
            network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {

        }

        if(!gps && !network){
            final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setCancelable(false);
            dialog.setMessage(getResources().getString(R.string.gps_network_not_enabled));
            dialog.setPositiveButton(getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);

                    checkLocation();
                }
            });
            dialog.setNegativeButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    checkLocation();
                }
            });
            dialog.show();
        } else {
            Manager.location().connect();
        }
    }
/*
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode,data);

        if (requestCode == 99) {
            Log.e("OnActivityResult", "yes");
        }
        // Check which request we're responding to
        if (requestCode == PUZZLE_CODE) {
            Log.e("requestCode myactivity", Integer.toString(PUZZLE_CODE));
            if (resultCode == RESULT_OK) {
                Log.e("resultCode", "Puzzle résolu");
                PuzzleGame puzzleGame = (PuzzleGame) Manager.game().getGameWithId("puzzle");
                puzzleGame.onResult(true);
            }
        }
        if (requestCode == QUESTION_CODE) {
            Log.e("requestCode myactivity", Integer.toString(QUESTION_CODE));
            if (resultCode == RESULT_OK) {
                Log.e("resultCode", "Question résolue");
<<<<<<< HEAD
=======
                QuestionGame questionGame = (QuestionGame) Manager.game().getGameWithId("question");
                questionGame.onResult(true);
>>>>>>> all challenges working
            }
        }
        if (requestCode == PhotoGame.PHOTO_CODE) {
            Log.e("requestCode myactivity", Integer.toString(PHOTO_CODE));
            if (resultCode == RESULT_OK) {
                Log.e("resultCode", "Photo réussie");
                PhotoGame photoGame = (PhotoGame) Manager.game().getGameWithId("photo");
                photoGame.onResult(true);
            }
        }
<<<<<<< HEAD
=======

        if (requestCode == FLAPPY_CODE) {
            Log.e("requestCode myactivity", Integer.toString(FLAPPY_CODE));
            if (resultCode == RESULT_OK) {
                Log.e("resultCode", "Flappy bird réussi");
                FlappyGame flappyGame = (FlappyGame) Manager.game().getGameWithId("flappy");
                flappyGame.onResult(true);
            }
        }
>>>>>>> all challenges working
    }
*/
    protected void onActivityResult(int requestCode, int resultCode) {
        // Check which request we're responding to
        if (requestCode == PUZZLE_CODE) {
            Log.e("requestCode myactivity", Integer.toString(PUZZLE_CODE));
            if (resultCode == RESULT_OK) {
                Log.e("resultCode", "Puzzle résolu");
                Manager.game().onSuccess(PUZZLE_CODE);
                Manager.game().onUnlock(QUESTION_CODE);
                //unlock question game
            }
        }
        if (requestCode == QUESTION_CODE) {
            Log.e("requestCode myactivity", Integer.toString(QUESTION_CODE));
            if (resultCode == RESULT_OK) {
                Log.e("resultCode", "Question résolue");
            }
        }
        if (requestCode == PhotoGame.PHOTO_CODE) {
            Log.e("requestCode myactivity", Integer.toString(PhotoGame.PHOTO_CODE));
            PhotoGame photoGame = (PhotoGame) Manager.game().getGameWithId("photo");
            photoGame.onResult(resultCode == RESULT_OK);
        }
    }

    public NavigationDrawerFragment getNavigationDrawerFragment() {
        return mNavigationDrawerFragment;
    }

}