package com.hw.misha.chatroom;

/**
 * Created by Misha on 9/10/2016.
 */
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class UtilMethods {



    public final static String getTimeStamp(){
        Calendar calendar = Calendar.getInstance();
        String currentTimestamp = new Timestamp(calendar.getTime().getTime()).toString();
        return currentTimestamp;
    }

    private final String getDateTime() {
        // get date time in custom format
        SimpleDateFormat sdf = new SimpleDateFormat("[yyyy/MM/dd - HH:mm:ss]");
        return sdf.format(new Date());
    }
}