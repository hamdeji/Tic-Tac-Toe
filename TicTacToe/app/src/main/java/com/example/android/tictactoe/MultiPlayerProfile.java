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
 * Input player names, select grid size and number of games.
 */

public class MultiPlayerProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Full Screen User Interface
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_multi_player_profile);

    }
    public void submitName(View view) {
        // player 1 name
        EditText player1Name = findViewById(R.id.player1);
        //player 2 name
        EditText player2Name = findViewById(R.id.player2);
        // number of games
        EditText numberOfGames = findViewById(R.id.numberOfMG);
        // convert player 1 name edit text format to String
        String player1NameText = player1Name.getText().toString();
        // convert player 2 name edit text format to String
        String player2NameText = player2Name.getText().toString();
        // convert number of games edit text format to String
        String number = numberOfGames.getText().toString();
        // 5x5 grid layout radio button
        RadioButton Mfivebyfivegrid = findViewById(R.id.Mg5x5);

        if (number.equals("") || Integer.parseInt(number) <= 0) {
            numberOfGames.setText(R.string.default_min_games);
            //minimum number of games = 1
            number = "1";
        }
        if (number.length() > 3) {
            number = "999";
            //maximum number of games = 100
            numberOfGames.setText(R.string.default_max_games);
        }

        if (player1NameText.equals("")) {
            player1NameText = "Player 1";
            // default player 1 name
            player1Name.setText(R.string.player_1_default_name);
        }
        if (player2NameText.equals("")) {
            player2NameText = "Player 2";
            // default player 2 name
            player2Name.setText(R.string.player_2_default_name);
        }
        if (Mfivebyfivegrid.isChecked()) {
            Intent intent = new Intent(this, MultiPlayerGame5x5.class);
            intent.putExtra("Player 1", player1NameText);
            intent.putExtra("Player 2", player2NameText);
            intent.putExtra("Number", number);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
                finish();
            }
        } else {
            Intent intent = new Intent(this, MultiPlayerGame.class);
            intent.putExtra("Player 1", player1NameText);
            intent.putExtra("Player 2", player2NameText);
            intent.putExtra("Number", number);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
                finish();
            }
        }
    }
}
