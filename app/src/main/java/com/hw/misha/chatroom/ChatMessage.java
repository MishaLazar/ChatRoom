package com.hw.misha.chatroom;

import java.util.Date;

/**
 * Created by Misha on 9/11/2016.
 */
public class ChatMessage {

    String id;
    boolean isMe;
    String message;
    String userId;
    String dateTime;
    String roomID;

    public ChatMessage() {
    }

    public ChatMessage(boolean isMe, String message,String roomID) {
        this.roomID = roomID;
        this.isMe = isMe;
        this.message = message;
        this.dateTime = UtilMethods.getDateTimeSimple();
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public boolean getIsme() {
        return isMe;
    }
    public void setMe(boolean isMe) {
        this.isMe = isMe;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDate() {
        return dateTime;
    }

    public void setDate(String dateTime) {
        this.dateTime = dateTime;
    }

}
