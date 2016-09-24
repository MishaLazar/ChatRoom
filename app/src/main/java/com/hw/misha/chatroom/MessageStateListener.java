package com.hw.misha.chatroom;

import com.firebase.client.DataSnapshot;

/**
 * Created by Misha on 9/19/2016.
 */
public interface MessageStateListener {

    void registerMessageListener(String roomID);

    void notifyMessageListener(DataSnapshot snapshot);
}
