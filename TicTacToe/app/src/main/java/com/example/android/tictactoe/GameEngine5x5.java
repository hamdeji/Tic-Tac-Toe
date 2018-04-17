package com.example.android.tictactoe;

import java.util.Random;

/**
 * The Single player game against computer on a 5x5 game board setup
 */
public class GameEngine5x5 {
    private static final Random RANDOM = new Random();
    private char[] elts;
    private char currentPlayer;
    private boolean ended;

    public GameEngine5x5() {
        elts = new char[25];
        newGame();
    }

    public boolean isEnded() {

        return ended;
    }

    public char play(int x, int y) {
        if (!ended && elts[5 * y + x] == ' ') {
            elts[5 * y + x] = currentPlayer;
            changePlayer();
        }

        return checkEnd();
    }

    public void changePlayer() {

        currentPlayer = (currentPlayer == 'X' ? 'O' : 'X');
    }

    public char getElt(int x, int y) {
        return elts[5 * y + x];
    }

    public void newGame() {
        for (int i = 0; i < elts.length; i++) {
            elts[i] = ' ';
        }

        currentPlayer = 'X';
        ended = false;
    }

    public char checkEnd() {
        for (int i = 0; i < 5; i++) {
            if (getElt(i, 0) != ' ' &&
                    getElt(i, 0) == getElt(i, 1) &&
                    getElt(i, 1) == getElt(i, 2) &&
                    getElt(i, 2) == getElt(i, 3) &&
                    getElt(i, 3) == getElt(i, 4)) {
                ended = true;
                return getElt(i, 0);
            }

            if (getElt(0, i) != ' ' &&
                    getElt(0, i) == getElt(1, i) &&
                    getElt(1, i) == getElt(2, i) &&
                    getElt(2, i) == getElt(3, i) &&
                    getElt(3, i) == getElt(4, i)) {
                ended = true;
                return getElt(0, i);
            }
        }

        if (getElt(0, 0) != ' ' &&
                getElt(0, 0) == getElt(1, 1) &&
                getElt(1, 1) == getElt(2, 2) &&
                getElt(2, 2) == getElt(3, 3) &&
                getElt(3, 3) == getElt(4, 4)) {
            ended = true;
            return getElt(0, 0);
        }

        if (getElt(4, 0) != ' ' &&
                getElt(4, 0) == getElt(3, 1) &&
                getElt(3, 1) == getElt(2, 2) &&
                getElt(2, 2) == getElt(1, 3) &&
                getElt(1, 3) == getElt(0, 4)) {
            ended = true;
            return getElt(4, 0);
        }

        for (int i = 0; i < 25; i++) {
            if (elts[i] == ' ')
                return ' ';
        }

        return 'T';
    }

    public char computer() {
        if (!ended) {
            int position = -1;

            do {
                position = RANDOM.nextInt(25);
            } while (elts[position] != ' ');

            elts[position] = currentPlayer;
            changePlayer();
        }

        return checkEnd();
    }

}
