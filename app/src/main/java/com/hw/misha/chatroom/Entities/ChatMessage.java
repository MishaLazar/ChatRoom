package com.hw.misha.chatroom.Entities;

/**
 * Created by Misha on 9/11/2016.
 */
public class ChatMessage {
    public boolean left;
    public String message;

    public ChatMessage(boolean left, String message) {
        super();
        this.left = left;
        this.message = message;
    }
}
