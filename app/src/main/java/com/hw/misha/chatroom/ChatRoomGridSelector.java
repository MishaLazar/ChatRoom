package com.hw.misha.chatroom;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class ChatRoomGridSelector extends AppCompatActivity implements ActivityRoomStateListener {

    GridView gridView;
    CustomGridViewAdapter customGridAdapter;
    ArrayList<Room> gridArray = new ArrayList<Room>();

    //DB
    FireBaseDAL fdb;

    BroadcastReceiver receiver;

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room_grid_selector);

        receiver = new InnerReceiver(ChatRoomGridSelector.this);
        registerReceiver(receiver, new IntentFilter(MessagePoolService.BROADCAST_ACTION_POLL_ROOMS));
        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        //init data dal object
        initDalObject();
        //get grid data
        //getGridData();

        getGridData();
       // initGrid();

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        gridView = (GridView) findViewById(R.id.gridView1);
        customGridAdapter = new CustomGridViewAdapter(this, R.layout.row_grid, gridArray);
        gridView.setAdapter(customGridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
                Toast.makeText(getApplicationContext(),gridArray.get(position).getRoom_ID(), Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    private void getGridData(){
    //fill the gridArray

        //registerRoomStateListener();

        fdb.getRoomsState();

        //unregisterRoomStateListener();
        //gridArray = new ArrayList<>();
        //gridArray.add(new Room("temp","owner"));
        //gridArray =  new ArrayList<>(tempHash.values());
    }



    private void initDalObject(){
        //for data

        fdb = FireBaseDAL.getFireBaseDALInstance();
        fdb.setContext(ChatRoomGridSelector.this);

    }

    @Override
    public void registerRoomStateListener() {
        fdb.registerStateListener();
    }

    @Override
    public void unregisterRoomStateListener() {
        fdb.unregisterStateListener(this);
    }

    @Override
    public void getRooms() {
        gridArray =  new ArrayList<>(fdb.getRoomHashMap().values());    }

    @Override
    public void notifyListener() {
        getRooms();
    }

    class InnerReceiver extends BroadcastReceiver {

        Context context;

        public InnerReceiver() {

        }

        public InnerReceiver(Context context) {
            this.context = context;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            // if (intent.getAction().equals(RoomRefreshService.BROADCAST_ACTION_POLL)) {
            //String param = intent.getStringExtra(RoomRefreshService.EXTRA_PARAM_B);
            getRooms();
            //Toast.makeText(context,"in onReceive",Toast.LENGTH_SHORT).show();
            Log.e("innerReceiver", "MyReceiver: broadcast received , rooms");
            //}
            initGrid();
        }
    }



    public void initGrid(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // here we may create new room/event
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        gridView = (GridView) findViewById(R.id.gridView1);
        customGridAdapter = new CustomGridViewAdapter(this, R.layout.row_grid, gridArray);
        gridView.setAdapter(customGridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
                /*Toast.makeText(getApplicationContext(),gridArray.get(position).getRoom_ID(), Toast.LENGTH_SHORT).show();*/
                Intent intent = new Intent(ChatRoomGridSelector.this, ChatRoom.class);
                intent.putExtra("roomID",gridArray.get(position).getRoom_ID());
                intent.putExtra("userID",userID);
                startActivity(intent);
            }
        });
    }

}

