package com.example.android.tictactoe;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import static android.graphics.Color.GREEN;
/**
 * play game with two players on a 5x5 game board
 */

public class MultiPlayerGame5x5 extends AppCompatActivity {

    int turn = 1,win = 0,gamover = 0,flagEndGame = 0,flag;
    String displayTurn, player1Name, player2Name, numberofGames;
    GridLayout grid;
    Button gameBoard[][] = new Button[5][5];
    TextView playerTurn;
    int number,counter = 0,player1Win = 0, player2Win = 0, draw = 0,flipValue = 0;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_multi_player_game5x5);
        playerTurn = findViewById(R.id.player);
        builder = new AlertDialog.Builder(this);
        Intent intent = getIntent();
        player1Name = intent.getExtras().getString("Player 1");
        player2Name = intent.getExtras().getString("Player 2");
        numberofGames = intent.getExtras().getString("Number");
        number = Integer.parseInt(numberofGames);
        grid = findViewById(R.id.grid);
        displayTurn = player1Name + "'s turn (X)";
        playerTurn.setText(displayTurn);
        TextView Mplayer1 = findViewById(R.id.Mplayer1);
        Mplayer1.setText(player1Name);
        TextView Mplayer2 = findViewById(R.id.Mplayer2);
        Mplayer2.setText(player2Name);


        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                gameBoard[i][j] = (Button) grid.getChildAt(5 * i + j);
            }
        }
    }

    public void playmove5(View view) {
        flag = 0;
        int index = grid.indexOfChild(view);
        int i = index / 5;
        int j = index % 5;
        if (turn == 1 && gamover == 0 && !(gameBoard[i][j].getText().toString().equals("X")) && !(gameBoard[i][j].getText().toString().equals("O"))) {


            if (flipValue == 0) {
                displayTurn = player2Name + "'s turn (O)";
                playerTurn.setText(displayTurn);
                gameBoard[i][j].setText("X");
            } else if (flipValue == 1) {
                displayTurn = player1Name + "'s turn (X)";
                playerTurn.setText(displayTurn);
                gameBoard[i][j].setText("O");
            }

            turn = 2;

        } else if (turn == 2 && gamover == 0 && !(gameBoard[i][j].getText().toString().equals("X")) && !(gameBoard[i][j].getText().toString().equals("O"))) {
            if (flipValue == 0) {
                displayTurn = player1Name + "'s turn (X)";
                playerTurn.setText(displayTurn);
                gameBoard[i][j].setText("O");
            } else if (flipValue == 1) {
                displayTurn = player2Name + "'s turn (O)";
                playerTurn.setText(displayTurn);
                gameBoard[i][j].setText("X");
            }
            turn = 1;

        }
        checkWin();
        if (gamover == 1) {
            if (win == 1) {
                builder.setMessage(player1Name + " wins!").setTitle("Game over");
                if (flagEndGame == 0) {
                    player1Win++;
                    counter++;
                    TextView Player1score = findViewById(R.id.player1score);
                    Player1score.setText(String.valueOf(player1Win));
                }
            } else if (win == 2) {
                builder.setMessage(player2Name + " wins!").setTitle("Game over");
                if (flagEndGame == 0) {
                    player2Win++;
                    counter++;
                }
            }
            flagEndGame = 1;
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    resetGame(new View(getApplicationContext()));
                    if (counter == number) {
                        Intent intent = new Intent(getApplicationContext(), FinalResults.class);
                        intent.putExtra("Player 1 Wins", player1Win);
                        intent.putExtra("Player 2 Wins", player2Win);
                        intent.putExtra("Draws", draw);
                        intent.putExtra("Player 1 Name", player1Name);
                        intent.putExtra("Player 2 Name", player2Name);
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                            finish();
                        }
                    }
                }

            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        if (gamover == 0) {
            for (i = 0; i < 5; i++) {
                for (j = 0; j < 5; j++) {
                    if (!gameBoard[i][j].getText().toString().equals("X") && !gameBoard[i][j].getText().toString().equals("O")) {
                        flag = 1;
                        break;

                    }
                }
            }
            if (flag == 0) {
                builder.setMessage("It's a draw!").setTitle("Game over");
                if (flagEndGame == 0) {
                    counter++;
                    draw++;
                }
                flagEndGame = 1;
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        resetGame(new View(getApplicationContext()));
                        if (counter == number) {
                            Intent intent = new Intent(getApplicationContext(), FinalResults.class);
                            intent.putExtra("Player 1 Wins", player1Win);
                            intent.putExtra("Player 2 Wins", player2Win);
                            intent.putExtra("Draws", draw);
                            intent.putExtra("Player 1 Name", player1Name);
                            intent.putExtra("Player 2 Name", player2Name);
                            if (intent.resolveActivity(getPackageManager()) != null) {
                                startActivity(intent);
                                finish();
                            }

                        }
                    }

                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
    }
    public void resetGame(View view) {
        win = 0;
        gamover = 0;
        turn = 1;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                gameBoard[i][j].setText(" ");
                gameBoard[i][j].setTextColor(Color.WHITE);
            }
        }

        if (flipValue == 0) {
            if (flagEndGame == 1) {
                flipValue = 1;
                displayTurn = player2Name + "'s turn (O)";
                playerTurn.setText(displayTurn);
            } else {
                displayTurn = player1Name + "'s turn (X)";
                playerTurn.setText(displayTurn);
            }
        } else if (flipValue == 1) {
            if (flagEndGame == 1) {
                flipValue = 0;
                displayTurn = player1Name + "'s turn (X)";
                playerTurn.setText(displayTurn);
            } else {
                displayTurn = player2Name + "'s turn (O)";
                playerTurn.setText(displayTurn);
            }
        }
        flagEndGame = 0;
    }

    public void checkWin() {
        for (int i = 0; i < 5; i++) {
            if (gameBoard[i][0].getText().toString().equals(gameBoard[i][1].getText().toString()) && gameBoard[i][0].getText().toString().equals(gameBoard[i][2].getText().toString()) && gameBoard[i][0].getText().toString().equals(gameBoard[i][3].getText().toString()) && gameBoard[i][0].getText().toString().equals(gameBoard[i][4].getText().toString())) {
                if (gameBoard[i][0].getText().toString().equals("X")) {
                    gamover = 1;
                    if (flipValue == 0)
                        win = 1;
                    else if (flipValue == 1)
                        win = 1;
                } else if (gameBoard[i][0].getText().toString().equals("O")) {
                    gamover = 1;
                    if (flipValue == 0)
                        win = 2;
                    else if (flipValue == 1)
                        win = 2;

                }
                if (!gameBoard[i][0].getText().toString().equals(" ")) {
                    gameBoard[i][0].setTextColor(GREEN);
                    gameBoard[i][1].setTextColor(GREEN);
                    gameBoard[i][2].setTextColor(GREEN);
                    gameBoard[i][3].setTextColor(GREEN);
                    gameBoard[i][4].setTextColor(GREEN);

                }

            }
            if (gameBoard[0][i].getText().toString().equals(gameBoard[1][i].getText().toString()) && gameBoard[0][i].getText().toString().equals(gameBoard[2][i].getText().toString()) && gameBoard[0][i].getText().toString().equals(gameBoard[3][i].getText().toString()) && gameBoard[0][i].getText().toString().equals(gameBoard[4][i].getText().toString())) {
                if (gameBoard[0][i].getText().toString().equals("X")) {
                    gamover = 1;
                    if (flipValue == 0)
                        win = 1;
                    else if (flipValue == 1)
                        win = 1;


                } else if (gameBoard[0][i].getText().toString().equals("O")) {
                    gamover = 1;
                    if (flipValue == 0)
                        win = 2;
                    else if (flipValue == 1)
                        win = 2;

                }
                if (!gameBoard[0][i].getText().toString().equals(" ")) {
                    gameBoard[0][i].setTextColor(GREEN);
                    gameBoard[1][i].setTextColor(GREEN);
                    gameBoard[2][i].setTextColor(GREEN);
                    gameBoard[3][i].setTextColor(GREEN);
                    gameBoard[4][i].setTextColor(GREEN);
                }

            }


        }
        if (gameBoard[0][0].getText().toString().equals(gameBoard[1][1].getText().toString()) && gameBoard[0][0].getText().toString().equals(gameBoard[2][2].getText().toString()) && gameBoard[0][0].getText().toString().equals(gameBoard[3][3].getText().toString()) && gameBoard[0][0].getText().toString().equals(gameBoard[4][4].getText().toString())) {
            if (gameBoard[0][0].getText().toString().equals("X")) {
                gamover = 1;
                if (flipValue == 0)
                    win = 1;
                else if (flipValue == 1)
                    win = 1;


            } else if (gameBoard[0][0].getText().toString().equals("O")) {
                gamover = 1;
                if (flipValue == 0)
                    win = 2;
                else if (flipValue == 1)
                    win = 2;

            }
            if (!gameBoard[0][0].getText().toString().equals(" ")) {
                gameBoard[0][0].setTextColor(GREEN);
                gameBoard[1][1].setTextColor(GREEN);
                gameBoard[2][2].setTextColor(GREEN);
                gameBoard[3][3].setTextColor(GREEN);
                gameBoard[4][4].setTextColor(GREEN);
            }


        }
        if (gameBoard[0][4].getText().toString().equals(gameBoard[1][3].getText().toString()) && gameBoard[0][4].getText().toString().equals(gameBoard[2][2].getText().toString()) && gameBoard[0][4].getText().toString().equals(gameBoard[3][1].getText().toString()) && gameBoard[0][4].getText().toString().equals(gameBoard[4][0].getText().toString())) {
            if (gameBoard[0][4].getText().toString().equals("X")) {
                gamover = 1;
                if (flipValue == 0)
                    win = 1;
                else if (flipValue == 1)
                    win = 1;


            } else if (gameBoard[0][4].getText().toString().equals("O")) {
                gamover = 1;
                if (flipValue == 0)
                    win = 2;
                else if (flipValue == 1)
                    win = 2;

            }
            if (!gameBoard[4][0].getText().toString().equals(" ")) {
                gameBoard[4][0].setTextColor(GREEN);
                gameBoard[1][3].setTextColor(GREEN);
                gameBoard[2][2].setTextColor(GREEN);
                gameBoard[3][1].setTextColor(GREEN);
                gameBoard[0][4].setTextColor(GREEN);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    public void newMatch(View view) {
        Intent intent = new Intent(this, MultiPlayerProfile.class);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
            finish();
        }
    }
}
