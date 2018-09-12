package com.example.dionysuspoon.testingdynamicapi;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Dionysus.Poon on 4/5/2018.
 */

public class test2Activity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       startFrag(R.id.fl_main,new test2Fragment(),getFragmentManager());
    }
}
