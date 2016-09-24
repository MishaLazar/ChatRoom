package com.hw.misha.chatroom;

/**
 * Created by Misha on 9/10/2016.
 */
import com.firebase.client.DataSnapshot;

import java.util.HashMap;

public interface RoomStateListener {

    void registerStateListener(ActivityRoomStateListener listener);

    void notifyListener(DataSnapshot snapshot);

    void getRoomsState();

}