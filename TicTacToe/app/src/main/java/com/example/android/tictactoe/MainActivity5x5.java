package com.example.android.tictactoe;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import static com.example.android.tictactoe.R.id.board;

/**
 * Play against computer on a 5x5 game board
 */
public class MainActivity5x5 extends AppCompatActivity {
    String displayTurn,player1Name,player2Name;
    TextView playerTurn;
    int player1win = 0;
    private BoardView5x5 boardView5x5;
    private GameEngine5x5 gameEngine5x5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5x5);
        boardView5x5 =  findViewById(board);
        gameEngine5x5 = new GameEngine5x5();
        boardView5x5.setGameEngine5x5(gameEngine5x5);
        boardView5x5.setMainActivity(this);
        playerTurn =  findViewById(R.id.Splayer);
        Intent intent = getIntent();
        player1Name = intent.getExtras().getString("Player 1");
        player2Name = "Computer";
        displayTurn = player1Name + "'s turn (X)";
        playerTurn.setText(displayTurn);
        TextView Splayername = findViewById(R.id.Splayername);
        Splayername.setText(player1Name);
    }
 public void resetboard5x5 (View view){
            newGame();
        }

    public void gameEnded(char c) {
        String msg = (c == 'T') ? "Game Ended. Tie": "GameEnded. " + c + " win";

        new AlertDialog.Builder(this).setTitle("Tic Tac Toe").
                setMessage(msg).
                setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        newGame();
                    }
                }).show();
    }

    private void newGame() {
        gameEngine5x5.newGame();
        boardView5x5.invalidate();
    }

}
