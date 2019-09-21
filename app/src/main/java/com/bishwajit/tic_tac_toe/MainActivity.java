/* TicTacToe game
 * Assignment 1
 *
 * Revision History
 * Bishwajit Barua, 2018.09.22: Created
 */

package com.bishwajit.tic_tac_toe;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Declear variable player activity
    int activePlayer=0;
    String winner="";

    // 2 represent unplayed
    int[] gameState={2,2,2,2,2,2,2,2,2};

    // Variable for button information
    String [] playButton={"","","","","","","","",""};
    // Variable for Wining equation
    int[][] winningPositions={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    int counting=0;
    String playerX="Player X's turn";
    float rotationText=360f;
    int lockGame=0;

    // Method use for tapping on for play
    public void dropIn(View view){

            TextView counter = (TextView) view;
            TextView playerMessage = (TextView) findViewById(R.id.txtMessage);
            int tappedCounter = Integer.parseInt(counter.getTag().toString());
            MediaPlayer mediaplayerX = MediaPlayer.create(this, R.raw.ice);
            MediaPlayer mediaplayerY = MediaPlayer.create(this, R.raw.sms);
            String playerO="Player O's turn";
            String resultDraw="DRAW !!!";
            String resultWinnerX="X is Winner";
            String resultWinnerO="O is Winner";
            String PlayerX="X";
            String PlayerO="O";

            storeData.player_name_X=storeData.player1_name;
            storeData.player_name_O=storeData.player2_name;

            TextView player1_Name = (TextView) findViewById(R.id.txtPlayer1);
            player1_Name.setText(storeData.player1_name + " AS X");

            TextView player2_Name = (TextView) findViewById(R.id.txtPlayer2);
            player2_Name.setText(storeData.player2_name + " AS O");

        try {
            if(lockGame==0) {

                if (storeData.lockOn == 0) {
                    storeData.lockOn = 1;
                }
                if (gameState[tappedCounter] == 2) {
                    gameState[tappedCounter] = activePlayer;
                    counting++;
                    if (activePlayer == 0) {
                        mediaplayerX.start();
                        counter.setText(PlayerX);
                        playButton[tappedCounter] = PlayerX;
                        playerMessage.setText(playerO);
                        activePlayer = 1;
                    } else if (activePlayer == 1) {
                        mediaplayerY.start();
                        counter.setText(PlayerO);
                        playButton[tappedCounter] = PlayerO;
                        playerMessage.setText(playerX);
                        activePlayer = 0;
                    }
                    if (counting == 9) {
                        playerMessage.setText(resultDraw);
                        counting = 0;
                        lockGame=1;
                        storeData.ties(this);
                    }

                    for (int[] winningPosition : winningPositions) {
                        if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                                gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                                gameState[winningPosition[0]] != 2) {

                            if (gameState[winningPosition[0]] == 1) {
                                winner = resultWinnerO;
                                lockGame=1;
                                storeData.winO(this);
                            } else if (gameState[winningPosition[0]] == 0) {
                                winner = resultWinnerX;
                                lockGame=1;
                                storeData.winX(this);
                            }
                            playerMessage.setText(winner);
                            playerMessage.animate().rotationBy(rotationText).setDuration(3000).start();
                            activePlayer = 3;
                        }
                    }

                }
            }
        } catch (Exception e)
        {
            e.getMessage();
        }
    }

    // Refresh the game or restart
    public void newGame(View view){
        //Reset all variable
        activePlayer=0;
        counting=0;
        gameState= new int[]{2,2,2,2,2,2,2,2,2};
        playButton= new String[]{"","","","","","","","",""};
        lockGame=0;

        try {
        storeData.variableReset ();

        TextView playerbutton = (TextView) findViewById(R.id.btnPlay0);
        playerbutton.setText("");

        TextView playerbutton1 = (TextView) findViewById(R.id.btnPlay1);
        playerbutton1.setText("");

        TextView playerbutton2 = (TextView) findViewById(R.id.btnPlay2);
        playerbutton2.setText("");

        TextView playerbutton3 = (TextView) findViewById(R.id.btnPlay3);
        playerbutton3.setText("");

        TextView playerbutton4 = (TextView) findViewById(R.id.btnPlay4);
        playerbutton4.setText("");

        TextView playerbutton5 = (TextView) findViewById(R.id.btnPlay5);
        playerbutton5.setText("");

        TextView playerbutton6 = (TextView) findViewById(R.id.btnPlay6);
        playerbutton6.setText("");

        TextView playerbutton7 = (TextView) findViewById(R.id.btnPlay7);
        playerbutton7.setText("");

        TextView playerbutton8 = (TextView) findViewById(R.id.btnPlay8);
        playerbutton8.setText("");

        TextView playerMessage = (TextView) findViewById(R.id.txtMessage);
        playerMessage.setText(playerX);
        } catch (Exception e)
        {
            e.getMessage();
        }
    }

    // Change orientation
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        try {
            super.onConfigurationChanged(newConfig);
            if(newConfig.orientation==Configuration.ORIENTATION_PORTRAIT){
                Toast.makeText(getApplicationContext(), "Portrait mode", Toast.LENGTH_LONG).show();
            } else if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){
                Toast.makeText(getApplicationContext(), "Lanscape mode", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e)
        {
            e.getMessage();
        }
    }

    //Save data when orientation changes
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        try{
            super.onSaveInstanceState(outState);
            outState.putIntArray("gameStateSave", gameState);
            outState.putStringArray("playButtonSave", playButton);

            TextView playerMessage = (TextView) findViewById(R.id.txtMessage);
            String playerMessageSave=playerMessage.getText().toString();
            outState.putString("playerMessageSaveFinal", playerMessageSave);
            outState.putInt("countingSave", counting);
            outState.putInt("activePlayerSave", activePlayer);

        } catch (Exception e)
        {
            e.getMessage();
        }
    }

    //Restore screen data when orientation changes
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        try {

            int[] gameStateRestore=savedInstanceState.getIntArray("gameStateSave");
            for (int i = 0; i < gameStateRestore.length; i++) {
                gameState[i]=gameStateRestore[i];
            }

            String[] playButtonRestore=savedInstanceState.getStringArray("playButtonSave");
            for (int i = 0; i < playButtonRestore.length; i++) {
                playButton[i]=playButtonRestore[i];
            }

            String playerMessageRestore=savedInstanceState.getString("playerMessageSaveFinal");
            int countingRestore=savedInstanceState.getInt("countingSave");
            counting=countingRestore;

            int activePlayerRestore=savedInstanceState.getInt("activePlayerSave");
            activePlayer=activePlayerRestore;

            TextView playerbutton = (TextView) findViewById(R.id.btnPlay0);
            playerbutton.setText(playButton[0]);

            TextView playerbutton1 = (TextView) findViewById(R.id.btnPlay1);
            playerbutton1.setText(playButton[1]);

            TextView playerbutton2 = (TextView) findViewById(R.id.btnPlay2);
            playerbutton2.setText(playButton[2]);

            TextView playerbutton3 = (TextView) findViewById(R.id.btnPlay3);
            playerbutton3.setText(playButton[3]);

            TextView playerbutton4 = (TextView) findViewById(R.id.btnPlay4);
            playerbutton4.setText(playButton[4]);

            TextView playerbutton5 = (TextView) findViewById(R.id.btnPlay5);
            playerbutton5.setText(playButton[5]);

            TextView playerbutton6 = (TextView) findViewById(R.id.btnPlay6);
            playerbutton6.setText(playButton[6]);

            TextView playerbutton7 = (TextView) findViewById(R.id.btnPlay7);
            playerbutton7.setText(playButton[7]);

            TextView playerbutton8 = (TextView) findViewById(R.id.btnPlay8);
            playerbutton8.setText(playButton[8]);

            TextView playerMessage = (TextView) findViewById(R.id.txtMessage);
            playerMessage.setText(playerMessageRestore);

        } catch (Exception e)
        {
            e.getMessage();
        }
    }

    // Start app
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView player1_Name = (TextView) findViewById(R.id.txtPlayer1);
        player1_Name.setText(storeData.player1_name + " AS X");

        TextView player2_Name = (TextView) findViewById(R.id.txtPlayer2);
        player2_Name.setText(storeData.player2_name + " AS O");


    }


    public void playerShows(){
        if(storeData.player1_name.equals("None1")  || storeData.player2_name.equals("None2")  ) {
            storeData.checkPlayer=1;

            if(storeData.player1_name.equals("None1")  && storeData.player2_name.equals("None2") ){
                Toast.makeText(MainActivity.this, "Please select BOTH players.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, SelectPlayer1.class);
                startActivity(intent);
            }
            else if(storeData.player1_name.equals("None1") )
            {
                Toast.makeText(MainActivity.this, "Select player 1", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, SelectPlayer1.class);
                startActivity(intent);
            }
            else if(storeData.player2_name.equals("None2") )
            {
                Toast.makeText(MainActivity.this, "Select player 2", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, SelectPlayer2.class);
                startActivity(intent);
            }
        }
        else
        {
            TextView player1_Name = (TextView) findViewById(R.id.txtPlayer1);
            player1_Name.setText(storeData.player1_name + " AS X");

            TextView player2_Name = (TextView) findViewById(R.id.txtPlayer2);
            player2_Name.setText(storeData.player2_name + " AS O");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);


        int id= item.getItemId();
        if(id==R.id.start_game || id==R.id.start_game_menu){
            Intent intent = new Intent(this, SelectPlayer1.class);
            startActivity(intent);
        }
        else if (id==R.id.select_payer_1){
            Intent intent = new Intent(this, SelectPlayer1.class);
            startActivity(intent);
        }
        else if (id==R.id.view_scoreboard || id==R.id.Scoreboard_menu){
            Intent intent = new Intent(this, view_scoreboard.class);
            startActivity(intent);
        }
        else if (id==R.id.select_payer_2){
            Intent intent = new Intent(this, SelectPlayer2.class);
            startActivity(intent);
        }
        else if (id==R.id.add_player){
            Intent intent = new Intent(this, AddPlayer.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);

    }
}
