package com.example.dionysuspoon.testingdynamicapi;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Dionysus.Poon on 7/5/2018.
 */

public class test3Activity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startFrag(R.id.fl_main,new test3Fragment(),getFragmentManager());
    }
}
