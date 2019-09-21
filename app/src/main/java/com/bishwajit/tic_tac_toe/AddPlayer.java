package com.bishwajit.tic_tac_toe;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;

public class AddPlayer extends MainActivity {

    EditText editText;
    EditText editTextDel;
    private ListView listView;
    Button btDel;
    Button btAdd;
    Button btnUpdate;
    String playerSelected_final;

    private ScoreBoardDBActivity db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);

        db = new ScoreBoardDBActivity(this);

        listView=(ListView)findViewById(R.id.list_view);
        editText=(EditText)findViewById(R.id.add_player);
        editTextDel=(EditText)findViewById(R.id.delete_update);

        btAdd=(Button)findViewById(R.id.btnAdd);
        btDel=(Button)findViewById(R.id.btnDeleteUpdate);
        btnUpdate=(Button)findViewById(R.id.btnUpdate);

        updateDisplay();
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newItem=editText.getText().toString();
                try {
                    db.addScoreBoard(newItem, 0, 0, 0);
                    editText.setText("");
                }
                catch(Exception e){
                    e.printStackTrace();
                }

                updateDisplay();
            }

        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text= listView.getItemAtPosition(i).toString();
                String playerSelected= text.split("player_name=")[1].replace("}","");
                playerSelected_final=playerSelected.split(",")[0];
                storeData.player1_name=playerSelected_final.toString();
                editTextDel.setText(storeData.player1_name.toString());
                Toast.makeText(AddPlayer.this, playerSelected_final + " selected to delete.", Toast.LENGTH_SHORT).show();
                //nextPageGo();
            }
        });

        btDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String player=editTextDel.getText().toString();
                try {
                    db.deletePlayer(player);
                    editTextDel.setText("");
                }
                catch(Exception e){
                    e.printStackTrace();
                }

                updateDisplay();
            }

        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String player=editTextDel.getText().toString();
                try {
                    db.updatePlayer(storeData.player1_name, player);
                    //db.deletePlayer(player);
                    editTextDel.setText("");
                }
                catch(Exception e){
                    e.printStackTrace();
                }

                updateDisplay();
            }

        });



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
        listView.setAdapter(adapter1);
    }

}
