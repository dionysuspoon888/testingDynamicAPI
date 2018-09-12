package com.example.dionysuspoon.testingdynamicapi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dionysus.Poon on 7/5/2018.
 */

public class test3Fragment extends BaseFragment {
    TextView tv_1;
    TextView tv_2;

    String json;
    String treeJson;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.test3,container,false);


        tv_1 = v.findViewById(R.id.tv_1);
        tv_2 = v.findViewById(R.id.tv_2);


        json = "{\"doctor_code\":\"G999\",\"clinic_code\":AWM}";

        treeJson = "{ \"result\": [ { \"id\": 1, \"name\": \"test1\" }, { \"id\": 2, \"name\": \"test12\", \"children\": [ { \"id\": 3, \"name\": \"test123\", \"children\": [ { \"id\": 4, \"name\": \"test123\" } ] } ] } ] }";


        JSONObject mainObject = null;

        try {
            mainObject = new JSONObject(treeJson);
//            String DrID = mainObject.getString("doctor_code");
//            String location = mainObject.getString("clinic_code");
//            tv_1.setText(DrID);
//            tv_2.setText(location);
            JSONArray jA = mainObject.getJSONArray("result");
            JSONObject jA_object1 = jA.getJSONObject(1);
            JSONArray jA2 = jA_object1.getJSONArray("children");
            JSONObject jA2_object0 = jA2.getJSONObject(0);
            JSONArray jA3 = jA2_object0.getJSONArray("children");
            JSONObject jA3_object0 = jA3.getJSONObject(0);
            int ID_JA3 = jA3_object0.getInt("id");

            tv_1.setText(""+ID_JA3);


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return v;


    }
}
