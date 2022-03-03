package com.example.ablcordova;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ablcordova.model.CnicPostParams;
import com.example.ablcordova.model.ResponseDTO;

public class CNIC_Availability extends AppCompatActivity {

    private EditText etAccNumber;
    private EditText etCnicNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cnic_availability);


        etAccNumber = findViewById(R.id.et_accNumber);
        etCnicNumber = findViewById(R.id.et_cnicNumber);
    }

    public void postCustomerDetail(View view) throws InterruptedException {


        if (isEmpty(etAccNumber) ||
            isEmpty(etCnicNumber)){
            showAlert("Please fill all * fields");
            return;
        }
        if (etAccNumber.getText().length() < Config.ACCOUNT_LENGTH){
            showAlert("Account Number Length is not valid");
            return;
        }else if (etCnicNumber.getText().length() < Config.CNIC_LENGTH){
            showAlert("CNIC Length is not valid");
            return;
        }else if (!isOnline()){
            showAlert("No Internet connection!");
            return;
        }

        CnicPostParams CnicPostParams = new CnicPostParams();

        CnicPostParams.getData().setCnic(etCnicNumber.getText().toString());
        CnicPostParams.getData().setAccountNo(etAccNumber.getText().toString());

        myViewModel vm = new myViewModel();
        vm.postCNIC(CnicPostParams,this);


        vm.CnicSuccessLiveData.observe(this, new Observer<ResponseDTO>() {
            @Override
            public void onChanged(ResponseDTO responseDTO) {
                Intent i = new Intent(view.getContext(),OTP_Verification.class);
                i.putExtra(Config.RESPONSE,responseDTO);
                i.putExtra(Config.CNIC_ACC,CnicPostParams);
                startActivity(i);
                clearFields();
            }
        });

        vm.CnicErrorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String responseMsg) {
                showAlert(responseMsg);
            }
        });
    }

    private void clearFields() {
        etAccNumber.setText("");
        etCnicNumber.setText("");
    }

    public Boolean isEmpty(EditText et) {
        if (et.getText().toString().equals("")){
            return true;
        }
        return false;
    }

    public void cancelActivity(View view) {
        finish();
    }

    public void showAlert(String msg){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(CNIC_Availability.this);
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