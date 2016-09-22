package com.hw.misha.chatroom;

/**
 * Created by Misha on 9/10/2016.
 */
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Misha on 9/3/2016.
 */
public class FireBaseDAL implements RoomStateListener, Serializable, MessageStateListener {

    static FireBaseDAL instance = null;
    FireBaseDBHandler fdbHandler;

    HashMap<String,Room> roomHashMap;
    ArrayList<ChatMessage> MessageArray;

    public  FireBaseDAL(){
        this.roomHashMap = new HashMap<>();
        this.MessageArray = new ArrayList<>();
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

    @Override
    public void registerStateListener() {
        //register listener to be notified on rooms when updated
        fdbHandler.readChatRoomsState(this);

    }

    @Override
    public void notifyListener(DataSnapshot snapshot) {

        synchronized (this){
            for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                Room room = postSnapshot.getValue(Room.class);
                roomHashMap.put(room.getRoom_ID(),room);
            }
        }
    }

    public HashMap<?, ?> getRooms() {
        synchronized (this){
            return roomHashMap;
        }
    }

    @Override
    public void registerMessageListener(String roomID) {
        fdbHandler.registerMessageListener(this,roomID);
    }

    @Override
    public void notifyMessageListener(DataSnapshot snapshot) {
        synchronized (this){
            for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                try {
                    ChatMessage message = postSnapshot.getValue(ChatMessage.class);
                    MessageArray.add(message);
                }catch (Exception exc){

                    Log.e("notifyMessage" , exc.getStackTrace().toString());
                }


            }
        }
    }
}