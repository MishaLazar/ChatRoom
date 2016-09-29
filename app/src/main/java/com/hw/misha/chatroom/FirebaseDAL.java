package com.hw.misha.chatroom;

/**
 * Created by Misha on 9/10/2016.
 */
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Misha on 9/3/2016.
 */
public class FireBaseDAL implements RoomStateListener, Serializable, MessageStateListener {

    static FireBaseDAL instance = null;
    FireBaseDBHandler fdbHandler;

    ArrayList<ActivityRoomStateListener> roomStateListeners;
    HashMap<String,Room> roomHashMap;
    HashMap<String,ChatMessage> messageMap;
    ArrayList<MessageStateServiceListener> messageStateListeners;

    Context context;

    public  FireBaseDAL(){
        this.roomHashMap = new HashMap<>();
        this.messageMap = new HashMap<>();
        //registerStateListener();
    }

    public static FireBaseDAL getFireBaseDALInstance(){
        if(instance == null){
            instance = new FireBaseDAL( );
        }
        return instance;
    }
  /*  public FireBaseDAL() {
        this.fdbHandler = new FireBaseDBHandler(context);
        this.roomHashMap = new HashMap<>();
        registerStateListener();
    }*/

    public FireBaseDBHandler getFdbHandler() {
        return fdbHandler;
    }

    public void setFdbHandler(FireBaseDBHandler fdbHandler) {
        this.fdbHandler = fdbHandler;
    }

    public void registerRoom(Context context, Room room){
        String roomID;
        try {

            roomID = fdbHandler.registerRoom(room);
            room.setRoom_ID(roomID);
        }catch (Exception exc){
            Toast.makeText(context,exc.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void updateRoomStatus(Context context, Room room, String roomStatus){
        //TODO: make the string resource
        if(roomStatus.equals("closeRoom")) {
            room.setRoom_isActive(false);
            room.setRoom_closeDate(UtilMethods.getTimeStamp());
        }
        else if(roomStatus.equals("openRoom")) {
            room.setRoom_isActive(true);
            room.setRoom_closeDate("");
        }
        try {
            fdbHandler.changeRoomStatus(room,room.getRoom_ID());
        }catch (Exception exc){
            Toast.makeText(context,exc.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public void registerUser(Context context,ChatRoomUser newUser){
        String userID;
        try {

            userID = fdbHandler.registerUser(newUser);
            newUser.setUser_ID(userID);
        }catch (Exception exc){
            Toast.makeText(context,exc.getMessage(),Toast.LENGTH_SHORT).show();
        }


    }
    public void sendMessage(ChatMessage message){

        if(message != null){
            //try {
                fdbHandler.registerChatRoomMessage(message.getRoomID(),message);
            //}catch (Exception exc){
               // Log.e("sendMessage(...)" , exc.getMessage().toString());
           // }

        }
    }

    public void registerStateListener() {

        //roomStateListeners.add(roomStateListener);
        //register listener to be notified on rooms when updated
        fdbHandler.readChatRoomsState(this);

    }
    public void unregisterStateListener(ActivityRoomStateListener roomStateListener) {

        //roomStateListeners.remove(roomStateListener);
        fdbHandler.removeReadChatRoomsState(this);

    }
    public void unregisterMessageListener(String roomID) {

        //roomStateListeners.remove(roomStateListener);
        fdbHandler.unregisterMessageListener(this,roomID);

    }

    @Override
    public void notifyListener(DataSnapshot snapshot) {
        //TODO need to be processed at the activity
        synchronized (this){
            for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                try{
                    /*HashMap<?,?> obj  = (HashMap<?,?>)postSnapshot.getValue();
                    if (obj.size()>1){*/
                        Room room = postSnapshot.getValue(Room.class);
                        roomHashMap.put(postSnapshot.getKey(),room);
                    /*}*/
                }catch (Exception exc){
                    Log.e("notifyListener","Incorrect type" + exc.getMessage());
                }

            }

        }
       /* for (Object listener : roomStateListeners) {
            ActivityRoomStateListener castListener = (ActivityRoomStateListener)listener;
            castListener.notifyListener();
        }*/

    }

    public void getRoomsState() {
        synchronized (this){
            //fdbHandler.readChatRoomsOnce();
            //fdbHandler.readChatRoomsState(this);
            try {
                fdbHandler.triggerRoomsOnce();
            }catch (Exception exc){
                Log.e("triggerRoomsOnce()", "getRooms: "+exc.getStackTrace().toString());
            }
        }


        //roomSnapshot = fdbHandler.getUpdatedRooms();
        //TODO try and catch
        //processRoomSnapshot(roomSnapshot);

    }
    private void processRoomSnapshot(DataSnapshot roomSnapshot) {
        for (DataSnapshot postSnapshot : roomSnapshot.getChildren()) {
            try {
                Room room = postSnapshot.getValue(Room.class);
                roomHashMap.put(postSnapshot.getKey(), room);
            } catch (Exception exc) {

                Log.e("processRoomSnapshot", exc.getStackTrace().toString());
            }
        }
    }
    @Override
    public void registerMessageListener(String roomID) {
        fdbHandler.registerMessageListener(this,roomID);
    }

    public void registerMessageListener(MessageStateServiceListener listener,String roomID) {
        //messageStateListeners.add(listener);
        fdbHandler.registerMessageListener(this,roomID);
    }

    @Override
    public void notifyMessageListener(DataSnapshot snapshot) {
        synchronized (this){
            for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                try {
                    ChatMessage message = postSnapshot.getValue(ChatMessage.class);
                    messageMap.put(postSnapshot.getKey(),message);
                    /*Intent intent = new Intent("com.hw.misha.chatroom.BROADCAST_ACTION_POLL");
                    context.sendBroadcast(intent);*/
                    /*for (Object listener : messageStateListeners) {
                        MessageStateServiceListener castListener = (MessageStateServiceListener)listener;
                        castListener.notifyMessageStateServiceListener();
                    }*/
                }catch (Exception exc){

                    Log.e("notifyMessage" , exc.getStackTrace().toString());
                }


            }
            Intent intent = new Intent("com.hw.misha.chatroom.BROADCAST_ACTION_POLL");
            context.sendBroadcast(intent);
        }
    }
    @Override
    public void notifyQueryMessageListener(DataSnapshot snapshot) {
        synchronized (this){
                try {
                    ChatMessage message = snapshot.getValue(ChatMessage.class);
                    messageMap.put(snapshot.getKey(),message);
                    /*Intent intent = new Intent("com.hw.misha.chatroom.BROADCAST_ACTION_POLL");
                    context.sendBroadcast(intent);*/
                    /*for (Object listener : messageStateListeners) {
                        MessageStateServiceListener castListener = (MessageStateServiceListener)listener;
                        castListener.notifyMessageStateServiceListener();
                    }*/
                }catch (Exception exc){

                    Log.e("notifyMessage" , exc.getStackTrace().toString());
                }
            Intent intent = new Intent("com.hw.misha.chatroom.BROADCAST_ACTION_POLL");
            context.sendBroadcast(intent);
        }
    }
    public HashMap<String, Room> getRoomHashMap() {
        /*DataSnapshot snapshot = fdbHandler.getUpdatedRooms();
        processRoomSnapshot(snapshot);*/
        return roomHashMap;
    }
    public HashMap<String, ChatMessage> getMessagesHashMap() {
        /*DataSnapshot snapshot = fdbHandler.getUpdatedRooms();
        processRoomSnapshot(snapshot);*/
        HashMap<String, ChatMessage> tempToRerutn = messageMap;

        return tempToRerutn;
    }
    public void clearMessageMap(){
        messageMap.clear();
    }
}