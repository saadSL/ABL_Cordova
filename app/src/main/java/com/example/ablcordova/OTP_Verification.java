package com.example.ablcordova;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.ablcordova.model.CnicPostParams;
import com.example.ablcordova.model.OtpPostParams;
import com.example.ablcordova.model.OtpResponse;
import com.example.ablcordova.model.ResponseDTO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Locale;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class OTP_Verification extends AppCompatActivity {

    private EditText otp1;
    private EditText otp2;
    private EditText otp3;
    private EditText otp4;
    private EditText otp5;
    private EditText otp6;

    private TextView mobileNumber;

    private TextView timer;

    ResponseDTO res;
    CnicPostParams cnicPostParams;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_verification);

        otp1 = findViewById(R.id.et_otp1);
        otp2 = findViewById(R.id.et_otp2);
        otp3 = findViewById(R.id.et_otp3);
        otp4 = findViewById(R.id.et_otp4);
        otp5 = findViewById(R.id.et_otp5);
        otp6 = findViewById(R.id.et_otp6);

        timer = findViewById(R.id.timer);

        mobileNumber = findViewById(R.id.tv_mobileNumber);

        mobileNumber.setText("03XX-XXXX"+Config.fakeContact.substring(Config.fakeContact.length()-3));

        res = (ResponseDTO) getIntent().getSerializableExtra(Config.RESPONSE);
        cnicPostParams = (CnicPostParams) getIntent().getSerializableExtra(Config.CNIC_ACC);

        startTimer(Config.countDownTime);
    }

    private void startTimer(int totalTime) {
        new CountDownTimer(totalTime,1000) {
            @Override
            public void onTick(long l) {
                int minutes = (int)(l/1000)/60;
                int seconds = (int)(l/1000)%60;
                String timeFormat = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
                if (minutes==0){
                    timer.setText(timeFormat+" seconds");
                }else{
                    timer.setText(timeFormat+" minutes");
                }

            }

            @Override
            public void onFinish() {
                showAlert("You OTP Expired");
            }
        }.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void OTPVerification(View view) throws InterruptedException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        if (isEmpty(otp1) || isEmpty(otp4) ||
                isEmpty(otp2) || isEmpty(otp5) ||
                isEmpty(otp3) || isEmpty(otp6)) {
            showAlert("OTP fields empty");
            return;
        }else if (!isOnline()){
            showAlert("No internet connection !!!");
            return;
        }

        String otp = otp1.getText().toString()+otp2.getText().toString()+otp3.getText().toString()+otp4.getText().toString()+otp5.getText().toString()+otp6.getText().toString();

        OtpPostParams otpPostParams = new OtpPostParams();

        otpPostParams.getData().setOtp(encrypt(otp));
        otpPostParams.getData().setRdaCustomerProfileId(""+res.getData().getEntityId());

       myViewModel vm = new myViewModel();
       vm.postOtp(otpPostParams,res.getData().getAccessToken(),this);


       vm.OtpSuccessLiveData.observe(this, new Observer<OtpResponse>() {
           @Override
           public void onChanged(OtpResponse otpResponse) {
               Intent i = new Intent(view.getContext(),FingerPrintActivity.class);
               i.putExtra(Config.RESPONSE,otpResponse);
               startActivity(i);
               clearFields();
           }
       });

       vm.OtpErrorLiveData.observe(this, new Observer<String>() {
           @Override
           public void onChanged(String errorMsg) {
                showAlert(errorMsg);
                clearFields();
           }
       });
//        startActivity(new Intent(OTP_Verification.this, FingerPrintActivity.class));
    }

    private void clearFields() {
        otp1.setText("");
        otp2.setText("");
        otp3.setText("");
        otp4.setText("");
        otp5.setText("");
        otp6.setText("");
    }

    public Boolean isEmpty(EditText et) {
        if (et.getText().toString().equals("") || et.getText().toString().equals("-")) {
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
        myViewModel viewModel = new myViewModel();
        try {
            viewModel.postCNIC(cnicPostParams,this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void messageFunc(View view) {
        Toast.makeText(view.getContext(), "Sorry, currently the function is not responsive !!!", Toast.LENGTH_LONG).show();
    }

    public void powerSettingFunc(View view) {
        Toast.makeText(view.getContext(), "Sorry, currently the function is not responsive !!!", Toast.LENGTH_LONG).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String encrypt(String value) throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

            String initVector = "0000000000000000";
            String key = "4dweqdxcerfvc3rw";
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"),"AES");
            Cipher cipher = Cipher.getInstance("AES");

            cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec,ivParameterSpec);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            String data = Base64.getEncoder().encodeToString(encrypted);
            return data;
    }



    public void showAlert(String msg){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(OTP_Verification.this);
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            return false;
        }
        return true;
    }
}