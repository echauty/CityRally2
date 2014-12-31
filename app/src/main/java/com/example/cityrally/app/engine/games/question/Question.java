package com.example.cityrally.app.engine.games.question;

/**
 * Created by eric on 26/12/14.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.example.cityrally.app.R;
import com.example.cityrally.app.engine.games.QuestionGame;
import com.example.cityrally.app.engine.manager.Manager;


public class Question extends Activity {
    private CheckBox cb1, cb2,cb3;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.game_check_box);
        showPopUp3();
    }


    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkBox1:
                if (checked) {
                    if (cb2 != null)
                        cb2.setChecked(false);
                    if (cb3 != null)
                        cb3.setChecked(false);
                }
                break;
            case R.id.checkBox2:
                if (checked) {
                    if (cb1 != null)
                        cb1.setChecked(false);
                    if (cb3 != null)
                        cb3.setChecked(false);
                }
                break;
            case R.id.checkBox3:
                if (checked){
                    if (cb2 != null)
                        cb2.setChecked(false);
                    if (cb1 != null)
                        cb1.setChecked(false);
                }
                break;
            default:
                break;
        }
    }
    private void showSimplePopUp() {

        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        helpBuilder.setTitle("Pop Up");
        helpBuilder.setMessage("This is a Simple Pop Up");
        helpBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                    }
                });

        // Remember, create doesn't show the dialog
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
    }

    private void showPopUp2() {

        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        helpBuilder.setTitle("Pop Up");
        helpBuilder.setMessage("This is a Simple Pop Up");

        final EditText input = new EditText(this);
        input.setSingleLine();
        input.setText("");
        helpBuilder.setView(input);

        helpBuilder.setPositiveButton("Positive",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                    }
                });

        helpBuilder.setNegativeButton("Negative", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
            }
        });

        helpBuilder.setNeutralButton("Neutral", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
            }
        });

        // Remember, create doesn't show the dialog
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();

    }

    private void showPopUp3() {

        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        helpBuilder.setTitle("Killing question");
        helpBuilder.setMessage("How many people are living in Luxembourg");

        LayoutInflater inflater = getLayoutInflater();
        View checkboxLayout = inflater.inflate(R.layout.game_check_box, null);
        helpBuilder.setView(checkboxLayout);
        cb1 = (CheckBox) checkboxLayout.findViewById(R.id.checkBox1);
        cb2 = (CheckBox) checkboxLayout.findViewById(R.id.checkBox2);
        cb3 = (CheckBox) checkboxLayout.findViewById(R.id.checkBox3);
        helpBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        if (cb2.isChecked()) {
                            Toast.makeText(Manager.activity(), "New clue available :)", Toast.LENGTH_LONG).show();
                            setResult(RESULT_OK);
                            QuestionGame game = (QuestionGame) Manager.game().getGameWithId("question");
                            game.onResult(true);
                        }
                        else{
                            setResult(RESULT_CANCELED);
                            Toast.makeText(Manager.activity(), "Wrong answer :(", Toast.LENGTH_LONG).show();
                        }
                        finish();
                    }
                });

        // Remember, create doesn't show the dialog
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
    }
}

