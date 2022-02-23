package com.example.ablcordova;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class OTP_Verification extends AppCompatActivity {

    private EditText otp1;
    private EditText otp2;
    private EditText otp3;
    private EditText otp4;
    private EditText otp5;
    private EditText otp6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_verification);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.otp_toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0000FF")));

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        otp1 = findViewById(R.id.et_otp1);
        otp2 = findViewById(R.id.et_otp2);
        otp3 = findViewById(R.id.et_otp3);
        otp4 = findViewById(R.id.et_otp4);
        otp5 = findViewById(R.id.et_otp5);
        otp6 = findViewById(R.id.et_otp6);

        Intent i = getIntent();

        System.out.println(i.getStringExtra(CNIC_Availability.ACCOUNT_NUMBER));
        System.out.println(i.getStringExtra(CNIC_Availability.CNIC_NUMBER));

    }

    public void OTPVerification(View view) {
                if (isEmpty(otp1) || isEmpty(otp4)||
                    isEmpty(otp2) || isEmpty(otp5)||
                    isEmpty(otp3) || isEmpty(otp6)){

            Toast.makeText(view.getContext(),"OTP fields Empty",Toast.LENGTH_SHORT).show();
            return;
        }
        startActivity(new Intent(OTP_Verification.this,FingerPrintActivity.class));
    }

    public Boolean isEmpty(EditText et) {
        if (et.getText().toString().equals("") || et.getText().toString().equals("-")){
            return true;
        }
        return false;
    }

    public void cancelActivity(View view) {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void sendOtp(View view) {
        Toast.makeText(view.getContext(),"Sorry, currently the function is not responsive !!!",Toast.LENGTH_LONG).show();
    }

    public void messageFunc(View view){
        Toast.makeText(view.getContext(),"Sorry, currently the function is not responsive !!!",Toast.LENGTH_LONG).show();
    }

    public void powerSettingFunc(View view){
        Toast.makeText(view.getContext(),"Sorry, currently the function is not responsive !!!",Toast.LENGTH_LONG).show();
    }
}