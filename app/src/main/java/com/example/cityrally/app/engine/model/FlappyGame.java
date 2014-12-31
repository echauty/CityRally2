package com.example.cityrally.app.engine.model;

import android.content.Intent;
import com.example.cityrally.app.engine.model.flappy.FlappyMainActivity;
import com.example.cityrally.app.engine.controller.Controller;

/**
 * Created by eric on 28/12/14.
 */
public class FlappyGame extends Game{

    static final int FLAPPY_CODE = 4;
    @Override
    public void onStart() {
            Intent intent = new Intent(Controller.getActivity(),FlappyMainActivity.class);
            Controller.getActivity().startActivityForResult(intent, FLAPPY_CODE);
    }
}
