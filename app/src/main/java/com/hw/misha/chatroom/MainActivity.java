package com.hw.misha.chatroom;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
        Button btn = (Button)findViewById(R.id.ChatRoomTest);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ChatRoom.class);
                startActivity(intent);
            }
        });
    }

public void ServiceInit(){
    receiver = new InnerRoomsPoolReceiver();
    registerReceiver(receiver, new IntentFilter(RoomPoolService.BROADCAST_ACTION_POLL));
    Context context = MainActivity.this;
    Intent intentService = new Intent(context,RoomPoolService.class);
    //intentService.putExtra("DAL_FDB",fdb);
    startService(intentService);
}
public void printToasTest(){
    Toast.makeText(MainActivity.this,"in onReceive",Toast.LENGTH_SHORT).show();
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
