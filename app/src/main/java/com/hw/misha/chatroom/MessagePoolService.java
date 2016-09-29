package com.hw.misha.chatroom;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Messenger;

import android.util.Log;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.hw.misha.chatroom.Room;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
/**
 * Created by Misha on 9/10/2016.
 */
public class MessagePoolService extends Service implements Runnable , MessageStateServiceListener/*TimerTask*/ {
    public static final String BROADCAST_ACTION_POLL = "com.hw.misha.chatroom.BROADCAST_ACTION_POLL";
    public static final String BROADCAST_ACTION_POLL_ROOMS = "com.hw.misha.chatroom.BROADCAST_ACTION_POLL_ROOMS";
    // constant
    //TODO define the correct time interval
    public static final long ROOM_UPDATE_INTERVAL = 20 * 1000; // 30 seconds

    Messenger messageHandler;
    HashMap<String, Room> roomHashMap;
    // run on another Thread to avoid crash
    Handler mHandler = new Handler();
    // timer handling
    Timer mTimer = null;

    FireBaseDAL fdb;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle extras = intent.getExtras();


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        //
        Log.d("MessagePoolService","in onStartCommand");
        fdb = FireBaseDAL.getFireBaseDALInstance();
        Log.d("MessagePoolService","in onStartCommand after FireBaseDAL instance");
        run();
        //registerMessageListener(this,"-KSMgyqLOUtScTIm9zDZ");
        Log.d("MessagePoolService","in onStartCommand after run()");
/*
        // cancel if already existed
        if (mTimer != null) {
            mTimer.cancel();
        } else {
            // recreate new
            mTimer = new Timer();
        }
        // schedule task
        mTimer.scheduleAtFixedRate(new MessageRefreshTask(), 10, ROOM_UPDATE_INTERVAL);*/
    }

    @Override
    public void run() {
        //registerMessageListener(this,"-KSMgyqLOUtScTIm9zDZ");
        // run on another thread
        mHandler.post(new Runnable() {

            @Override
            public void run() {
                // display toast
                try {
                    Log.d("MessageRefreshTask" , "run() before register");
                    Log.d("MessageRefreshTask" , "run() after register, before wait()");
                    wait();
                    Log.d("MessageRefreshTask" , "run() after wait()");
                }catch (Exception exc){
                    Log.e("MessageRefreshTask" , "run() " + exc.getMessage());
                    Log.d("MessageRefreshTask" , "run() " + exc.getMessage());
                }
                Log.d("MessageRefreshTask" , "after wait() ");
                broadcastActionBaz();


            }

        });

    }

   /* public void registerMessageListener() {
        //roomid = -KSMgyqLOUtScTIm9zDZ
        fdb.registerMessageListener("-KSMgyqLOUtScTIm9zDZ");

    }*/


    /*public void notifyMessageListener() {
        synchronized (this){
            this.notify();
        }
    }*/


    public HashMap<String,Room> getRoomsHashMap(){
        return roomHashMap;
    }

    // called to send data to Activity
    public void broadcastActionBaz() {

        Intent intent = new Intent(BROADCAST_ACTION_POLL);
        //intent.putExtra(EXTRA_PARAM_B, "test test");
        sendBroadcast(intent);

    }

    @Override
    public void registerMessageListener(MessageStateServiceListener listener, String roomID) {
        fdb.registerMessageListener(this,roomID);
    }

    @Override
    public void notifyMessageStateServiceListener() {
        synchronized (this){
            this.notify();
        }
    }
   /* class MessageRefreshTask extends TimerTask{

            @Override
            public void run() {
                registerMessageListener();
                // run on another thread
                mHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        // display toast
                        try {
                            wait();
                        }catch (Exception exc){
                            Log.e("MessageRefreshTask" , "run() " + exc.getMessage());
                            Log.d("MessageRefreshTask" , "run() " + exc.getMessage());
                        }
                        Log.d("MessageRefreshTask" , "after wait() ");
                        broadcastActionBaz();


                    }

                });
            }


            public void registerMessageListener() {
                //roomid = -KSMgyqLOUtScTIm9zDZ
                fdb.registerMessageListener("-KSMgyqLOUtScTIm9zDZ");
            }


            public void notifyMessageListener() {
                synchronized (this){
                    this.notify();
                }
            }

        }
        public HashMap<String,Room> getRoomsHashMap(){
            return roomHashMap;
        }

        // called to send data to Activity
        public void broadcastActionBaz() {

            Intent intent = new Intent(BROADCAST_ACTION_POLL);
            //intent.putExtra(EXTRA_PARAM_B, "test test");
            sendBroadcast(intent);

        }*/
}

