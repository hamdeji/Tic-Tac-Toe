package com.example.android.tictactoe;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import static android.graphics.Color.GREEN;

/**
 * play game against computer on a 3x3 game board when user selects X in the Single player profile.
 */
public class SinglePlayerGameX extends AppCompatActivity {
    int turn = 1, win = 0, gamover = 0, flagEndGame = 0, flag;
    String displayTurn;
    GridLayout grid;
    Button gameBoard[][] = new Button[3][3];
    int boardArray[][] = new int[3][3];
    double probArray[][] = new double[3][3];
    TextView playerTurn;
    String player1Name, player2Name, numberText;
    int number, moveNumber = 1, counter = 0, player1Win = 0, player2Win = 0, draw = 0;
    int flipValue = 0;
    AlertDialog.Builder builder;
    int level = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player_gamex);
        playerTurn = findViewById(R.id.Splayer);
        builder = new AlertDialog.Builder(this);
        Intent intent = getIntent();
        player1Name = intent.getExtras().getString("Player 1");
        player2Name = "Computer";
        numberText = intent.getExtras().getString("Number");
        number = Integer.parseInt(numberText);
        grid = findViewById(R.id.grid); // 3x3 game grid
        displayTurn = player1Name + "'s turn (X)";
        playerTurn.setText(displayTurn);
        TextView Splayername = findViewById(R.id.Splayername);
        Splayername.setText(player1Name);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameBoard[i][j] = (Button) grid.getChildAt(3 * i + j); // gameBoard initialization
                boardArray[i][j] = 0;
            }
        }
        if (flipValue == 1) {
            computerGame();
            turn = 2;
        }
    }
    public void playmove(View view) {
        int index = grid.indexOfChild(view);
        int i = index / 3;
        int j = index % 3;
        flag = 0;
        if (turn == 1 && gamover == 0 && !(gameBoard[i][j].getText().toString().equals("X")) && !(gameBoard[i][j].getText().toString().equals("O"))) {
            if (flipValue == 0) {
                displayTurn = player2Name + "'s turn (O)";
                playerTurn.setText(displayTurn);
                gameBoard[i][j].setText("X");
                boardArray[i][j] = 1;
                turn = 2;
                moveNumber++;
                computerGame();
                turn = 1;
                displayTurn = player1Name + "'s turn (X)";
                moveNumber++;
            }


        } else if (turn == 2 && gamover == 0 && !(gameBoard[i][j].getText().toString().equals("X")) && !(gameBoard[i][j].getText().toString().equals("O"))) {

            if (flipValue == 1) {
                displayTurn = player2Name + "'s turn (O)";
                playerTurn.setText(displayTurn);
                gameBoard[i][j].setText("X");
                boardArray[i][j] = 1;
                turn = 1;
                moveNumber++;
                computerGame();
                displayTurn = player1Name + "'s turn (X)";
                turn = 2;
                moveNumber++;
            }
        }
        checkWin();
        if (gamover == 1) {
            if (win == 1) {
                builder.setMessage(player1Name + " wins!").setTitle("Game over");
                if (flagEndGame == 0) {
                    player1Win++;
                    counter++;
                    TextView Splayerscore1 = findViewById(R.id.Splayerscore);
                    Splayerscore1.setText(String.valueOf(player1Win));
                }
            } else if (win == 2) {
                builder.setMessage(player2Name + " wins!").setTitle("Game over");
                if (flagEndGame == 0) {
                    player2Win++;
                    counter++;
                    TextView ComputerScore = findViewById(R.id.Computerscore);
                    ComputerScore.setText(String.valueOf(player2Win));
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
            for (i = 0; i < 3; i++) {
                for (j = 0; j < 3; j++) {
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
                    TextView drawScore1 = findViewById(R.id.Drawscore);
                    drawScore1.setText(String.valueOf(draw));
                }
                flagEndGame = 1;
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

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

                        } else {
                            resetGame(new View(getApplicationContext()));
                        }
                    }

                });
                AlertDialog dialog = builder.create();
                dialog.show();


            }


        }


    }

    public void randomGame() {
        int random = (int) (Math.random() * 9);
        int i = random / 3;
        int j = random % 3;
        gameBoard[i][j].setText("O");
        boardArray[i][j] = 1;


    }

    public void computerGame() {
        int currentTurn = turn;
        int currentMove = moveNumber;
        int i = 0, j = 0;
        int moveChoice = 0;
        int flag = 0;
        int flagGameNotOver = 0;
        if (turn == 1) {
            turn = 2;
        } else {
            turn = 1;
        }
        for (int c = 0; c < 9; c++) {
            i = c / 3;
            j = c % 3;
            //  tempBoard[i][j].setText(gameBoard[i][j].getText().toString());
            probArray[i][j] = 0;
        }

        for (int c = 0; c < 9; c++) {
            i = c / 3;
            j = c % 3;
            if (boardArray[i][j] == 0) {
                flagGameNotOver = 1;
                // Log.e("INCP", "I got "+String.valueOf(i)+" "+String.valueOf(j));
                boardArray[i][j] = 1;
                if (flipValue == 1)
                    gameBoard[i][j].setText("O");
                else
                    gameBoard[i][j].setText("O");
                if (checkWinComp() == 2 && flipValue == 0) {
                    flag = 1;
                    gameBoard[i][j].setText(" ");
                    boardArray[i][j] = 0;
                    break;
                } else if (checkWinComp() == 2 && flipValue == 1) {
                    flag = 1;
                    gameBoard[i][j].setText(" ");
                    boardArray[i][j] = 0;
                    break;
                }
                if (checkWinComp() == 1 && flipValue == 1) {
                    gameBoard[i][j].setText(" ");
                    //  Log.v("CP","I came to the first if");
                    boardArray[i][j] = 0;
                    continue;
                } else if (checkWinComp() == 1 && flipValue == 0) {
                    gameBoard[i][j].setText(" ");
                    //   Log.v("CP","I came to the second if");
                    boardArray[i][j] = 0;
                    continue;

                } else {
                    level++;
                    probArray[i][j] = computerAnalyze();
                    //    double value = computerAnalyze();
//                    sum+=value;
//                    counter++;
                    level--;
                    //   Log.v("CP","Analysis has been done! " + String.valueOf(i)+" "+String.valueOf(j));

                }
                gameBoard[i][j].setText(" ");
                boardArray[i][j] = 0;
                //probArray[i][j]=sum;

            }
        }
        if (flagGameNotOver == 0) {
            return;
        }
        double maxProb = 0;
        if (flag == 0) {
            for (int p = 0; p < 3; p++) {
                for (int q = 0; q < 3; q++) {
                    if (maxProb < probArray[p][q]) {
                        maxProb = probArray[p][q];
                    }
                }
            }
            for (int p = 0; p < 3; p++) {
                for (int q = 0; q < 3; q++) {
                    if (maxProb == probArray[p][q] && boardArray[p][q] == 0) {
                        moveChoice = 3 * p + q;
                        break;
                    }
                }
            }
        } else {
            moveChoice = 3 * i + j;
        }
        turn = currentTurn;
        moveNumber = currentMove;
        int xCoord = moveChoice / 3;
        int yCoord = moveChoice % 3;
        boardArray[xCoord][yCoord] = 1;
        if (flipValue == 0) {
            gameBoard[xCoord][yCoord].setText("O");
            displayTurn = player1Name + "'s turn (X)";
            playerTurn.setText(displayTurn);
        } else {
            gameBoard[xCoord][yCoord].setText("O");
            displayTurn = player1Name + "'s turn (X)";
            playerTurn.setText(displayTurn);
        }

    }

    public double computerAnalyze() {
        double sum = 0;
        int counter = 0;
        int flagCheckGameNotOver = 0;
        for (int c = 0; c < 9; c++) {
            int i = c / 3;
            int j = c % 3;

            if (boardArray[i][j] == 0) {
                flagCheckGameNotOver = 1;
                boardArray[i][j] = 1;

                if (turn == 1)
                    gameBoard[i][j].setText("X");
                else
                    gameBoard[i][j].setText("X");
                if (checkWinComp() == 2 && flipValue == 0) {
                    sum = 1;
                    gameBoard[i][j].setText(" ");
                    boardArray[i][j] = 0;

                    return sum;
                } else if (checkWinComp() == 2 && flipValue == 1) {
                    sum = 1;
                    gameBoard[i][j].setText(" ");
                    boardArray[i][j] = 0;

                    return sum;
                } else if (checkWinComp() == 1 && flipValue == 1) {
                    sum = 0;
                    gameBoard[i][j].setText(" ");
                    boardArray[i][j] = 0;

                    return sum;
                } else if (checkWinComp() == 1 && flipValue == 0) {
                    sum = 0;
                    gameBoard[i][j].setText(" ");
                    boardArray[i][j] = 0;

                    return sum;
                } else {
                    counter++;
                    if (turn == 1) {
                        turn = 2;
                    } else {
                        turn = 1;
                    }
                    level++;
                    double value = computerAnalyze();
                    level--;
                    sum += value;
                }
                gameBoard[i][j].setText(" ");
                boardArray[i][j] = 0;
                if (turn == 1) {
                    turn = 2;
                } else {
                    turn = 1;
                }
            }

        }
        if (flagCheckGameNotOver == 0) {
            return 0.5;
        }
        return (sum) / ((double) counter);
    }

    public void resetGame(View view) {

        win = 0;
        gamover = 0;
        turn = 1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameBoard[i][j].setText(" ");
                gameBoard[i][j].setTextColor(Color.WHITE);
                boardArray[i][j] = 0;
            }
        }

        if (flipValue == 0) {
            if (flagEndGame == 1) {
                flipValue = 1;
                displayTurn = player1Name + "'s turn (X)";
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
        if (flipValue == 1) {

            randomGame();
            turn = 2;
        }
    }

    public void checkWin() {
        for (int i = 0; i < 3; i++) {
            if (gameBoard[i][0].getText().toString().equals(gameBoard[i][1].getText().toString()) && gameBoard[i][0].getText().toString().equals(gameBoard[i][2].getText().toString())) {
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

                }

            }
            if (gameBoard[0][i].getText().toString().equals(gameBoard[1][i].getText().toString()) && gameBoard[0][i].getText().toString().equals(gameBoard[2][i].getText().toString())) {
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
                }

            }


        }
        if (gameBoard[0][0].getText().toString().equals(gameBoard[1][1].getText().toString()) && gameBoard[0][0].getText().toString().equals(gameBoard[2][2].getText().toString())) {
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
            }


        }
        if (gameBoard[0][2].getText().toString().equals(gameBoard[1][1].getText().toString()) && gameBoard[0][2].getText().toString().equals(gameBoard[2][0].getText().toString())) {
            if (gameBoard[0][2].getText().toString().equals("X")) {
                gamover = 1;
                if (flipValue == 0)
                    win = 1;
                else if (flipValue == 1)
                    win = 1;


            } else if (gameBoard[0][2].getText().toString().equals("O")) {
                gamover = 1;
                if (flipValue == 0)
                    win = 2;
                else if (flipValue == 1)
                    win = 2;

            }
            if (!gameBoard[2][0].getText().toString().equals(" ")) {
                gameBoard[2][0].setTextColor(GREEN);
                gameBoard[1][1].setTextColor(GREEN);
                gameBoard[0][2].setTextColor(GREEN);
            }


        }
    }

    public int checkWinComp() {
        for (int i = 0; i < 3; i++) {
            if (gameBoard[i][0].getText().toString().equals(gameBoard[i][1].getText().toString()) && gameBoard[i][0].getText().toString().equals(gameBoard[i][2].getText().toString())) {
                if (gameBoard[i][0].getText().toString().equals("X")) {

                    if (flipValue == 0)
                        return 1;
                    else if (flipValue == 1)
                        return 1;


                } else if (gameBoard[i][0].getText().toString().equals("O")) {

                    if (flipValue == 0)
                        return 2;
                    else if (flipValue == 1)
                        return 2;

                }


            }
            if (gameBoard[0][i].getText().toString().equals(gameBoard[1][i].getText().toString()) && gameBoard[0][i].getText().toString().equals(gameBoard[2][i].getText().toString())) {
                if (gameBoard[0][i].getText().toString().equals("X")) {

                    if (flipValue == 0)
                        return 1;
                    else if (flipValue == 1)
                        return 1;


                } else if (gameBoard[0][i].getText().toString().equals("O")) {

                    if (flipValue == 0)
                        return 2;
                    else if (flipValue == 1)
                        return 2;

                }


            }


        }
        if (gameBoard[0][0].getText().toString().equals(gameBoard[1][1].getText().toString()) && gameBoard[0][0].getText().toString().equals(gameBoard[2][2].getText().toString())) {
            if (gameBoard[0][0].getText().toString().equals("X")) {

                if (flipValue == 0)
                    return 1;
                else if (flipValue == 1)
                    return 1;


            } else if (gameBoard[0][0].getText().toString().equals("O")) {

                if (flipValue == 0)
                    return 2;
                else if (flipValue == 1)
                    return 2;

            }


        }
        if (gameBoard[0][2].getText().toString().equals(gameBoard[1][1].getText().toString()) && gameBoard[0][2].getText().toString().equals(gameBoard[2][0].getText().toString())) {
            if (gameBoard[0][2].getText().toString().equals("X")) {

                if (flipValue == 0)
                    return 1;
                else if (flipValue == 1)
                    return 1;


            } else if (gameBoard[0][2].getText().toString().equals("O")) {

                if (flipValue == 0)
                    return 2;
                else if (flipValue == 1)
                    return 2;
            }
        }
        return 0;
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
        Intent intent = new Intent(this, SinglePlayerGameX.class);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
            finish();
        }
    }

}