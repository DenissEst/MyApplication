package com.example.dj_15.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Carlotta on 16/05/2017.
 */

public class Book implements Parcelable{
    public String urlCover;
    public String title;
    public String author;
    public String plot;
    public String isbn;
    public int numPages;
    public boolean you;

    public Book(JSONObject item){
        try {
            urlCover = item.getJSONObject("imageLinks").getString("smallThumbnail");
            title = item.getString("title");
            author = "";
            for(int i = 0; i < item.getJSONArray("authors").length(); i++){
                if(author.equals(""))
                    author = item.getJSONArray("authors").getString(i);
                else
                    author += ", " + item.getJSONArray("authors").getString(i);
            }
            if(!item.isNull("description"))
                plot = item.getString("description");
            isbn = "";
            for(int i = 0; i < item.getJSONArray("industryIdentifiers").length(); i++){
                if(isbn.equals(""))
                    isbn = item.getJSONArray("industryIdentifiers").getJSONObject(i).getString("identifier");
                else
                    isbn += ", " + item.getJSONArray("industryIdentifiers").getJSONObject(i).getString("identifier");
            }
            numPages = item.getInt("pageCount");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setPrefer(boolean you){
        this.you = you;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
