package com.hw.misha.chatroom;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by Misha on 9/22/2016.
 */
public class CustomGridViewAdapter extends ArrayAdapter<Room> {
    Context context;
    int layoutResourceId;
    ArrayList<Room> data;



    public CustomGridViewAdapter(Context context, int layoutResourceId, ArrayList<Room> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }
    @Override public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RecordHolder holder = null;
        if (row == null) { LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new RecordHolder();
            holder.txtTitle = (TextView) row.findViewById(R.id.item_text);
            holder.imageItem = (ImageView) row.findViewById(R.id.item_image);
            row.setTag(holder);
        } else {
            holder = (RecordHolder) row.getTag();
        }
        Room item = data.get(position);
        holder.txtTitle.setText(item.getRoom_DisplayName());
        holder.imageItem.setImageResource(R.mipmap.ic_launcher);
        holder.roomID = item.getRoom_ID();
        return row;
    }
    static class RecordHolder {
        TextView txtTitle;
        ImageView imageItem;
        String roomID;
    }

}