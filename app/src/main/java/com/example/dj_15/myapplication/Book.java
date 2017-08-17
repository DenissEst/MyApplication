package com.example.dj_15.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Carlotta on 16/05/2017.
 */

public class Book implements Serializable {

    public String urlCover;
    public String title;
    public String author;
    public String plot;
    public String isbn;
    public int numPages;
    public boolean you;
    public Bitmap cover;
    public int reading;
    public int finished;

    public Book(String isbn, int reading, int finished){
        this.isbn = isbn;
        this.reading = reading;
        this.finished = finished;
    }

    public Book(JSONObject item){
        try {
            if(!item.isNull("imageLinks"))
                urlCover = item.getJSONObject("imageLinks").getString("smallThumbnail");
            else
                urlCover = "";
            if(!item.isNull("title"))
                title = item.getString("title");
            else
                title = "";
            if(!item.isNull("authors")) {
                author = "";
                for (int i = 0; i < item.getJSONArray("authors").length(); i++) {
                    if (author.equals(""))
                        author = item.getJSONArray("authors").getString(i);
                    else
                        author += ", " + item.getJSONArray("authors").getString(i);
                }
            }else
                author = "";
            if(!item.isNull("description"))
                plot = item.getString("description");
            else
                plot = "";
            isbn = "";
            if(!item.isNull("industryIdentifiers")) {
                for (int i = 0; i < item.getJSONArray("industryIdentifiers").length(); i++) {
                    if (isbn.equals(""))
                        isbn = item.getJSONArray("industryIdentifiers").getJSONObject(i).getString("identifier");
                    else
                        isbn += ", " + item.getJSONArray("industryIdentifiers").getJSONObject(i).getString("identifier");
                }
            }
            if(!item.isNull("pageCount"))
                numPages = item.getInt("pageCount");
            else
                numPages = 0;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setPrefer(boolean you){
        this.you = you;
    }

    public JSONObject buildJSON(){
        JSONObject json = new JSONObject();

        try {
            json.put("urlCover", urlCover);
            json.put("isbn", isbn);
            json.put("numPages", numPages);
            json.put("action", you);
            json.put("reading", reading);
            json.put("finished", finished);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }
}
