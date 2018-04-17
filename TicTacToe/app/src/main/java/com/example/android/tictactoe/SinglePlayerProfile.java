package com.example.android.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
/**
 * Input player names, select grid size,Select X or O and input number of games.
 */

public class SinglePlayerProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Full Screen User Interface
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_single_player_profile);
    }

    /**
     *An Onclick method to access the next activity of the game.
     * @param view
     */
    public void submitName(View view) {
        // Single player username
        EditText singlePlayerNameBox = findViewById(R.id.sinplayer);
        // Number of Games player wants to play
        EditText numberOfSingleGames = findViewById(R.id.numberOfSG);
        // Select a 3x3 grid layout for the Single player game
        RadioButton threebythreegrid = findViewById(R.id.Sg3x3);
        // Select a 5x5 grid layout for the Single player game
        RadioButton fivebyfivegrid = findViewById(R.id.Sg5x5);
        RadioButton userSelectX = findViewById(R.id.selectX);
        RadioButton userSelectO = findViewById(R.id.selectO);
        // Convert Edit text player name format to String format
        String SingleplayerNameText = singlePlayerNameBox.getText().toString();
        // Convert Edit text number format to String format
        String number = numberOfSingleGames.getText().toString();

        if (number.length() > 3) {
            number = "100";
            //max number of games playable is 100
            numberOfSingleGames.setText(R.string.default_max_games);
        }
        if (number.equals("") || Integer.parseInt(number) <= 0) {
            numberOfSingleGames.setText(R.string.default_min_games);
            //minimum number of games playable is 1
            number = "1";
        }


        if (SingleplayerNameText.equals("")) {
            SingleplayerNameText = "Human";
            //if name field is not filled, "Human is automatically assigned as the player name"
            singlePlayerNameBox.setText("Human");
        }
        // if 5x5 radio button is checked, goto the MainActivity5x5 activity
        if (fivebyfivegrid.isChecked()) {
            Intent intent = new Intent(this, MainActivity5x5.class);
            //send player name to MainActivity5x5 activity
            intent.putExtra("Player 1", SingleplayerNameText);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
                finish();
            }
            // if 3x3 radio button is checked, goto the SinglePlayerGame activity
        } else {if (userSelectO.isChecked()){
            Intent intent = new Intent(this, SinglePlayerGameO.class);
            //send player name to SinglePlayerGame activity
            intent.putExtra("Player 1", SingleplayerNameText);
            //send number of games to SinglePlayerGame activity
            intent.putExtra("Number", number);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
                finish();
            }
        }
        else{
            Intent intent = new Intent(this, SinglePlayerGameX.class);
            //send player name to SinglePlayerGame activity
            intent.putExtra("Player 1", SingleplayerNameText);
            //send number of games to SinglePlayerGame activity
            intent.putExtra("Number", number);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
                finish();
            }
        }
        }
    }
}