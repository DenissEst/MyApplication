package com.example.dj_15.myapplication;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Carlotta on 02/08/2017.
 */

public class BookFragment extends Fragment {

    private Book selected;
    private Bundle args;
    private ImageView cover;
    private TextView title;
    private TextView author;
    private TextView isbn;
    private TextView numPage;
    private TextView plot;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_fragment, container, false);

        cover = (ImageView) view.findViewById(R.id.img_cover);
        title = (TextView) view.findViewById(R.id.title_content);
        author = (TextView) view.findViewById(R.id.author_content);
        isbn = (TextView) view.findViewById(R.id.isbn_content);
        numPage = (TextView) view.findViewById(R.id.number_content);
        plot = (TextView) view.findViewById(R.id.number_content);

        args = getArguments();
        selected = new Book(args.getString("cover"), args.getString("title"), args.getString("author"), args.getString("plot"), args.getString("isbn"), args.getInt("numPages"), args.getBoolean("you"));

        new BitmapThread(cover).execute(selected.urlCover);

        return view;
    }

}