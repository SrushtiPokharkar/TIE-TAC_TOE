package com.example.srupra.tictacgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int player1points = 0;
    private int player2points = 0;

    private boolean player1Turn = true;
    private int roundCount = 0;
    private boolean win = false;

    private Button buttons[][] = new Button[3][3];

    private TextView player1TextView;
    private TextView player2TextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player1TextView = findViewById(R.id.player1TextView);
        player2TextView = findViewById(R.id.player2TextView);

        for (int i = 0; i <3 ; i++) {
            for (int j = 0; j <3 ; j++) {
                int buttonID = getResources().getIdentifier("button"+i+j, "id", getPackageName());
                buttons[i][j] = (Button) findViewById(buttonID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonClear = (Button) findViewById(R.id.button_clear);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetBoard();
            }
        });

        Button buttonReset = (Button) findViewById(R.id.buttonReset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player1points = 0;
                player2points = 0;
                updateTextView();
                clearGame();
                resetBoard();
            }
        });
    }


    @Override
    public void onClick(View bt) {

        if ( !((Button) bt).getText().toString().equals("") || win) {
            return;
        }

        if ( player1Turn ) {
            ((Button) bt).setText("X");
            player2TextView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            player1TextView.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        } else {
            ((Button) bt).setText("O");
            player1TextView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            player2TextView.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        }

        roundCount++;

        win = checkWinCase();

        if (win) {
            if (player1Turn) {
                player1points++;
                Toast.makeText(this, "Player 1 win!", Toast.LENGTH_SHORT).show();

            } else {
                player2points++;
                Toast.makeText(this, "Player 2 win!", Toast.LENGTH_SHORT).show();

            }

            updateTextView();
            clearGame();

        } else if (roundCount==9){

            Toast.makeText(this, "Draw", Toast.LENGTH_SHORT).show();

        } else {

            player1Turn = !player1Turn;

        }

    }

    private boolean checkWinCase() {

        String fields[][] = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                fields[i][j] =  buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (fields[i][0].equals(fields[i][1]) && fields[i][0].equals(fields[i][2])
                    && !fields[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (fields[0][i].equals(fields[1][i]) && fields[0][i].equals(fields[2][i])
                    && !fields[0][i].equals("")) {
                return true;
            }
        }

        if (fields[0][0].equals(fields[1][1]) && fields[0][0].equals(fields[2][2])
                && !fields[0][0].equals("")) {
            return true;
        }

        if (fields[0][2].equals(fields[1][1]) && fields[0][2].equals(fields[2][0])
                && !fields[0][2].equals("")) {
            return true;
        }

        return false;

    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        win = false;
    }

    private void updateTextView() {
        player1TextView.setText("Player 1: " + player1points);
        player2TextView.setText("Player 2: " + player2points);
    }

    private void clearGame() {
        roundCount = 0;
        player1Turn = true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Point", player1points);
        outState.putInt("player2Point", player2points);
        outState.putBoolean("player1Turn", player1Turn);
        outState.putBoolean("win", win);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        player1points = savedInstanceState.getInt("player1Point");
        player2points = savedInstanceState.getInt("player2Point");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
        win = savedInstanceState.getBoolean("win");
    }
}
