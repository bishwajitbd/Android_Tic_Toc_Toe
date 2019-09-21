package com.bishwajit.tic_tac_toe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class view_scoreboard extends MainActivity {
    EditText editText;
    private ListView listView;

    private ScoreBoardDBActivity db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoreboard_view);

        db = new ScoreBoardDBActivity(this);

        listView=(ListView)findViewById(R.id.list_view);
        //db.deleteRow();
        updateDisplay();

    }
    private void updateDisplay(){
        // create a List of Map<String, ?> objects
        ArrayList<HashMap<String, String>> data = db.getPlayers();

        // create the resource, from, and to variables
        int resource = R.layout.listview_item_scorboard;
        String[] from = {"player_name", "wins", "losses", "ties"};
        int[] to = {R.id.nameTextView, R.id.winsTextView, R.id.lossesTextView, R.id.tiesTextView};

        // create and set the adapter
        SimpleAdapter adapter =
                new SimpleAdapter(this, data, resource, from, to);
        listView.setAdapter(adapter);

    }
}
