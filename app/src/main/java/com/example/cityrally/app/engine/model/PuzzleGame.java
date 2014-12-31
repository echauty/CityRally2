package com.example.cityrally.app.engine.model;

import android.content.Intent;
import com.example.cityrally.app.engine.model.puzzle.SlidePuzzleMain;
import com.example.cityrally.app.engine.controller.Controller;

/**
 * Created by eric on 26/12/14.
 */
public class PuzzleGame extends Game{

    static final int PUZZLE_CODE = 2;

    @Override
    public void onStart() {
        Intent intent = new Intent(Controller.getActivity(),SlidePuzzleMain.class);
        Controller.getActivity().startActivityForResult(intent, PUZZLE_CODE);
    }
}
