package com.hw.misha.chatroom;

import java.util.HashMap;

/**
 * Created by Misha on 9/23/2016.
 */
public interface ActivityRoomStateListener {

    void registerRoomStateListener();
    void unregisterRoomStateListener();

    void getRooms();

    void notifyListener();

}
