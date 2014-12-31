package com.example.cityrally.app.engine.challenges;

import android.content.Intent;
import com.example.cityrally.app.engine.challenges.flappy.FlappyMainActivity;
import com.example.cityrally.app.engine.manager.Manager;

/**
 * Created by eric on 28/12/14.
 */
public class FlappyGame extends Game{

    static final int FLAPPY_CODE = 4;
    @Override
    public void onStart() {
            Intent intent = new Intent(Manager.activity(),FlappyMainActivity.class);
            Manager.activity().startActivityForResult(intent, FLAPPY_CODE);
    }
}
