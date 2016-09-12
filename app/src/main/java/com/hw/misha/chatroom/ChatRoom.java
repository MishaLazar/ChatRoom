package com.hw.misha.chatroom;

import android.app.Activity;
import android.database.DataSetObserver;
import android.os.Bundle;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ChatRoom extends Activity {

    ChatArrayAdapter chatArrayAdapter;
    ListView listView;
    EditText chatText;
    Button btn_Send;
    boolean side = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        /*chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.room_message_even_layout);*/
        initViews();
        Log.e("ChatRoomActivity","afterinitView");
        chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.room_message_even_layout);
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
        chatArrayAdapter.add(new ChatMessage(side, chatText.getText().toString()));
        chatText.setText("");
        side = !side;
        return true;
    }

}
