package com.example.dj_15.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Carlotta on 04/08/2017.
 */

public class BitmapThread extends AsyncTask<String, Void, Bitmap> {

    private static final int CONNECTION_TIMEOUT=10000;
    private static final int READ_TIMEOUT=15000;

    ImageView cover;
    HttpURLConnection connection;
    URL url=null;
    String selected;
    Bitmap downloaded;

    public BitmapThread(ImageView cover){
        this.cover = cover;
    }

    @Override
    protected Bitmap doInBackground(String... books) {
        selected = books[0];

        try{
            url = new URL(selected);
        }catch(MalformedURLException e){
            e.printStackTrace();
        }

        try{
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.setDoInput(true);
            connection.connect();
        }catch(IOException e1){
            e1.printStackTrace();
        }

        try {
            int response= connection.getResponseCode();
            if(response==HttpURLConnection.HTTP_OK){
                InputStream input = connection.getInputStream();
                downloaded = BitmapFactory.decodeStream(input);
            }
        }catch(IOException e2){
            e2.printStackTrace();
        }finally {
            connection.disconnect();
        }
        return downloaded;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        cover.setImageBitmap(result);
    }
}