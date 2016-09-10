package com.hw.misha.chatroom.Controllers;

/**
 * Created by Misha on 9/10/2016.
 */
import android.content.Context;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.hw.misha.chatroom.DB.FireBaseDBHandler;
import com.hw.misha.chatroom.Entities.ChatRoom;
import com.hw.misha.chatroom.Entities.ChatRoomUser;
import com.hw.misha.chatroom.Interfaces.RoomStateListener;
import com.hw.misha.chatroom.Utilities.UtilMethods;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Misha on 9/3/2016.
 */
public class FirebaseDAL implements RoomStateListener, Serializable {

    FireBaseDBHandler fdbHandler;

    HashMap<String,ChatRoom> roomHashMap;

    public FirebaseDAL() {
        this.fdbHandler = new FireBaseDBHandler();
        this.roomHashMap = new HashMap<>();
    }

    public void registerRoom(Context context,ChatRoom room){
        String roomID;
        try {

            roomID = fdbHandler.registerRoom(room);
            room.setRoom_ID(roomID);
        }catch (Exception exc){
            Toast.makeText(context,exc.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }
    public void updateRoomStatus(Context context,ChatRoom room,String roomStatus){
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

    @Override
    public void registerStateListener() {
        //register listener to be notified on rooms when updated
        fdbHandler.readChatRoomsState(this);

    }

    @Override
    public void notifyListener(DataSnapshot snapshot) {

        synchronized (this){
            for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                ChatRoom room = postSnapshot.getValue(ChatRoom.class);
                roomHashMap.put(room.getRoom_ID(),room);
            }
        }
    }

    @Override
    public HashMap<?, ?> getrooms() {
        synchronized (this){
            return roomHashMap;
        }
    }
}