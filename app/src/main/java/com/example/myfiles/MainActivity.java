package com.example.myfiles;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.myfiles.fragment.Explorer;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Explorer()).commit();
    }

}