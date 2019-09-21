package com.bishwajit.tic_tac_toe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class SelectPlayer2 extends MainActivity {

    ListView list;
    TextView player_name;
    private ScoreBoardDBActivity db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_player2);

        db = new ScoreBoardDBActivity(this);

        player_name=(TextView) findViewById(R.id.txt_player2);


        list=(ListView)findViewById(R.id.list_view);

        updateDisplay();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text= list.getItemAtPosition(i).toString();
                String playerSelected= text.split("player_name=")[1].replace("}","");
                String playerSelected_final=playerSelected.split(",")[0];
                storeData.player2_name=playerSelected_final.toString();
                player_name.setText(storeData.player2_name.toString());

                if(storeData.player1_name.equals("None1")  && storeData.player2_name.equals("None2")  ) {
                    storeData.checkPlayer=0;

                }
                Toast.makeText(SelectPlayer2.this, playerSelected_final + " selected as as Player 2.", Toast.LENGTH_SHORT).show();
                nextPageGo();
            }
        });
    }
    private void nextPageGo(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void updateDisplay(){
        // create a List of Map<String, ?> objects
        ArrayList<HashMap<String, String>> data = db.getPlayers();

        // create the resource, from, and to variables
        int resource = R.layout.listview_item;
        String[] from = {"player_name"};
        int[] to = {R.id.nameTextView};

        // create and set the adapter
        SimpleAdapter adapter1 =
                new SimpleAdapter(this, data, resource, from, to);
        list.setAdapter(adapter1);
    }

}
