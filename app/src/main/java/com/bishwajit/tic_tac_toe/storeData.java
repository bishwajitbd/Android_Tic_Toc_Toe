package com.bishwajit.tic_tac_toe;
import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public class storeData {
    public static ArrayList<String> itemList;
    public static String player1_name="None1";
    public static String player2_name="None2";
    public static int lockOn=0;
    public static int lockUpdateX=0;
    public static int lockUpdateO=0;
    public static int lockUpdateTies=0;


    public static String player_name_X;
    public static int winX=0;
    public static int lossX=0;
    public static int tiesX=0;

    public static String player_name_O;
    public static int winO=0;
    public static int lossO=0;
    public static int tiesO=0;

    public static int checkPlayer=1;

    private static ScoreBoardDBActivity db;

    public static void variableReset (){
        //player1_name="None1";
        //player2_name="None2";
        lockOn=0;
        lockUpdateX=0;
        lockUpdateO=0;
        lockUpdateTies=0;

        winX=0;
        lossX=0;
        tiesX=0;

        winO=0;
        lossO=0;
        tiesO=0;
    }


    //public static List<ScoreBoard> scoreBoard = new ArrayList<ScoreBoard>();

    public static void result (Context oContext, int winX, int lossX, int tieX,
                               int winO, int lossO, int tieO){
        db = new ScoreBoardDBActivity(oContext);

        storeData.player_name_X=storeData.player1_name;
        storeData.player_name_O=storeData.player2_name;

        try {
            db.addScoreBoard(player_name_X, winX, lossX, tieX);
            db.addScoreBoard(player_name_O, winO, lossO, tieO);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void winX (Context oContext){
        result (oContext, 1, 0, 0, 0, 1, 0);
    }

    public static void winO(Context oContext){
        result (oContext, 0, 1, 0, 1, 0, 0);
    }
//==============================================================
    public static void ties(Context oContext){
        result (oContext, 0, 0, 1, 0, 0, 1);
    }


}



