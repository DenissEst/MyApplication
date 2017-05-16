package com.example.dj_15.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

/**
 * Created by dj-15 on 14/05/2017.
 */

public class UserDB  {
    protected SQLiteDatabase database;
    private UserHelper dbHelper;
    private Context context;
    private static final String TABLE_NAME = "Profilo";

    public UserDB( Context context){
        this.context =context;

    }

    public UserDB open()throws SQLiteException{
            dbHelper = new UserHelper(context);
            database = dbHelper.getWritableDatabase();
            Log.e("database operations", "open de database");
            return this;
    }

    public void close(){
        database.close();
    }


    public long  insertUser(String nome ,String user, String sesso){
        ContentValues values = new ContentValues();

        values.put("username", user);
        values.put("name",nome);
        values.put("sesso", sesso);
        try {
            database.insertOrThrow( TABLE_NAME,"", values);
            Log.e("database operations", "one raw insert");
            return 0;

        }catch (SQLiteException sqle){
            sqle.printStackTrace();
            return -1;
        }


    }








}
