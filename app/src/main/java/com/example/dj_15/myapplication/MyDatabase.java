package com.example.dj_15.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;
import android.util.Log;


/**
 * Created by dj-15 on 14/05/2017.
 */

public class MyDatabase {
    SQLiteDatabase database;
    DBHelper dbHelper;
    Context context;


    private static final String TABLE_NAME = "ProfiLo";



    public MyDatabase(Context ctx){
        dbHelper = new DBHelper(ctx);
    }

    public void open(){
        database = dbHelper.getWritableDatabase();
        Log.e("operazion","open");
    }


    public void close(){
        database.close();
    }



    public boolean insertUser(String name, String user, String sesso){
        ContentValues v = new ContentValues();
        v.put("username",user);
        v.put("name",name);
        v.put("sesso",sesso);
        try {
             database.insert(TABLE_NAME, null, v);
            Log.e("PROCESSO", "insert user");
            return true;
        }catch (SQLiteException sqle){

            Log.e("PROCESSO", " nonn insert user");
            sqle.printStackTrace();
            return false;
        }
    }

    public boolean upDate(String name, String user, String sesso ,String info){
        ContentValues v = new ContentValues();
        v.put("username",user);
        v.put("name",name);
        v.put("sesso",sesso);
        v.put("info",info);
        try {

            /*
            "id" + "='"
                + currentActiveId + "'"
             */
            String where = " username " + "='" + user + "'";
            database.update(TABLE_NAME , v ,where ,null);
          return true;
    }catch (SQLiteException sqle){
        Log.e("PROCESSO", " NOOO update user");
        sqle.printStackTrace();
        return false;
    }




    }



    public Cursor getUser(String user){
        database = dbHelper.getReadableDatabase();
       Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE username =  ? ",new String[]{user});

        if(cursor == null || cursor.getCount() == 0){

            Log.e("PROCESSO","get user is null");
            return null;

        }else{

            cursor.moveToFirst();
            Log.e("PROCESSO", "get user is true");
            return cursor;
        }
    }

}

