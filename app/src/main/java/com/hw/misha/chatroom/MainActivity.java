package com.hw.misha.chatroom;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.client.Firebase;


public class MainActivity extends AppCompatActivity {

    //BroadcastReceiver
    InnerRoomsPoolReceiver receiver;

    FireBaseDAL fdb;

    //Service
    RoomPoolService roomPoolService;
    Firebase fire_db;



    //Trank
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        //ServiceInit();
        FireBaseDBHandler dbHandler = FireBaseDBHandler.getFireBaseDBHandlerInstance(MainActivity.this);
        fdb = FireBaseDAL.getFireBaseDALInstance();
        fdb.setFdbHandler(dbHandler);
        fdb.registerStateListener();
        fdb.getRoomsState();
        //delayBar();

        Button btn = (Button) findViewById(R.id.ChatRoomTest);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MainActivity.this, ChatRoomGridSelector.class);
                        startActivity(intent);
                    }
                }, 3000);

            }
        });
        Button btn2 = (Button) findViewById(R.id.addChatRoomTest);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fdb.registerRoom(MainActivity.this, new Room("Name", "me"));
            }
        });
        Button btn3 = (Button) findViewById(R.id.create_room);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateChatRoomActivity.class);
                startActivity(intent);
            }
        });
    }

    public void ServiceInit() {
        receiver = new InnerRoomsPoolReceiver();
        registerReceiver(receiver, new IntentFilter(RoomPoolService.BROADCAST_ACTION_POLL));
        Context context = MainActivity.this;
        Intent intentService = new Intent(context, RoomPoolService.class);
        //intentService.putExtra("DAL_FDB",fdb);
        startService(intentService);
    }

    public void printToasTest() {
        Toast.makeText(MainActivity.this, "in onReceive", Toast.LENGTH_SHORT).show();
    }

    public class InnerRoomsPoolReceiver extends BroadcastReceiver {


        public InnerRoomsPoolReceiver() {

        }

        @Override
        public void onReceive(Context context, Intent intent) {
            // if (intent.getAction().equals(RoomRefreshService.BROADCAST_ACTION_POLL)) {
            //String param = intent.getStringExtra(RoomRefreshService.EXTRA_PARAM_B);


            Log.e("innerReceiver", "MyReceiver: broadcast received");
            printToasTest();
            //}
        }
    }
    }
