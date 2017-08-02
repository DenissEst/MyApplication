package com.example.dj_15.myapplication;

import java.util.Observable;

/**
 * Created by Carlotta on 02/08/2017.
 */

public class Position extends Observable {

    private int position;

    public Position(){
        position = Integer.MIN_VALUE;
    }

    public void setPosition(int position){
        if(this.position != position){
            this.position = position;
            setChanged();
            notifyObservers(this.position);
        }
    }
}
