package com.example.dj_15.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * Created by dj-15 on 14/05/2017.
 */

class DBHelper extends SQLiteOpenHelper{
    private static final String TABLE_NAME = "ProfiLo";
    private static final String DB_NAME = "database.db";


    public DBHelper(Context context){
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String q = "CREATE TABLE IF NOT EXISTS  " + TABLE_NAME + "( username TEXT UNIQUE  ," + "name  TEXT," + " sesso TEXT , " + "info TEXT);";
        db.execSQL(q);
        Log.e("processo","creo tabella");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL(" UPDATE SET name = ' " + newVersion + " ' WHERE name = '" + oldVersion + "'");
    }


}
