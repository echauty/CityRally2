package com.example.cityrally.app.engine.games;

import android.util.Log;
import com.example.cityrally.app.engine.manager.Manager;
import com.example.cityrally.app.engine.view.Challenge;

import java.util.HashMap;

/**
 * Created by eric on 24/12/14.
 */
public abstract class Game {
    protected GameResultListener gameResultListener;

    public abstract void onStart();

    public void start(GameResultListener gameResultListener) {
        this.gameResultListener = gameResultListener;

        onStart();
    }
    /*
    public void onResult(int gameCode, boolean result) {
        if (result == true)
            //we set the attribute of the corresponding challenge to solved!
            Manager.game().getChallengesMap().get(gameCode).setSolved(true);
        if (gameCode <= 2){
            //we unlock the next challenge
            Manager.game().getChallengesMap().get(gameCode+1).setUnlocked(true);
        }
    }
*/
    public void onResult(boolean result) {
        if (this.gameResultListener != null) {
            this.gameResultListener.onResult(result);
        }
    }

    public void stop(int gameCode){
        Log.e("GAME", "the Game "+ gameCode + " has been finished");
        //Manager.activity().finish();
    }
}
