package com.example.jayare.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    boolean gameIsActive = true;
    // 0 = O's 1 = X's
    int activePlayer = 0;
    //-1 means playable space
    int[] gameState = {-1,-1,-1,-1,-1,-1,-1,-1,-1};

    int[][] winningConditions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void reset(View view){
        gameIsActive = true;
        //reset gameState
        for(int i = 0;i<gameState.length;i++){
            gameState[i] = -1;
        }
        //hide play again and set alpha of board back to 1
        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgain);
        layout.setVisibility(View.INVISIBLE);
        GridLayout grid = (GridLayout) findViewById(R.id.boardLayout);
        grid.setVisibility(View.VISIBLE);

        activePlayer = 0; //reset first player

        //reset src's for images
        for(int i = 0;i<grid.getChildCount();i++){
            ((ImageView) grid.getChildAt(i)).setImageResource(0);
        }
    }

    public void winnerPopup(String s){
        gameIsActive = false;
        TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
        winnerMessage.setText(s);
        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgain);
        layout.setVisibility(View.VISIBLE);
        GridLayout grid = (GridLayout) findViewById(R.id.boardLayout);
        grid.setVisibility(View.INVISIBLE);
    }

    public boolean checkWin(){
        for(int[] win: winningConditions){
            if(gameState[win[0]] == gameState[win[1]]
                    && gameState[win[1]] == gameState[win[2]]
                    && gameState[win[2]] != -1){
                return true;
            }
        }
        return false;
    }

    public boolean checkFull(){
        for(int i:gameState){
            if(i == -1){
                return false;
            }
        }
        return true;
    }

    public void dropIn(View view){
        ImageView counter = (ImageView) view;

        int tag = Integer.parseInt(counter.getTag().toString());
        if(gameState[tag] != -1 && gameIsActive == false){
            return;
        }
        counter.setTranslationY(-1000f);

        if(activePlayer == 0){
            counter.setImageResource(R.drawable.o);
            activePlayer = 1;
            gameState[tag] = activePlayer;
        }
        else{
            counter.setImageResource(R.drawable.x);
            activePlayer = 0;
            gameState[tag] = activePlayer;
        }
        counter.animate().translationYBy(1000f).setDuration(300);

        if(checkWin()){
            char winner = (activePlayer == 1)? 'O':'X';
            winnerPopup("Player " + winner +" wins! Play again");
            //suspend background playing
        }
        else if(checkFull()){
            winnerPopup("It's a tie! Play again");
        }

    }
}
