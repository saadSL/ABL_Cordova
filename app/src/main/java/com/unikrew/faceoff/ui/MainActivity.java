package com.unikrew.faceoff.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.unikrew.faceoff.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_account_otp_verification);

    }

    public void cnicActivity(View view) {
        Intent i = new Intent(view.getContext(), CnicAvailabilityActivity.class);
        startActivity(i);
    }

}