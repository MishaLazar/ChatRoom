package com.hw.misha.chatroom;

/**
 * Created by Misha on 9/24/2016.
 */
public interface MessageStateServiceListener {

    void registerMessageListener(MessageStateServiceListener listener ,String roomID);

    void notifyMessageStateServiceListener();
}
