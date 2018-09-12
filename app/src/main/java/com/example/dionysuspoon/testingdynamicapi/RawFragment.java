package com.example.dionysuspoon.testingdynamicapi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Dionysus.Poon on 7/5/2018.
 */

public class RawFragment extends BaseFragment {
    TextView tv_raw1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.raw,container,false);
        tv_raw1 = v.findViewById(R.id.tv_raw1);
        int x = 3;
        int[] seq= {1,2,3};
        while(x>0) {
            tv_raw1 = v.findViewById(R.id.tv_raw1);
            tv_raw1.setText(""+x);
            x--;
        }


        return v;
    }
}
