package com.example.ablcordova;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class FingerPrintActivity extends AppCompatActivity {

    private Button btSubmit,btCancel;
    private ImageView ivFingerPrint, ivBack;
    private LinearLayout liSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_print);
        bindViews();
        setClicks();
    }

    private void setClicks() {
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitFingerPrint();
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void submitFingerPrint() {
        ivFingerPrint.setVisibility(View.GONE);
        liSuccess.setVisibility(View.VISIBLE);
        btSubmit.setText("Done");
    }

    private void bindViews() {
        ivBack = findViewById(R.id.iv_back);
        btCancel = findViewById(R.id.bt_cancel);
        btSubmit = findViewById(R.id.bt_submit);
        ivFingerPrint = findViewById(R.id.iv_finger_print);
        liSuccess = findViewById(R.id.li_success);
    }
}