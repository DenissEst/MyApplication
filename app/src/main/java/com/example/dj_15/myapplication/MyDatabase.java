package com.example.dj_15.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.NonNull;
import android.widget.Toast;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


/**
 * Created by dj-15 on 14/05/2017.
 */

public class MyDatabase {
    private SQLiteDatabase database;
    private DBHelper dbHelper;
    private Context context;


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
            createTable(user);
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

    public void createTable(String user){
        String query = "CREATE TABLE IF NOT EXISTS " + user + " ( isbn TEXT UNIQUE PRIMARY KEY, title TEXT, author TEXT, plot TEXT, pages INTEGER, cover TEXT, favourite BOOLEAN, finished BOOLEAN, reading INT);";
        database.execSQL(query);
    }

    public boolean insertNewBook(Book book, String user){
        Cursor check = getBook(user, book.isbn);
        if(check != null) {
            Log.e("ATTENZIONE", "libro già presente");
            return false;
        }else{
            ContentValues v = new ContentValues();
            v.put("isbn", book.isbn);
            v.put("cover", book.urlCover);
            v.put("title", book.title);
            v.put("author", book.author);
            v.put("plot", book.plot);
            v.put("pages", book.numPages);
            v.put("reading", 0);
            v.put("finished", false);
            v.put("favourite", false);
            try {
                database.insert(user, null, v);
                Log.e("PROCESSO", "Inserito " + book.title);
                return true;
            } catch (SQLiteException sqle) {
                Log.e("PROCESSO", "non è andata la insert del libro");
                sqle.printStackTrace();
                return false;
            }
        }
    }

    public boolean removeBook(Book book, String user){
        String where_clause = "isbn = ?";
        Cursor check = getBook(user, book.isbn);
        if(check == null){
            Log.e("ERRORE", "libro non esiste");
            return false;
        }else {
            try {
                database.delete(user, where_clause, new String[]{book.isbn});
                Log.e("PROCESSO", "Cancellato " + book.title);
                return true;
            } catch (SQLiteException sql) {
                Log.e("PROCESSO", "Non ha cancellato il libro " + book.title);
                sql.printStackTrace();
                return false;
            }
        }
    }

    public void beginTransaction(){
        database.beginTransaction();
    }

    public void commit(){
        database.setTransactionSuccessful();
    }

    public void endTransaction(){
        database.endTransaction();
    }

    public Cursor getBook(String user, String isbn){
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + user + " WHERE isbn =  ? ",new String[]{isbn});

        if(cursor == null || cursor.getCount() == 0){
            Log.e("PROCESSO","get Book is null");
            return null;
        }else{
            cursor.moveToFirst();
            Log.e("PROCESSO", "get user is true");
            return cursor;
        }
    }

    public boolean favourite(String user, String isbn){
        Cursor check = getBook(user, isbn);
        database = dbHelper.getWritableDatabase();
        if(check == null)
            return false;
        else{
            ContentValues values = new ContentValues();
            int fav = check.getInt(check.getColumnIndex("favourite"));
            String where = "isbn = ?";
            if(fav == 0)
                values.put("favourite", 1);
            else
                values.put("favourite", 0);
            try{
                database.update(user, values, where, new String[]{isbn});
                Log.e("SUCCESSO", "Aggiornata preferenza " + fav + "del libro " + check.getString(check.getColumnIndex("title")));
                return true;
            }catch (SQLiteException sql){
                Log.e("FALLIMENTO", "UPDATE fallita");
                sql.printStackTrace();
                return false;
            }
        }
    }

    public boolean updatePage(String user, String isbn, int number){
        Cursor check = getBook(user, isbn);
        database = dbHelper.getWritableDatabase();
        if(check == null)
            return false;
        else {
            String where = "isbn = ?";
            ContentValues values = new ContentValues();
            values.put("reading", number);
            if(number == check.getInt(check.getColumnIndex("pages")))
                values.put("finished", true);
            try{
                database.update(user, values, where, new String[]{isbn});
                Log.e("SUCCESSO", "Aggiornata pagina " + number + " a " + check.getString(check.getColumnIndex("title")));
                return true;
            }catch (SQLiteException sql){
                Log.e("FALLITO", "Update fallita");
                sql.printStackTrace();
                return false;
            }
        }
    }

    public boolean bookEnd(String user, String isbn){
        Cursor check = getBook(user, isbn);
        database = dbHelper.getWritableDatabase();
        if(check == null)
            return false;
        else {
            String where = "isbn = ?";
            ContentValues values = new ContentValues();
            values.put("reading", check.getInt(check.getColumnIndex("pages")));
            values.put("finished", true);
            try{
                database.update(user, values, where, new String[]{isbn});
                Log.e("SUCCESSO", "Aggiornato stato a " + check.getString(check.getColumnIndex("title")));
                return true;
            }catch (SQLiteException sql){
                Log.e("FALLITO", "Update fallita");
                sql.printStackTrace();
                return false;
            }
        }
    }

    public Cursor getBookByTitle(String user, String title){
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + user + " WHERE title =  ? ",new String[]{title});

        if(cursor == null || cursor.getCount() == 0){
            Log.e("PROCESSO","get Book is null");
            return null;
        }else{
            cursor.moveToFirst();
            Log.e("PROCESSO", "get user is true");
            return cursor;
        }
    }

    public Cursor getBookByAuthor(String user, String author){
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + user + " WHERE author =  ? ",new String[]{author});

        if(cursor == null || cursor.getCount() == 0){
            Log.e("PROCESSO","get Book is null");
            return null;
        }else{
            cursor.moveToFirst();
            Log.e("PROCESSO", "get book is true");
            return cursor;
        }
    }

    public ArrayList<Book> getAllBooks(String user) {
        ArrayList<Book> books = new ArrayList<>();
        database = dbHelper.getReadableDatabase();
        String stringQuery = "SELECT * FROM " + user;
        Cursor resultset = database.rawQuery(stringQuery,null);
        resultset.moveToFirst();
        while(!resultset.isAfterLast()){
            String isbn = resultset.getString(resultset.getColumnIndex("isbn"));
            int reading = resultset.getInt(resultset.getColumnIndex("reading"));
            int finished = resultset.getInt(resultset.getColumnIndex("finished"));
            Book temp = new Book(isbn, reading, finished);
            books.add(temp);
            resultset.moveToNext();
        }
        resultset.close();
        return books;
    }

    public void updateDB(String user,ArrayList<Book> books){
        if(books.size() > 0) {
            JSONObject jsonToSend = new JSONObject();
            try {
                jsonToSend.put("user", user);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONArray array = new JSONArray();
            for (int i = 0; i < books.size(); i++)
                array.put(books.get(i).buildJSON());
            try {
                jsonToSend.put("books", array);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new BookThread("http://charlytime92.altervista.org/updateDB.php").execute(jsonToSend);
        }
    }
}

