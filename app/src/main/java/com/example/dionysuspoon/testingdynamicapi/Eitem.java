package com.example.dionysuspoon.testingdynamicapi;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Dionysus.Poon on 10/5/2018.
 */

public class Eitem implements Parcelable{
    private int item1;
    private String item2;

    public Eitem(Integer item1,String item2){
        this.item1 = item1;
        this.item2 = item2;
    }

    protected Eitem(Parcel in) {
        item1 = in.readInt();
        item2 = in.readString();
    }

    public static final Creator<Eitem> CREATOR = new Creator<Eitem>() {
        @Override
        public Eitem createFromParcel(Parcel in) {
            return new Eitem(in);
        }

        @Override
        public Eitem[] newArray(int size) {
            return new Eitem[size];
        }
    };

    public int getItem1() {
        return item1;
    }

    public void setItem1(int item1) {
        this.item1 = item1;
    }

    public String getItem2() {
        return item2;
    }

    public void setItem2(String item2) {
        this.item2 = item2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(item1);
        dest.writeString(item2);
    }
}
