package com.example.dj_15.myapplication;

import java.util.Observable;

/**
 * Created by Carlotta on 02/08/2017.
 */

public class Position extends Observable {

    private int position;
    public boolean add;
    public boolean remove;

    public Position(){
        position = Integer.MIN_VALUE;
        add = false;
        remove = false;
    }

    public void setPosition(int position, boolean add, boolean remove){
        if(this.position != position){
            this.position = position;
            if(this.add != add)
                this.add = add;
            if(this.remove != remove)
                this.remove = remove;
            setChanged();
            notifyObservers(this.position);
        }
    }

    public void restore(){
        position = Integer.MIN_VALUE;
        add = false;
        remove = false;
    }
}
