package com.example.dj_15.myapplication;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Monica on 28/04/2017.
 */

public class ExpandableListProfile {

    public static HashMap<String, List<String>> getData() {

        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> favouritebooks = new ArrayList<String>();
        favouritebooks.add("Book1");
        favouritebooks.add("Book2");
        favouritebooks.add("Book3");

        List<String> followers = new ArrayList<String>();
        followers.add("Person1");
        followers.add("Person2");
        followers.add("Person3");

        List<String> following = new ArrayList<String>();
        following.add("Person1");
        following.add("Person2");
        following.add("Person3");

        expandableListDetail.put("I TUOI LIBRI PREFERITI", favouritebooks);
        expandableListDetail.put("TI SEGUONO", followers);
        expandableListDetail.put("STAI SEGUENDO", following);

        return expandableListDetail;
        }
    }