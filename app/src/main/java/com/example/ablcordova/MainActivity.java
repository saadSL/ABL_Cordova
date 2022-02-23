package com.example.ablcordova;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void cnicActivity(View view) {
        Intent i = new Intent(view.getContext(),CNIC_Availability.class);
        startActivity(i);
    }
}