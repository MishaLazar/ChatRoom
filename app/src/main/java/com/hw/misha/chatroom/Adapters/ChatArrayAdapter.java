package com.hw.misha.chatroom.Adapters;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.hw.misha.chatroom.Entities.ChatMessage;
import com.hw.misha.chatroom.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Misha on 9/11/2016.
 */
public class ChatArrayAdapter extends ArrayAdapter<ChatMessage> {

    TextView chatText;
    List<ChatMessage> chatMessageList = new ArrayList<>();
    Context context;

    @Override
    public void add(ChatMessage object) {
        chatMessageList.add(object);
        super.add(object);
    }

    public ChatArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.context = context;
    }

    public int getCount() {
        return this.chatMessageList.size();
    }

    public ChatMessage getItem(int index) {
        return this.chatMessageList.get(index);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMessage chatMessageObj = getItem(position);
        View row = convertView;
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (chatMessageObj.left) {
            row = inflater.inflate(R.layout.room_message_even_layout, parent, false);
        }else{
            row = inflater.inflate(R.layout.room_message_odd_layout, parent, false);
        }
        chatText = (TextView) row.findViewById(R.id.msg);
        chatText.setText(chatMessageObj.message);
        return row;
    }
}
