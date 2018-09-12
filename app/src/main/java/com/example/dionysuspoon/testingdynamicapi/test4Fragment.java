package com.example.dionysuspoon.testingdynamicapi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Dionysus.Poon on 10/5/2018.
 */

public class test4Fragment extends BaseFragment {
    TextView test1;
    TextView test2;
    TextView test3;
    TextView test4;
    eData masterData;

    public static test4Fragment newInstance(eData masterData) {
        test4Fragment fragment = new test4Fragment();
        Bundle args = new Bundle();
        args.putParcelable("eData", masterData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            masterData = getArguments().getParcelable("eData");
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.test4,container,false);
        test1 = v.findViewById(R.id.test1);
        test2 = v.findViewById(R.id.test2);
        test3 = v.findViewById(R.id.test3);
        test4 = v.findViewById(R.id.test4);

        if(masterData!= null) {
            if(masterData.getArrayList().get(0) != null ){
                test1.setText(masterData.getArrayList().get(0));
            }

            if(masterData.getHashtable().get("apple") != null) {
                test2.setText("" + masterData.getHashtable().get("apple"));
            }

            if(masterData.getHashMap().get("F") != null) {
                test3.setText("" + masterData.getHashMap().get("F"));
            }

            if(masterData.getEitem() != null) {
                test3.setText("" + masterData.getEitem().getItem1()+masterData.getEitem().getItem2());
            }

        }




        return v;
    }
}
