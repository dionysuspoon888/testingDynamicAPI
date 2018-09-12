package com.example.dionysuspoon.testingdynamicapi;

import android.content.ClipData;
import android.content.EntityIterator;
import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * Created by Dionysus.Poon on 10/5/2018.
 */

public class eData implements Parcelable {
    private ArrayList<String> arrayList ;
    private Hashtable<String,Double> hashtable = new Hashtable<>();
    private HashMap<String,Boolean> hashMap = new HashMap<>();
    private Eitem eitem;
    private String abc;


    public eData(){}


    protected eData(Parcel in) {
        arrayList = in.createStringArrayList();
        eitem = in.readParcelable(Eitem.class.getClassLoader());
        abc = in.readString();
    }

    public static final Creator<eData> CREATOR = new Creator<eData>() {
        @Override
        public eData createFromParcel(Parcel in) {
            return new eData(in);
        }

        @Override
        public eData[] newArray(int size) {
            return new eData[size];
        }
    };

    public ArrayList<String> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }

    public Hashtable<String, Double> getHashtable() {
        return hashtable;
    }

    public void setHashtable(Hashtable<String, Double> hashtable) {
        this.hashtable = hashtable;
    }

    public HashMap<String, Boolean> getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap<String, Boolean> hashMap) {
        this.hashMap = hashMap;
    }

    public Eitem getEitem() {
        return eitem;
    }

    public void setEitem(Eitem eitem) {
        this.eitem = eitem;
    }

    public String getAbc() {
        return abc;
    }

    public void setAbc(String abc) {
        this.abc = abc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(arrayList);
        dest.writeParcelable(eitem, flags);
        dest.writeString(abc);
    }
}
