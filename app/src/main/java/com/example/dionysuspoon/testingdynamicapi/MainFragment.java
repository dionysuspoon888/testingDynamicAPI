package com.example.dionysuspoon.testingdynamicapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Dionysus.Poon on 4/5/2018.
 */

public class MainFragment extends BaseFragment{
    Button b_raw;
    Button b_test1;
    Button b_test2;
    Button b_test3;
    Button b_test4;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main,container,false);
        b_raw = v.findViewById(R.id.b_raw);
        b_raw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),RawActivity.class));
            }
        });

        b_test1 = v.findViewById(R.id.b_test1);
        b_test1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),test1Activity.class));

            }
        });

        b_test2  = v.findViewById(R.id.b_test2);
        b_test2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),test2Activity.class));
            }
        });

        b_test3  = v.findViewById(R.id.b_test3);
        b_test3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),test3Activity.class));
            }
        });
        b_test4  = v.findViewById(R.id.b_test4);
        b_test4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),test4Activity.class));
            }
        });
        return v;

    }
}
