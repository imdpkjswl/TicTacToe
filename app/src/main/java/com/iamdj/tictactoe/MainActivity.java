package com.iamdj.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Add Vibration on cell click
    Vibrator vibe;

    boolean gameActive = true;

    // Player Representation:
    // 0 -> X
    // 1 -> O
    int activePlayer = 0; // active player 'X' initialize

    // State meanings:
    // 0 -> X
    // 1 -> O
    // 2 -> Null(Empty)
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    // Winning Position Representation:
    int[][] winPosition = { {0,1,2}, {3,4,5}, {6,7,8},
                            {0,3,6}, {1,4,7}, {2,5,8},
                            {0,4,8}, {2,4,6}           };



    public void playerTap(View view){
        ImageView img = (ImageView) view;
        int tappedImage = Integer.parseInt(img.getTag().toString()); // get tag and convert it into string.

        // reset game
        if(!gameActive){
            gameReset();
            return;
        }

        if(gameState[tappedImage] == 2){ // when cell will be vacant.
            vibe.vibrate(30); // vibrate on click
            gameState[tappedImage] =activePlayer; // put active player
            img.setTranslationY(-1000f);// for animation

            // Now, change active player
            if(activePlayer == 0){
                img.setImageResource(R.drawable.x);
                activePlayer = 1;
                // Add Status which shows turn
                TextView st = findViewById(R.id.playerStatus);
                st.setText("O's turn - Tap to play");
            }
            else{
                img.setImageResource(R.drawable.o);
                activePlayer = 0;
                // Add Status which shows turn
                TextView st = findViewById(R.id.playerStatus);
                st.setText("X's turn - Tap to play");
            }

            img.animate().translationYBy(1000f).setDuration(400); // add animation
        }else{
                // When draw condition occur
                TextView st = findViewById(R.id.playerStatus);
                st.setText("Match drew, Tap to Play");
                gameReset(); // Reset
                return;
            }

        // Check if any player has won
        for(int[] winPos: winPosition){
            if(gameState[winPos[0]] == gameState[winPos[1]] &&
                    gameState[winPos[1]] == gameState[winPos[2]] &&
                    gameState[winPos[2]] != 2) {

                // Somebody has won - Find out who?
                gameActive = false; // Reset game after win the game.
                String winnerStr;
                if(gameState[winPos[0]] == 0){
                    winnerStr = "X has won";
                }else{
                    winnerStr = "O has won";
                }
                // Update status bar for winner announcement
                TextView st = findViewById(R.id.playerStatus);
                st.setText(winnerStr);
            }
        }

    }

    private void gameReset() {
        gameActive = true;
        activePlayer = 0;
        for(int i = 0; i < gameState.length; i++){
            gameState[i] = 2;
        }

        ((ImageView)findViewById(R.id.imageView0)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView1)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView2)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView3)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView4)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView5)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView6)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView7)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView8)).setImageResource(0);


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Uses system service to perform vibration on wrong answer chosen.
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

    }
}