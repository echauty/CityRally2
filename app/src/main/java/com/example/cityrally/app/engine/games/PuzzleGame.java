package com.example.cityrally.app.engine.games;

import android.content.Intent;
import com.example.cityrally.app.engine.challenges.SlidePuzzleMain;
import com.example.cityrally.app.engine.manager.Manager;

/**
 * Created by eric on 26/12/14.
 */
public class PuzzleGame extends Game{

    static final int PUZZLE_CODE = 2;

    @Override
    public void onStart() {
        Intent intent = new Intent(Manager.activity(),SlidePuzzleMain.class);
        Manager.activity().startActivityForResult(intent, PUZZLE_CODE);
    }
}
