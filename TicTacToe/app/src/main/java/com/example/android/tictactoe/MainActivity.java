package com.example.android.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * First Activity in the program.user(s) select Single player Game or Multiplayer game
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * An OnClick Method for a button to access the Single player game profile.
     */

    public void SinglePGame(View view) {
        Intent intent = new Intent(this, SinglePlayerProfile.class);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    /**
     * An OnClick Method for a button to access the Multi-player game profile.
     */
    public void MultiPGame(View view) {
        Intent intent = new Intent(this, MultiPlayerProfile.class);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}