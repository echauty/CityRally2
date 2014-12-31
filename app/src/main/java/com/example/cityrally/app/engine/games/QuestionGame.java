package com.example.cityrally.app.engine.games;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.example.cityrally.app.R;
import com.example.cityrally.app.engine.challenges.SlidePuzzleMain;
import com.example.cityrally.app.engine.games.question.Question;
import com.example.cityrally.app.engine.manager.Manager;

public class QuestionGame extends Game {

    static final int QUESTION_CODE = 3;

    @Override
    public void onStart() {
        Intent intent = new Intent(Manager.activity(),Question.class);
        Manager.activity().startActivityForResult(intent, QUESTION_CODE);
    }

    protected void onStop(){
        Manager.activity().finish();
        super.stop(QUESTION_CODE);
    }
}
