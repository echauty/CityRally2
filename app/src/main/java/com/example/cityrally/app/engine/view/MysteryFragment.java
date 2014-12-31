package com.example.cityrally.app.engine.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.cityrally.app.R;
import com.example.cityrally.app.engine.controller.Controller;

/**
 * Created by eric on 25/11/14.
 */

public class MysteryFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.mystery, container, false);

        TextView tv;
        if(Controller.getGame().getChallengesMap().get("1").isSolved()) {
            tv = (TextView) rootView.findViewById(R.id.clue1);
            tv.setText(R.string.clue1);
        }
        if(Controller.getGame().getChallengesMap().get("2").isSolved()) {
            tv = (TextView) rootView.findViewById(R.id.clue2);
            tv.setText(R.string.clue2);
        }
        if(Controller.getGame().getChallengesMap().get("3").isSolved()) {
            tv = (TextView) rootView.findViewById(R.id.clue3);
            tv.setText(R.string.clue3);
        }
        if(Controller.getGame().getChallengesMap().get("4").isSolved()) {
            tv = (TextView) rootView.findViewById(R.id.clue4);
            tv.setText(R.string.clue4);
        }

        Button bt = (Button) rootView.findViewById(R.id.button);

        //verify the answer
        //Le chiffre des unités correspond au nombre des consonnes formant le prénom
        // et le chiffre des dizaines correspond au nombre de voyelles.
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText et = (EditText) rootView.findViewById(R.id.mystery_answer);
                Log.e("answer",et.getText().toString());
                if (et.getText().toString().equals("44")){
                    Toast.makeText(Controller.getActivity(), "Bravo! MysteryFragment solved :)", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(Controller.getActivity(), "Try again, this is easy!", Toast.LENGTH_LONG).show();
                }

            }// end onClick
        });

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MyActivity) activity).onSectionAttached(3);
    }

}