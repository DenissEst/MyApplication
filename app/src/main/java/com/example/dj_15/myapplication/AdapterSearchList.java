package com.example.dj_15.myapplication;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidquery.AQuery;

import java.util.List;

/**
 * Created by Carlotta on 02/06/2017.
 */

public class AdapterSearchList extends ArrayAdapter<Book> implements View.OnClickListener{

    private class ViewHolder{
        public ImageView cover;
        public TextView title;
        public TextView author;
        public Button you;
        public RelativeLayout text;
    }

    private AQuery aQuery;

    private Position var;

    public AdapterSearchList(Context context, int resource, List<Book> books, Position var){
        super(context, resource, books);
        this.var = var;
        aQuery = new AQuery(context);
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
            holder.text = (RelativeLayout) convertView.findViewById(R.id.title_container);

            convertView.setTag(holder);
        }else
            holder = (ViewHolder) convertView.getTag();
        Book book = getItem(position);

        //TODO immagini di copertina

        holder.title.setText(book.title);
        holder.author.setText(book.author);
        //holder.cover.setImageBitmap(book.cover);

        if(book.you == false){
            holder.you.setBackgroundResource(R.drawable.add_read);
        }else{
            holder.you.setBackgroundResource(R.drawable.remove_read);
        }

        holder.you.setOnClickListener(this);
        holder.you.setTag(position);

        holder.text.setOnClickListener(this);
        holder.text.setTag(position);

        aQuery.id(holder.cover).image(book.urlCover);

        return convertView;
    }

    @Override
    public void onClick(View view) {
        int position = (int) view.getTag();;
        switch(view.getId()){
            case R.id.title_container:
                var.setPosition(position, false, false);
                break;
            case R.id.add_remove:
                Book temp = getItem(position);
                if(temp.you == false) {
                    temp.setPrefer(true);
                    view.setBackgroundResource(R.drawable.remove_read);
                    var.setPosition(position, true, false);
                }else{
                    temp.setPrefer(false);
                    view.setBackgroundResource(R.drawable.add_read);
                    var.setPosition(position, false, true);
                }
                break;
        }
    }



}