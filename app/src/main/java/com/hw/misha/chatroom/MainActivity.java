package com.hw.misha.chatroom;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity {
    //Trank
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_main);
    }
public class InnerRoomsPoolReceiver extends BroadcastReceiver {

        Context context;

        public InnerRoomsPoolReceiver() {

        }

        public InnerRoomsPoolReceiver(Context context) {
            this.context = context;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            // if (intent.getAction().equals(RoomRefreshService.BROADCAST_ACTION_POLL)) {
            //String param = intent.getStringExtra(RoomRefreshService.EXTRA_PARAM_B);
            Toast.makeText(context,"in onReceive",Toast.LENGTH_SHORT).show();
            Log.e("innerReceiver", "MyReceiver: broadcast received");
            //}
        }
    }
}
