package com.example.cityrally.app.engine.model;

import android.content.Intent;
import com.example.cityrally.app.engine.model.question.Question;
import com.example.cityrally.app.engine.controller.Controller;

public class QuestionGame extends Game {

    static final int QUESTION_CODE = 3;

    @Override
    public void onStart() {
        Intent intent = new Intent(Controller.getActivity(),Question.class);
        Controller.getActivity().startActivityForResult(intent, QUESTION_CODE);
    }

    protected void onStop(){
        Controller.getActivity().finish();
        super.stop(QUESTION_CODE);
    }
}
