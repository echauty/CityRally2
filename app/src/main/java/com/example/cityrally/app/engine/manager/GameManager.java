package com.example.cityrally.app.engine.manager;

        import android.content.Context;
        import android.content.SharedPreferences;
        import android.widget.Toast;
        import com.example.cityrally.app.R;
<<<<<<< HEAD
        import com.example.cityrally.app.engine.games.Game;
        import com.example.cityrally.app.engine.games.PhotoGame;
        import com.example.cityrally.app.engine.games.PuzzleGame;
        import com.example.cityrally.app.engine.games.QuestionGame;
=======
        import com.example.cityrally.app.engine.challenges.*;
>>>>>>> all challenges working
        import com.example.cityrally.app.engine.manager.location.SimpleGeofence;
        import com.example.cityrally.app.engine.view.Challenge;
        import com.example.cityrally.app.engine.view.ChallengesFragment;
        import com.google.android.gms.location.Geofence;

        import java.io.*;
        import java.util.ArrayList;
        import java.util.Collection;
        import java.util.HashMap;

public class GameManager {
    private final String CHALLENGES_FILENAME = "save.dat";
    private static final long SECONDS_PER_HOUR = 60;
    private static final long MILLISECONDS_PER_SECOND = 1000;
    private static final long GEOFENCE_EXPIRATION_IN_HOURS = 24;
    private static final long GEOFENCE_EXPIRATION_TIME = GEOFENCE_EXPIRATION_IN_HOURS * SECONDS_PER_HOUR * MILLISECONDS_PER_SECOND;

    private HashMap<String, SimpleGeofence> mGeofences;
    private HashMap<String, Challenge> mChallenges;
    private HashMap<String, Game> mGames;

    public GameManager() {
        super();

        mGeofences = new HashMap<String, SimpleGeofence>();
        initGeofences();

        mChallenges = new HashMap<String, Challenge>();
        //loadChallenges();
        if (mChallenges.size() == 0) {
            initChallenges();
        }
        reloadChallengesResources();
        saveChallenges();

        mGames = new HashMap<String, Game>();
        initGames();
    }

    private void reloadChallengesResources() {
        mChallenges.get("1").setResources(R.drawable.lux_city, R.string.c_title_1, R.string.c_text_1, R.string.c_game_1);
        mChallenges.get("2").setResources(R.drawable.lux_city, R.string.c_title_2, R.string.c_text_2, R.string.c_game_2);
        mChallenges.get("3").setResources(R.drawable.lux_city, R.string.c_title_3, R.string.c_text_3, R.string.c_game_3);
        mChallenges.get("4").setResources(R.drawable.lux_city, R.string.c_title_4, R.string.c_text_4, R.string.c_game_4);
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
        ObjectOutput out = null;

        try {
            out = new ObjectOutputStream(new FileOutputStream(new File(Manager.activity().getFilesDir(),"") + File.separator + CHALLENGES_FILENAME));
            out.writeObject(mChallenges);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadChallenges() {
        ObjectInputStream input;

        try {
            input = new ObjectInputStream(new FileInputStream(new File(new File(Manager.activity().getFilesDir(),"") + File.separator + CHALLENGES_FILENAME)));
            mChallenges = (HashMap<String, Challenge>) input.readObject();
            input.close();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initChallenges() {
        mChallenges.put("1", new Challenge("1", "photo", true, false,false));
        mChallenges.put("2", new Challenge("2", "puzzle", false, false, false));
        mChallenges.put("3", new Challenge("3", "question", false, false, false));
        mChallenges.put("4", new Challenge("4", "flappy", false, false, false));
    }

    public void onUnlock(final Challenge challenge, Geofence geofence) {
        if (!challenge.isUnlocked()) {
            challenge.setUnlocked(true);
            saveChallenges();

            Manager.activity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(Manager.activity(), R.string.challenge_unlocked, Toast.LENGTH_LONG).show();

                    if (ChallengesFragment.mAdapter != null) {
                        ChallengesFragment.mAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }
    public void onUnlock(final Challenge challenge) {
        if (!challenge.isUnlocked()) {
            challenge.setUnlocked(true);
            saveChallenges();

            Manager.activity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(Manager.activity(), R.string.challenge_unlocked, Toast.LENGTH_LONG).show();

                    if (ChallengesFragment.mAdapter != null) {
                        ChallengesFragment.mAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    public void onPosition(final Challenge challenge, Geofence geofence) {
        if (!challenge.isInPosition()) {
            challenge.setInPosition(true);
            saveChallenges();

            Manager.activity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(Manager.activity(), R.string.challenge_inposition, Toast.LENGTH_LONG).show();

                    if (ChallengesFragment.mAdapter != null) {
                        ChallengesFragment.mAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
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

    public Collection<SimpleGeofence> getGeofences() {
        return mGeofences.values();
    }

    public SimpleGeofence getGeofenceWithId(String id) {
        return mGeofences.get(id);
    }

    public Game getGameWithId(String id) {
        return mGames.get(id);
    }

    public Collection<Challenge> getChallenges() {
        return mChallenges.values();
    }

    public HashMap<String, Challenge> getChallengesMap() {
        return mChallenges;
    }


}