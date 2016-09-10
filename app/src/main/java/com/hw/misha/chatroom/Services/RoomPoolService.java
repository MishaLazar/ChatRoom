package com.hw.misha.chatroom.Services;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import android.util.Log;
import android.widget.Toast;

import com.hw.misha.chatroom.Entities.ChatRoom;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
/**
 * Created by Misha on 9/10/2016.
 */
public class RoomPoolService {
    /**
     * Created by Misha on 9/7/2016.
     */
    public class RoomRefreshService extends Service /*TimerTask*/ {
        private static final String DB_REF = "db_obj";
        private static final String EXTRA_PARAM_A = "PARAM_A";

        public static final String BROADCAST_ACTION_POLL = "com.example.misha.myfirebasetest.BROADCAST_ACTION_POLL";
        public static final String EXTRA_PARAM_B = "getRoomsUpdate";
        // constant
        //TODO define the correct time interval 
        public static final long ROOM_UPDATE_INTERVAL = 20 * 1000; // 30 seconds

        Messenger messageHandler;
        HashMap<String, ChatRoom> roomHashMap;
        // run on another Thread to avoid crash
        Handler mHandler = new Handler();
        // timer handling
        Timer mTimer = null;

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            Bundle extras = intent.getExtras();
            messageHandler = (Messenger) extras.get("MESSENGER");
            Toast.makeText(getBaseContext(),"in onStartCommand",Toast.LENGTH_SHORT).show();
            return super.onStartCommand(intent, flags, startId);
        }


        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public void onCreate() {


            // cancel if already existed
            if (mTimer != null) {
                mTimer.cancel();
            } else {
                // recreate new
                mTimer = new Timer();
            }
            // schedule task
            mTimer.scheduleAtFixedRate(new RoomsRefreshTask(), 10, ROOM_UPDATE_INTERVAL);
        }

        class RoomsRefreshTask extends TimerTask {

            @Override
            public void run() {
                // run on another thread
                mHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        // display toast
                        Toast.makeText(getBaseContext(),"broadcastActionBaz()",Toast.LENGTH_SHORT).show();
                        Log.e("sohail","broadcastActionBaz()");
                        broadcastActionBaz();


                    }

                });
            }

        }
        public HashMap<String,ChatRoom> getRoomsHashMap(){
            return roomHashMap;
        }

        // called to send data to Activity
        public void broadcastActionBaz() {

            Intent intent = new Intent(BROADCAST_ACTION_POLL);
            intent.putExtra(EXTRA_PARAM_B, "test test");
            sendBroadcast(intent);

        }
    }
}
