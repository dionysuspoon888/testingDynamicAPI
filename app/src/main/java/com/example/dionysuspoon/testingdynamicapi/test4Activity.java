package com.example.dionysuspoon.testingdynamicapi;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * Created by Dionysus.Poon on 10/5/2018.
 */

public class test4Activity extends BaseActivity {
    eData data;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = new eData();
        ArrayList<String> tmpList = new ArrayList<>();
        tmpList.add("a");
        tmpList.add("b");
        data.setArrayList(tmpList);

        Hashtable<String,Double> tmpHashTABLE = new Hashtable<>();
        tmpHashTABLE.put("apple",3.0);
        tmpHashTABLE.put("bbq",5.2);
        data.setHashtable(tmpHashTABLE);

        HashMap<String,Boolean> tmpHashMap = new HashMap<>();
        tmpHashMap.put("Y",true);
        tmpHashMap.put("F",false);
        data.setHashMap(tmpHashMap);

        data.setEitem(new Eitem(7,"checking"));


        Fragment fragment = test4Fragment.newInstance(data);
        startFrag(R.id.fl_main,fragment,getFragmentManager());
    }
}
