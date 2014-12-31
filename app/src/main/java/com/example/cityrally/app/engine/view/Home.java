package com.example.cityrally.app.engine.view;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.example.cityrally.app.R;
import com.example.cityrally.app.engine.model.Challenge;
import com.example.cityrally.app.engine.model.PhotoGame;
import com.example.cityrally.app.engine.controller.Controller;


public class Home extends ActionBarActivity {

    private static final int PUZZLE_CODE = 2;
    private static final int QUESTION_CODE = 3;
    private static final int PHOTO_CODE = 1;
    private static final int FLAPPY_CODE = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
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

    public void onClick(View view){
        Intent intent = new Intent(this, MyActivity.class);
        startActivity(intent);
    }

    protected void onActivityResult(int requestCode, int resultCode) {
        // Check which request we're responding to
        if (requestCode == PUZZLE_CODE) {
            Log.e("requestCode myactivity", Integer.toString(PUZZLE_CODE));
            if (resultCode == RESULT_OK) {
                Log.e("resultCode", "Puzzle résolu");
                Challenge challenge = Controller.getGame().getChallengesMap().get(QUESTION_CODE);
                Controller.getGame().onSuccess(PUZZLE_CODE);
                Controller.getGame().onUnlock(challenge);
                //unlock question game
            }
        }
        if (requestCode == QUESTION_CODE) {
            Log.e("requestCode myactivity", Integer.toString(QUESTION_CODE));
            if (resultCode == RESULT_OK) {
                Log.e("resultCode", "Question résolue");
            }
        }

        if (requestCode == FLAPPY_CODE) {
            Log.e("requestCode myactivity", Integer.toString(FLAPPY_CODE));
            if (resultCode == RESULT_OK) {
                Log.e("resultCode", "Flappy bird réussi");
            }
        }
        if (requestCode == PhotoGame.PHOTO_CODE) {
            Log.e("requestCode myactivity", Integer.toString(PhotoGame.PHOTO_CODE));
            PhotoGame photoGame = (PhotoGame) Controller.getGame().getGameWithId("photo");
            photoGame.onResult(resultCode == RESULT_OK);
        }
    }




}
