package com.bishwajit.tic_tac_toe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ScoreBoardDBActivity {
    // database constants
    public static final String DB_NAME = "scroboardDB.sqlite";
    public static final int    DB_VERSION = 1;

    private static final String TABLE_NAME = "scorboard";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "player_name";
    private static final String COLUMN_WINS = "wins";
    private static final String COLUMN_LOSSES = "losses";
    private static final String COLUMN_TIES = "ties";

    private static class ScoreboardDBHelper extends SQLiteOpenHelper {

        public ScoreboardDBHelper(Context context, String name,
                        SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // create tables
            String sql = "CREATE TABLE " + TABLE_NAME + " (\n" +
                    "    " + COLUMN_ID + " INTEGER NOT NULL CONSTRAINT employees_pk PRIMARY KEY AUTOINCREMENT,\n" +
                    "    " + COLUMN_NAME + " varchar(20) NOT NULL,\n" +
                    "    " + COLUMN_WINS + " INTEGER NOT NULL DEFAULT 0,\n" +
                    "    " + COLUMN_LOSSES + " INTEGER NOT NULL DEFAULT 0,\n" +
                    "    " + COLUMN_TIES + " INTEGER NOT NULL DEFAULT 0\n" +
                    ");";

            db.execSQL(sql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            String sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
            db.execSQL(sql);
            onCreate(db);
        }
    }
    // database and database helper objects
    private SQLiteDatabase db;
    private ScoreboardDBHelper scoreboardDBHelper;

    // constructor
    public ScoreBoardDBActivity(Context context) {
        scoreboardDBHelper = new ScoreboardDBHelper(context, DB_NAME, null, DB_VERSION);

        openWriteableDB();
        closeDB();
    }

    // private methods
    private void openReadableDB() {
        db = scoreboardDBHelper.getReadableDatabase();
    }

    private void openWriteableDB() {
        db = scoreboardDBHelper.getWritableDatabase();
    }

    private void closeDB() {
        if (db != null)
            db.close();
    }

    void addScoreBoard(String player_name, int wins, int losses, int ties)throws Exception{

        openWriteableDB();
        ContentValues content = new ContentValues();
        content.put(COLUMN_NAME, player_name);
        content.put(COLUMN_WINS, wins);
        content.put(COLUMN_LOSSES, losses);
        content.put(COLUMN_TIES, ties);
        long nResult = db.insert(TABLE_NAME,null, content);
        if(nResult == -1) throw new Exception("no data");
        closeDB();
    }

    /*public void deleteRow()
    {
        openWriteableDB();
        db.execSQL("DELETE FROM " + TABLE_NAME+ " WHERE "+COLUMN_WINS+"='"+0+"'"+ " AND "+COLUMN_LOSSES+"='"+0+"'"+ " AND "+COLUMN_TIES+"='"+0+"'");
        db.close();
    }*/

    public void deletePlayer(String player)
    {
        openWriteableDB();
        db.execSQL("DELETE FROM " + TABLE_NAME+ " WHERE "+COLUMN_NAME+"='"+player+"'");
        db.close();
    }
    public void updatePlayer(String previousPlayer, String player)
    {
        openWriteableDB();
        db.execSQL("UPDATE " + TABLE_NAME+ " SET "+ COLUMN_NAME + "='"+player+ "' WHERE "+COLUMN_NAME+"='"+previousPlayer+"'");
        db.close();
    }

    ArrayList<HashMap<String, String>> getPlayers(){
        ArrayList<HashMap<String, String>> data =
                new ArrayList<HashMap<String, String>>();
        openReadableDB();

        String sql = "SELECT " + COLUMN_NAME + "\n" +
                ", SUM(" + COLUMN_WINS + ")\n" +
                ", SUM(" + COLUMN_LOSSES + ")\n" +
                ", SUM(" + COLUMN_TIES + ")\n" +
                " FROM " + TABLE_NAME + "\n" +
                " GROUP BY "+ COLUMN_NAME + "\n" +
                " ORDER BY "+2+ " DESC";

        Cursor cursor = db.rawQuery(sql,null );
        while (cursor.moveToNext()) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put(COLUMN_NAME, cursor.getString(0));
            map.put(COLUMN_WINS, cursor.getString(1));
            map.put(COLUMN_LOSSES, cursor.getString(2));
            map.put(COLUMN_TIES, cursor.getString(3));
            data.add(map);
        }
        if (cursor != null)
            cursor.close();
        closeDB();

        return data;
    }
}
