package com.hw.misha.chatroom;
/**
 * Created by Misha on 9/10/2016.
 */
import android.content.Context;
import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Created by Misha on 9/3/2016.
 */
public class FireBaseDBHandler implements Serializable{

    static  FireBaseDBHandler instance = null;
    ArrayList<RoomStateListener> roomsStatelisteners;
    ArrayList<MessageStateListener> messageStatelisteners;
    Firebase fire_db ;//= new Firebase("https://chatroomapp-6dd82.firebaseio.com/");


    DataSnapshot RoomsSnapshot = null;
    //Firebase triggerRoomsOnceref;

    ValueEventListener roomsvalueListener=null;
    String acceptKey = "-KSOJKnUb6lxvcxWC0hU";


    public FireBaseDBHandler(Context context) {
        Firebase.setAndroidContext(context);
        fire_db = new Firebase("https://chatroomapp-6dd82.firebaseio.com/");
        roomsStatelisteners  = new ArrayList<>();
        messageStatelisteners = new ArrayList<>();
    }
    public static FireBaseDBHandler getFireBaseDBHandlerInstance(Context context){
        if (instance == null ){
            instance = new FireBaseDBHandler(context);
        }
        return instance;
    }

    //Write functions

    public String registerRoom(Room room) throws Exception{
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

    public void registerChatRoomMessage(String roomID,ChatMessage message) {
        Firebase roomsNodeRef = fire_db.child("ChatRoomNode");
        Firebase roomNodeRef = roomsNodeRef.child(roomID);
        Firebase messageNodeRef = roomNodeRef.child("ChatMessage");
        Firebase newMessageNode = messageNodeRef.push();
        if (roomID != null) {
            //try {
            String postId = newMessageNode.getKey();
            message.setId(postId);
            newMessageNode.setValue(message, new Firebase.CompletionListener() {
                @Override
                public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                    if (firebaseError != null) {
                        System.out.println("Data could not be saved. " + firebaseError.getMessage());
                    } else {
                        System.out.println("Data saved successfully.");
                    }
                }
            });

           /* }catch (Exception exc){
                Log.e("registerChatRoomMessage", exc.getMessage());
                //throw new Exception("Something failed.", new Throwable(String.valueOf(exc.getMessage())));
            }*/
        }
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

    //update functions
    public void changeRoomStatus(Room room , String roomID) throws Exception{
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
    public void removeReadChatRoomsState(RoomStateListener listener){
        roomsStatelisteners.remove(this);
    }
    public void readChatRoomsState(RoomStateListener listener){
        //register new room state listener
        roomsStatelisteners.add(listener);

        final Firebase ref = new Firebase("https://chatroomapp-6dd82.firebaseio.com/ChatRoomNode");
        // Attach an listener to read rooms state reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(roomsStatelisteners.size()>0){
                    notifyListeners(roomsStatelisteners,snapshot,"RoomStateListener");
                    RoomsSnapshot = snapshot;
                    ref.removeEventListener(this);
                }
               else{
                    RoomsSnapshot = snapshot;
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                //TODO need to take care of this case
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }
    public void readChatRoomsOnce(){
        final Firebase ref = new Firebase("https://chatroomapp-6dd82.firebaseio.com/ChatRoomNode");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // do some stuff once
                Log.d("readChatRoomsOnce" , "before onDataChange ");
                RoomsSnapshot = snapshot;
                Log.d("readChatRoomsOnce" , "after onDataChange ");
                ref.removeEventListener(this);
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }
    public void triggerRoomsOnce() throws Exception{
        //TODO Add Listener to notify on complete
        Firebase roomsNodeRef = fire_db.child("ChatRoomNode");
        Firebase newNodeRef = null;
        if(acceptKey == null){
            newNodeRef = roomsNodeRef.push();
            acceptKey = newNodeRef.getKey();
        }else{
            newNodeRef = roomsNodeRef.child(acceptKey);
        }
       // Firebase newNodeRef = roomsNodeRef.push();
        try {
            newNodeRef.setValue("accept",new Firebase.CompletionListener() {
                @Override
                public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                    if (firebaseError != null) {
                        Log.d("triggerRoomsOnce","onComplete Data could not be saved. " + firebaseError.getMessage());
                    } else {
                        Log.d("triggerRoomsOnce"," onComplete Data saved successfully.");
                    }
                }
            });

        }catch (Exception exc){
            throw new Exception("Something failed.", new Throwable(String.valueOf(Exception.class)));
        }


      /*  triggerRoomsOnceRef.setValue("accepted");*//*, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    triggerRoomsOnceRef.removeEventListener();
                    System.out.println("Data could not be saved. " + firebaseError.getMessage());
                } else {
                    System.out.println("Data saved successfully.");
                }
            }
        });*//*
*/

    }

    public void notifyListeners(ArrayList listeners , DataSnapshot snapshot , String typeID){
        if (listeners == null || snapshot == null){
           Log.e("notifyListeners error" , "Error");
        }
        else if (typeID.equals("RoomStateListener") ){
            Log.d("RoomStateListener" , "in notification");
            for (Object listener : listeners) {
                RoomStateListener castListener = (RoomStateListener)listener;
                castListener.notifyListener(snapshot);
            }
        }
        else if (typeID.equals("MessageStateListener")){
            Log.d("MessageStateListener" , "in notification");
            for (Object listener : listeners) {
                MessageStateListener castListener = (MessageStateListener)listener;
                castListener.notifyMessageListener(snapshot);
            }
        }
    }

    public void notifyMessageListeners(ArrayList<?> listeners , DataSnapshot snapshot){
        if (listeners == null || snapshot == null){
            System.out.println("notifyListeners error");
        }
        else if (listeners instanceof RoomStateListener){
            for (Object listener : listeners) {
                MessageStateListener castListener = (MessageStateListener)listener;
                castListener.notifyMessageListener(snapshot);
            }
        }
    }
    public void registerMessageListener(MessageStateListener listener,String roomID){
        messageStatelisteners.add(listener);
        Log.d("readMessageState","after Register Listener");
        readMessageState(roomID);
    }

    private void readMessageState(String roomID){
        //register new room state listener


        Firebase ref = new Firebase("https://chatroomapp-6dd82.firebaseio.com/ChatRoomNode"
                                    +"/"+roomID+"/"+"ChatMessage");
        // Attach an listener to read rooms state reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.d("readMessageState","onDataChange");
                notifyListeners(messageStatelisteners,snapshot,"MessageStateListener");
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                //TODO need to take care of this case
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    public DataSnapshot getUpdatedRooms() {
        return RoomsSnapshot;
    }
}