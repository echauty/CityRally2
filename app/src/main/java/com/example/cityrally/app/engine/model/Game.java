package com.example.cityrally.app.engine.model;

import android.util.Log;

/**
 * Created by eric on 24/12/14.
 */
public abstract class Game {
    protected GameListener gameListener;

    public abstract void onStart();

    public void start(GameListener gameListener) {
        this.gameListener = gameListener;

        onStart();
    }
    /*
    public void onResult(int gameCode, boolean result) {
        if (result == true)
            //we set the attribute of the corresponding challenge to solved!
            Controller.game().getChallengesMap().get(gameCode).setSolved(true);
        if (gameCode <= 2){
            //we unlock the next challenge
            Controller.game().getChallengesMap().get(gameCode+1).setUnlocked(true);
        }
    }
*/
    public void onResult(boolean result) {
        if (this.gameListener != null) {
            this.gameListener.onResult(result);
        }
    }

    public void stop(int gameCode){
        Log.e("GAME", "the Game "+ gameCode + " has been finished");
        //Controller.activity().finish();
    }
}
