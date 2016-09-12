package com.hw.misha.chatroom;

/**
 * Created by Misha on 9/10/2016.
 */


import java.util.ArrayList;

public class Room {

    String room_DisplayName;
    String room_Owner;

    //unique  id
    String room_ID = "";

    //tags for filter
    ArrayList<String> room_Tags = new ArrayList<String>(){{add("DefaultValue");}};

    //status failed's
    boolean room_isActive = true;
    String room_createDate = "" ;
    String room_closeDate = "";

    //default constructor in firebase use
    public Room() {
    }

    public Room(String room_DisplayName, String room_Owner) {
        this.room_DisplayName = room_DisplayName;
        this.room_Owner = room_Owner;
    }

    public void setRoom_ID(String room_ID) {
        this.room_ID = room_ID;
    }

    public void setRoom_Tags(ArrayList<String> room_Tags) {
        this.room_Tags = room_Tags;
    }

    public void setRoom_isActive(Boolean room_isActive) {
        this.room_isActive = room_isActive;
    }

    public void setRoom_createDate(String room_createDate) {
        this.room_createDate = room_createDate;
    }

    public void setRoom_closeDate(String room_closeDate) {
        this.room_closeDate = room_closeDate;
    }

    public String getRoom_DisplayName() {
        return room_DisplayName;
    }

    public String getRoom_Owner() {
        return room_Owner;
    }

    public String getRoom_ID() {
        return room_ID;
    }

    public ArrayList<String> getRoom_Tags() {
        return room_Tags;
    }

    public Boolean getRoom_isActive() {
        return room_isActive;
    }

    public String getRoom_createDate() {
        return room_createDate;
    }
}
