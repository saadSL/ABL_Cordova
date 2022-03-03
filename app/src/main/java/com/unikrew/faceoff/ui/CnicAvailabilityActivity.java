package com.unikrew.faceoff.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.unikrew.faceoff.Config;
import com.unikrew.faceoff.R;
import com.unikrew.faceoff.model.CnicPostParams;
import com.unikrew.faceoff.model.ResponseDTO;

public class CnicAvailabilityActivity extends AppCompatActivity {

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
            Toast.makeText(view.getContext(),"Please fill all * fields",Toast.LENGTH_LONG).show();
            return;
        }
        if (etAccNumber.getText().length() < Config.ACCOUNT_LENGTH){
            Toast.makeText(view.getContext(),"Account Length is not valid", Toast.LENGTH_LONG).show();
            return;
        }else if (etCnicNumber.getText().length() < Config.CNIC_LENGTH){
            Toast.makeText(view.getContext(),"CNIC Length is not valid",Toast.LENGTH_SHORT).show();
            return;
        }

        CnicPostParams CnicPostParams = new CnicPostParams();

        CnicPostParams.getData().setCnic(etCnicNumber.getText().toString());
        CnicPostParams.getData().setAccountNo(etAccNumber.getText().toString());

        CnicAvailabilityViewModel vm = new CnicAvailabilityViewModel();
        vm.postCNIC(CnicPostParams);


        vm.CnicSuccessLiveData.observe(this, new Observer<ResponseDTO>() {
            @Override
            public void onChanged(ResponseDTO responseDTO) {
                Intent i = new Intent(view.getContext(), OtpVerificationActivity.class);
                i.putExtra(Config.RESPONSE,responseDTO);
                startActivity(i);
            }
        });

        vm.CnicErrorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String responseDTO) {
                Toast.makeText(CnicAvailabilityActivity.this,responseDTO,Toast.LENGTH_SHORT).show();
            }
        });
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
}