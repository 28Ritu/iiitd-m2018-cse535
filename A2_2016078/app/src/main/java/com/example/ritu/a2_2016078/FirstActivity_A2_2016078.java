package com.example.ritu.a2_2016078;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FirstActivity_A2_2016078 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first__a2_2016078);
        setTitle("Music Player");
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragmentParentViewGroup, new FirstFragment_A2_2016078())
                    .commit();
        }
    }
}
