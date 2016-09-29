package com.hw.misha.chatroom;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.client.Firebase;


public class MainActivity extends AppCompatActivity {

    FireBaseDAL fdb;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userID = "-KQqc02cWiHQWkaktOUp"; // temp user for test
        //ServiceInit();
        FireBaseDBHandler dbHandler = FireBaseDBHandler.getFireBaseDBHandlerInstance(MainActivity.this);
        fdb = FireBaseDAL.getFireBaseDALInstance();
        fdb.setFdbHandler(dbHandler);
        /*fdb.registerStateListener();
        fdb.getRoomsState();*/
        //delayBar();


        Button btn = (Button) findViewById(R.id.ChatRoomTest);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {*/
                        Intent intent = new Intent(MainActivity.this, ChatRoomGridSelector.class);
                        intent.putExtra("userID",userID);
                        startActivity(intent);
//                    }
//                }, 3000);

            }
        });
        Button btn2 = (Button) findViewById(R.id.addChatRoomTest);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChatRoom.class);
                intent.putExtra("userID",userID);
                startActivity(intent);
            }
        });
        Button btn3 = (Button) findViewById(R.id.create_room);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateChatRoomActivity.class);
                intent.putExtra("userID",userID);
                startActivity(intent);
            }
        });
    }

    }
