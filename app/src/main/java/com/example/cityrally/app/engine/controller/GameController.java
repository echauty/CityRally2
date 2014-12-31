package com.example.cityrally.app.engine.controller;

import android.widget.Toast;
import com.example.cityrally.app.engine.view.ChallengesFragment;
import com.google.android.gms.location.Geofence;
import com.example.cityrally.app.R;
import com.example.cityrally.app.engine.model.*;
import java.util.Collection;
import java.util.HashMap;
import com.example.cityrally.app.engine.controller.location.SimpleGeofence;
import com.example.cityrally.app.engine.model.Challenge;


public class GameController {
    private static final long SECONDS_PER_HOUR = 60;
    private static final long MILLISECONDS_PER_SECOND = 1000;
    private static final long GEOFENCE_EXPIRATION_IN_HOURS = 24;
    private static final long GEOFENCE_EXPIRATION_TIME = GEOFENCE_EXPIRATION_IN_HOURS * SECONDS_PER_HOUR * MILLISECONDS_PER_SECOND;

    private HashMap<String, SimpleGeofence> mGeofences;
    private HashMap<String, Game> mGames;
    private HashMap<String, Challenge> mChallenges;


    public GameController() {
        super();

        mGeofences = new HashMap<String, SimpleGeofence>();
        initGeofences();

        mGames = new HashMap<String, Game>();
        initGames();

        mChallenges = new HashMap<String, Challenge>();
        if (mChallenges.size() == 0) {
            initChallenges();
        }
        loadResources();
    }

    public void initGeofences() {
        mGeofences.clear();
        // residence uni val
        mGeofences.put("1", new SimpleGeofence("1",49.503052, 5.940890,300,
                GEOFENCE_EXPIRATION_TIME, Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT));
        //University kirchberg
        mGeofences.put("2", new SimpleGeofence("2",49.626486, 6.159343,300,
                GEOFENCE_EXPIRATION_TIME, Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT));
        //luxembourg airport
        mGeofences.put("3", new SimpleGeofence("3",49.632978, 6.214220,300,
                GEOFENCE_EXPIRATION_TIME, Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT));
        //palais grand ducal
        mGeofences.put("4", new SimpleGeofence("4",49.611888, 6.132521,300,
                GEOFENCE_EXPIRATION_TIME, Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT));

    }


    public void onSuccess(int id){
        Challenge challenge = getChallengesMap().get(id);
        if(id <= 2) {
            Challenge challenge2 = getChallengesMap().get(id + 1);
            challenge2.setUnlocked(true);
        }
        challenge.setSolved(true);
    }

    private void loadResources() {
        mChallenges.get("1").setResources(R.drawable.lux_city, R.string.c_title_1,R.string.c_text_1);
        mChallenges.get("2").setResources(R.drawable.lux_city, R.string.c_title_2, R.string.c_text_2);
        mChallenges.get("3").setResources(R.drawable.lux_city, R.string.c_title_3,R.string.c_text_3);
        mChallenges.get("4").setResources(R.drawable.lux_city, R.string.c_title_4,R.string.c_text_4);
    }

    public void initChallenges() {
        mChallenges.put("1", new Challenge("1", "photo", true, false,false));
        mChallenges.put("2", new Challenge("2", "puzzle", false, false, false));
        mChallenges.put("3", new Challenge("3", "question", false, false, false));
        mChallenges.put("4", new Challenge("4", "flappy", false, false, false));
    }

    private void initGames() {
        mGames.put("photo", new PhotoGame());
        mGames.put("puzzle", new PuzzleGame());
        mGames.put("question", new QuestionGame());
        mGames.put("flappy", new FlappyGame());

    }
    public void saveChallenges() {
        /*
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.saved_high_score), newHighScore);
        editor.commit();
        */
    }

    public HashMap<String, Challenge> getChallengesMap() {
        return mChallenges;
    }

    public Collection<SimpleGeofence> getGeofences() {
        return mGeofences.values();
    }

    public Game getGameWithId(String id) {
        return mGames.get(id);
    }

    public SimpleGeofence getGeofenceWithId(String id) {
        return mGeofences.get(id);
    }

    public Collection<Challenge> getChallenges() {
        return mChallenges.values();
    }

    public void onUnlock(Challenge challenge) {
        if (!challenge.isUnlocked()) {
            challenge.setUnlocked(true);
            //saveChallenges();
            Controller.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(Controller.getActivity(), R.string.challenge_unlocked, Toast.LENGTH_LONG).show();
                    if (ChallengesFragment.mAdapter != null) {
                        ChallengesFragment.mAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    public void onPosition(final Challenge challenge) {
        if (!challenge.isInPosition()) {
            challenge.setInPosition(true);
            //saveChallenges();
            Controller.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(Controller.getActivity(), R.string.challenge_inposition, Toast.LENGTH_LONG).show();
                    if (ChallengesFragment.mAdapter != null) {
                        ChallengesFragment.mAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    public void unlockNextChallenge(Integer id1) {
        //only 4 challenges, the last one does not unlock
        if (id1 <= 3) {
            Challenge challenge2 = mChallenges.get(String.valueOf(id1 + 1));
            challenge2.setUnlocked(true);
        }
    }




}