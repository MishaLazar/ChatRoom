package com.hw.misha.chatroom.DB;
/**
 * Created by Misha on 9/10/2016.
 */
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.hw.misha.chatroom.Entities.ChatRoom;
import com.hw.misha.chatroom.Entities.ChatRoomUser;
import com.hw.misha.chatroom.Interfaces.RoomStateListener;

import java.util.ArrayList;


/**
 * Created by Misha on 9/3/2016.
 */
public class FireBaseDBHandler {

    ArrayList<RoomStateListener> roomsStatelisteners;
    Firebase fire_db = new Firebase("https://chatroomapp-6dd82.firebaseio.com/");


    public FireBaseDBHandler() {
        roomsStatelisteners  = new ArrayList<>();
    }

    //Write functions

    public String registerRoom(ChatRoom room) throws Exception{
        //TODO Add Listener to notify on complete
        Firebase roomsNodeRef = fire_db.child("ChatRoomNode");
        Firebase newNodeRef = roomsNodeRef.push();
        if (room != null)
            try {
                newNodeRef.setValue(room,new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                        if (firebaseError != null) {
                            System.out.println("Data could not be saved. " + firebaseError.getMessage());
                        } else {
                            System.out.println("Data saved successfully.");
                        }
                    }
                });
                String postId = newNodeRef.getKey();
                return postId;
            }catch (Exception exc){
                throw new Exception("Something failed.", new Throwable(String.valueOf(Exception.class)));
            }
        return  null;
    }
    public String registerUser(ChatRoomUser newUser) throws Exception{
        //TODO Add Listener to notify on complete
        Firebase roomsNodeRef = fire_db.child("Users");
        Firebase newNodeRef = roomsNodeRef.push();
        if (newUser != null)
            try {
                newNodeRef.setValue(newUser,new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                        if (firebaseError != null) {
                            System.out.println("Data could not be saved. " + firebaseError.getMessage());
                        } else {
                            System.out.println("Data saved successfully.");
                        }
                    }
                });
                String postId = newNodeRef.getKey();
                return postId;
            }catch (Exception exc){
                throw new Exception("Something failed.", new Throwable(String.valueOf(Exception.class)));
            }
        return  null;
    }


    ///override function

    /*public void changeRoomStatus(String roomID,boolean status){
        Firebase roomsNodeRef = fire_db.child("ChatRoomNode");
        Firebase room_isActiveRef = roomsNodeRef.child("https://chatroomapp-6dd82.firebaseio.com/ChatRoomNode/-KQlWrP9K27jxT0exvYk/room_isActive");
        room_isActiveRef.updateChildren(status);
    }*/
    //update functions
    public void changeRoomStatus(ChatRoom room , String roomID) throws Exception{
        //TODO Add Listener to notify on complete
        Firebase roomsNodeRef = fire_db.child("ChatRoomNode");
        Firebase roomRef = roomsNodeRef.child(roomID);
        if (room != null)
            try {
                roomRef.setValue(room, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                        if (firebaseError != null) {
                            System.out.println("Data could not be saved. " + firebaseError.getMessage());
                        } else {
                            System.out.println("Data saved successfully.");
                        }
                    }
                });
            }catch (Exception exc){
                throw new Exception("Something failed.", new Throwable(String.valueOf(Exception.class)));
            }
    }
    //ReadFunctions
    public void loadUserHistory(String userName,String Password) {
        //reference to users Node
        Firebase usersNodeRef = new Firebase("https://myfirebasetest-79096.firebaseio.com/users");

        fire_db.orderByValue().limitToLast(4).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {

                //  Toast.makeText(MainActivity.this
                //       ,"The " + snapshot.getKey().toString() + " dinosaur's score is " + snapshot.getValue().toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        //




    }
    public void readChatRoomsState(RoomStateListener listener){
        //register new room state listener
        roomsStatelisteners.add(listener);

        Firebase ref = new Firebase("https://chatroomapp-6dd82.firebaseio.com/ChatRoomNode");
        // Attach an listener to read rooms state reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                notifyListeners(roomsStatelisteners,snapshot);
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                //TODO need to take care of this case
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    public void notifyListeners(ArrayList<?> listeners , DataSnapshot snapshot){
        if (listeners == null || snapshot == null){
            System.out.println("notifyListeners error");
        }
        else if (listeners instanceof RoomStateListener){
            for (Object listener : listeners) {
                RoomStateListener castListener = (RoomStateListener)listener;
                castListener.notifyListener(snapshot);
            }
        }
    }
}