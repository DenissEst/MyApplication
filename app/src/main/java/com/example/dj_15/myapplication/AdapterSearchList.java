package com.example.dj_15.myapplication;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Carlotta on 02/06/2017.
 */

public class AdapterSearchList extends ArrayAdapter<Book> implements View.OnClickListener {

    private class ViewHolder{
        public ImageView cover;
        public TextView title;
        public TextView author;
        public Button you;
    }

    public AdapterSearchList(Context context, int resource, List<Book> books){
        super(context, resource, books);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        return getViewOptimize(position, convertView, parent);
    }

    public View getViewOptimize(int position, View convertView, ViewGroup parent){
        ViewHolder holder = null;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.result_line, null);
            holder = new ViewHolder();
            holder.cover = (ImageView) convertView.findViewById(R.id.cover);
            holder.title = (TextView) convertView.findViewById(R.id.bookTitle);
            holder.author = (TextView) convertView.findViewById(R.id.author);
            holder.you = (Button) convertView.findViewById(R.id.add_remove);
            convertView.setTag(holder);
        }else
            holder = (ViewHolder) convertView.getTag();
        Book book = getItem(position);

        //TODO immagini di copertina

        holder.title.setText(book.title);
        holder.author.setText(book.author);
        if(book.you == false){
            holder.you.setBackgroundResource(R.drawable.add_read);
            holder.you.setOnClickListener(this);
        }else{
            holder.you.setBackgroundResource(R.drawable.remove_read);
        }
        return convertView;
    }

    @Override
    public void onClick(View view) {
        //Gestire i click sul bottone e sulla linea
    }
}
