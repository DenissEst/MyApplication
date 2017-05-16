package com.example.dj_15.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by dj-15 on 12/05/2017.
 */

public class UserHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "ProfiloLocal.db";
    private static final String TABLE_NAME = "Profilo";
    private static final int version = 1;
    private static UserHelper instance;



    public UserHelper(Context context) {

        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String q = "CREATE TABLE" + TABLE_NAME + "( username VARCHAR PRIMARY KEY  ," + "name  TEXT," + " sesso TEXT)";
        db.execSQL(q);
        Log.e("DATA OPERATIONS", "tabel created");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
}