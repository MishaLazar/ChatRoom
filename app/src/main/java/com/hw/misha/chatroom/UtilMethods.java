package com.hw.misha.chatroom;

/**
 * Created by Misha on 9/10/2016.
 */
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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

    public final static String getDateTime() {
        // get date time in custom format
        SimpleDateFormat sdf = new SimpleDateFormat("[yyyy/MM/dd - HH:mm:ss]");
        return sdf.format(new Date());
    }

    public final static String getDateTimeSimple() {
        // get date time in custom format
        SimpleDateFormat sdf = new SimpleDateFormat("[yyyy/MM/dd - HH:mm]");
        return sdf.format(new Date());
    }

}