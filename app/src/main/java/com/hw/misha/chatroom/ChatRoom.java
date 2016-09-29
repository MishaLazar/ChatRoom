package com.hw.misha.chatroom;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.DataSetObserver;
import android.os.Bundle;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatRoom extends Activity {

    ChatArrayAdapter chatArrayAdapter;
    ListView listView;
    EditText chatText;
    Button btn_Send;
    boolean side = false;

    FireBaseDAL fdb;
    boolean first = false;
    String roomID;
    InnerReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        //register Reciver for Broadcast From service
        receiver = new InnerReceiver(ChatRoom.this);
        registerReceiver(receiver, new IntentFilter(MessagePoolService.BROADCAST_ACTION_POLL));
        Intent intent = getIntent();
        //get\create singleton db reference
        fdb = FireBaseDAL.getFireBaseDALInstance();
        fdb.setContext(ChatRoom.this);
        initViews();
        //startService();
        Log.d("ChatRoomActivity","after initViews()");

        intAdapter();
        Log.d("ChatRoomActivity","after intAdapter()");
        roomID = "-KSQlA6SdGfaLTFDLfFs";

        //TODO make it general
        registerForMessage(roomID);
        /*chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.list_item_chat_message);
        listView.setAdapter(chatArrayAdapter);



        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(chatArrayAdapter);
        //auto scroll on data change
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });*/

    }
    @Override
    public void onResume() {
        super.onResume();
        //startService();

    }
    @Override
    public void onPause() {
        super.onPause();
        unregisterForMessage(roomID);
        //stopService();
    }
    @Override
    public void onBackPressed() {
        unregisterForMessage(roomID);
        //stopService();
    }

    private void stopService() {
    }

    public void startService(){
        Context context = ChatRoom.this;
        //filter=new IntentFilter("sohail.aziz");
        Intent intentService = new Intent(context,MessagePoolService.class);
        startService(intentService);
    }
    public void intAdapter(){
        chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.list_item_chat_message);
        listView.setAdapter(chatArrayAdapter);



        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(chatArrayAdapter);
        //auto scroll on data change
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });

    }
    public void registerForMessage(String roomID){
        //TODO  need to make it generic
        fdb.registerMessageListener(roomID);
    }
    public void unregisterForMessage(String roomID){
        //TODO  need to make it generic
        fdb.unregisterMessageListener(roomID);
    }

    public void initViews(){
        //list view for messages
        listView = (ListView)findViewById(R.id.room_msg_view);
        //send message button
        btn_Send = (Button)findViewById(R.id.room_btn_send);
        try{
            btn_Send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendChatMessage();
                }
            });
        }catch (NullPointerException exc){
            String str  = exc.getMessage();
            Log.e(str,null);

        }
        chatText = (EditText)findViewById(R.id.msg);
        chatText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return sendChatMessage();
                }
                return false;
            }
        });

    }
    private boolean sendChatMessage() {

        ChatMessage message = new ChatMessage(1, chatText.getText().toString(),"-KSQlA6SdGfaLTFDLfFs");
        //TODO need to create this properly 
        message.setUserId("idtest");
        fdb.sendMessage(message);
//        chatArrayAdapter.add(new ChatMessage(side, chatText.getText().toString()));
//        chatText.setText("");
//        side = !side;
        return true;
    }
    class InnerReceiver extends BroadcastReceiver {

        Context context;

        public InnerReceiver() {

        }

        public InnerReceiver(Context context) {
            this.context = context;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            // if (intent.getAction().equals(RoomRefreshService.BROADCAST_ACTION_POLL)) {
            //String param = intent.getStringExtra(RoomRefreshService.EXTRA_PARAM_B);
            getMessages();
            //Toast.makeText(context,"in onReceive",Toast.LENGTH_SHORT).show();
            Log.e("innerReceiver", "MyReceiver: broadcast received");
            //}
        }
    }
    private void getMessages(){

        ArrayList<ChatMessage> messages = new ArrayList<>(fdb.getMessagesHashMap().values());
        for (ChatMessage message:messages) {
            chatArrayAdapter.add(message);
        }
        fdb.clearMessageMap();
    }

}
