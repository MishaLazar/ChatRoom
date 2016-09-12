package com.hw.misha.chatroom;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteFullException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteOutOfMemoryException;
import android.database.sqlite.SQLiteTableLockedException;

import com.hw.misha.chatroom.R;

/**
 * Created by Misha on 9/10/2016.
 */
public class SQLiteDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ChatRoomDB.db";

    ArrayList<String> query_list;

    public SQLiteDBHelper(Context context) throws SQLiteDatabaseCorruptException,
                                                    SQLiteCantOpenDatabaseException{
        super(context, DATABASE_NAME , null, R.integer.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        getCreateDbQuery();
        for (int i = 0; i < query_list.size() ; i++) {
            String createQuery = query_list.get(i);
            db.execSQL(
                /*"create table scores " +
                        "(id integer primary key, name text,level text,winTime long, date text,attempts text)"*/
                    createQuery
            );
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS ChatRoomUser");
        db.execSQL("DROP TABLE IF EXISTS MyChatRoom");
        db.execSQL("DROP TABLE IF EXISTS RoomTags");
        db.execSQL("DROP TABLE IF EXISTS FavoritesRooms");
        onCreate(db);
    }

    public boolean insertRoom  (String room_ID,String room_Owner,String room_DisplayName,
                                Integer room_isActive,String room_createDate,String room_closeDate

    ) throws SQLiteTableLockedException,SQLiteOutOfMemoryException,SQLiteFullException
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("room_ID", room_ID);
        contentValues.put("room_DisplayName", room_DisplayName);
        contentValues.put("room_Owner", room_Owner);
        contentValues.put("room_isActive", room_isActive);
        contentValues.put("room_createDate", room_createDate);
        contentValues.put("room_closeDate", room_closeDate);
        long test =  db.insert("MyChatRoom", null, contentValues);
        return true;
    }
    public boolean insertUserInfo  (String user_ID,String user_name,String user_password,
                                String user_email,String user_createDate,String user_closeDate

    ) throws SQLiteTableLockedException,SQLiteOutOfMemoryException,SQLiteFullException
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_ID", user_ID);
        contentValues.put("user_name", user_name);
        contentValues.put("user_password", user_password);
        contentValues.put("user_createDate", user_createDate);
        contentValues.put("user_email", user_email);
        contentValues.put("user_closeDate", user_closeDate);
        long test =  db.insert("ChatRoomUser", null, contentValues);
        return true;
    }
    public boolean insertFavorites  (String user_ID,String ChatRoomID

    ) throws SQLiteTableLockedException,SQLiteOutOfMemoryException,SQLiteFullException
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_ID", user_ID);
        contentValues.put("ChatRoomID", ChatRoomID);
        long test =  db.insert("FavoritesRooms", null, contentValues);
        return true;
    }
    public boolean insertRoomTags  (String room_ID,String room_tag

    ) throws SQLiteTableLockedException,SQLiteOutOfMemoryException,SQLiteFullException
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("room_ID", room_ID);
        contentValues.put("room_tag", room_tag);
        long test =  db.insert("RoomTags", null, contentValues);
        return true;
    }

    public boolean updateMyRoomByID(String room_ID,String room_Owner,String room_DisplayName,
                                 Integer room_isActive,String room_createDate,String room_closeDate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("room_DisplayName", room_DisplayName);
        contentValues.put("room_Owner", room_Owner);
        contentValues.put("room_isActive", room_isActive);
        contentValues.put("room_createDate", room_createDate);
        contentValues.put("room_closeDate", room_closeDate);
        db.update("ChatRoomUser", contentValues, "room_ID = ? ", new String[] { room_ID } );
        return true;
    }
    public boolean updateUserByID(String user_ID,String user_name,String user_password,
                                  String user_email,String user_createDate,String user_closeDate

    ) throws SQLiteTableLockedException,SQLiteOutOfMemoryException,SQLiteFullException
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_ID", user_ID);
        contentValues.put("user_name", user_name);
        contentValues.put("user_password", user_password);
        contentValues.put("user_createDate", user_createDate);
        contentValues.put("user_email", user_email);
        contentValues.put("user_closeDate", user_closeDate);
        db.update("MyChatRoom", contentValues, "user_ID = ? ", new String[] { user_ID } );
        return true;
    }
    public Cursor getRoomByID(String room_ID){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from MyChatRoom where room_ID="+room_ID+"", null );
        return res;
    }
    public Cursor getUserInfoByID(String user_ID){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from ChatRoomUser where user_ID="+user_ID+"", null );
        return res;
    }
    public Cursor getRoomTagsByRoomID(String room_ID){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from scores where room_ID="+room_ID+"", null );
        return res;
    }
    public Cursor getFavoritesRooms(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from FavoritesRooms ", null );
        return res;
    }

    /*public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, SCORE_TABLE_NAME);
        return numRows;
    }*/

   /* public Integer deleteScore (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("scores",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }
*/
    /*public ArrayList<String> getAllScores()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from scores", null );
        res.moveToFirst();
        String [] test = res.getColumnNames();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(SCORE_COLUMN_NAME))
                    +":"+res.getString(res.getColumnIndex(SCORE_COLUMN_END_TIME))
                    +":"+res.getString(res.getColumnIndex(SCORE_COLUMN_LEVEL_SELECTED)));
            res.moveToNext();
        }
        return array_list;
    }*/


   /* public ArrayList<String> getTop10ScoresByLevel(String level) throws SQLiteDatabaseCorruptException,SQLiteBindOrColumnIndexOutOfRangeException {
        ArrayList<String> array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql="select * from "+SCORE_TABLE_NAME
                +" where " +SCORE_COLUMN_LEVEL_SELECTED+" like '"+level+"' order by "+SCORE_COLUMN_END_TIME
                +" limit "+SCORE_BOARD_MAX_LIST_SIZE;

        Cursor res =  db.rawQuery( sql, null );
        *//*Cursor res =  db.rawQuery( "select top 10 * from scores where (select * from scores where level="+level+") order by "
                                                        +SCORE_COLUMN_END_TIME, null );*//*
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(SCORE_COLUMN_NAME))
                    +":"+res.getString(res.getColumnIndex(SCORE_COLUMN_END_TIME))
                    +":"+res.getString(res.getColumnIndex(SCORE_COLUMN_LEVEL_SELECTED)));
            res.moveToNext();
        }
        return array_list;
    }*/

    public ArrayList<String> getCreateDbQuery(){
        query_list = new ArrayList<>();
        query_list.add("CREATE TABLE `ChatRoomUser` (" +
                "`ID`INTEGER NOT NULL," +
                "`user_ID`TEXT NOT NULL," +
                "`user_name`TEXT NOT NULL," +
                "`user_password`TEXT NOT NULL," +
                "`user_createDate`TEXT NOT NULL," +
                "`user_email`TEXT," +
                "`user_deleteDate`TEXT," +
                "PRIMARY KEY(`ID`)" +
                ")");
        query_list.add("CREATE TABLE `MyChatRoom` (" +
                "`ID`INTEGER NOT NULL," +
                "`room_ID`REAL NOT NULL," +
                "`room_DisplayName`TEXT," +
                "`room_Owner`TEXT NOT NULL," +
                "`room_isActive`INTEGER NOT NULL," +
                "`room_createDate`TEXT NOT NULL," +
                "`room_closeDate`INTEGER," +
                "PRIMARY KEY(`ID`)," +
                "FOREIGN KEY(`ID`) REFERENCES `RoomTags`(`ID`)," +
                "FOREIGN KEY(`room_Owner`) REFERENCES `ChatRoomUser`(`ID`)" +
                ")");
        query_list.add("CREATE TABLE `FavoritesRooms` (" +
                "`user_ID`TEXT NOT NULL," +
                "`ChatRoomID`TEXT NOT NULL," +
                "PRIMARY KEY(`user_ID`,`ChatRoomID`)," +
                "FOREIGN KEY(`user_ID`) REFERENCES `ChatRoomUser`(`ID`)," +
                "FOREIGN KEY(`ChatRoomID`) REFERENCES `MyChatRoom`(`ID`)" +
                ")");
        query_list.add("CREATE TABLE `RoomTags` (" +
                "`room_ID`INTEGER NOT NULL," +
                "`room_tag`TEXT NOT NULL," +
                "PRIMARY KEY(`room_ID`)," +
                "FOREIGN KEY(`room_ID`) REFERENCES `MyChatRooms`(`ID`)" +
                ")");
        return query_list;
    }

}
