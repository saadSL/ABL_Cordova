package com.unikrew.faceoff.ui;

import static android.os.Build.VERSION_CODES.M;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.unikrew.faceoff.Config;
import com.unikrew.faceoff.R;
import com.unikrew.faceoff.fingerprint.Customization.CustomUI;
import com.unikrew.faceoff.fingerprint.FingerprintConfig;
import com.unikrew.faceoff.fingerprint.FingerprintScannerActivity;
import com.unikrew.faceoff.fingerprint.LivenessNotSupportedException;
import com.unikrew.faceoff.fingerprint.NadraConfig;

public class FingerPrintActivity extends AppCompatActivity {

    private Button btSubmit;
    private ImageView ivFingerPrint, ivBack;
    private LinearLayout liSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_print);
        bindViews();
        setClicks();
        requestExternalStoragePermission();
    }

    private void requestExternalStoragePermission() {
        if (android.os.Build.VERSION.SDK_INT >= M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Config.EXTERNAL_STORAGE_CODE);
            }
        }
    }

    private void setClicks() {
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                submitFingerPrint();
                launchScanning(null, null, null, null, null, FingerprintConfig.Mode.EXPORT_WSQ);
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
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
        btSubmit = findViewById(R.id.bt_submit);
        ivFingerPrint = findViewById(R.id.iv_finger_print);
        liSuccess = findViewById(R.id.li_success);
    }

    private void launchScanning(String name, String username, String password,
                                String cnicNumber, NadraConfig nadraConfig, FingerprintConfig.Mode mode) {
        try {

            CustomUI customUI = new CustomUI()
                    .setShowGuidanceScreen(true);

            // Build FingerprintConfig, required by Fingerprint SDK
            // Fingerprint Config is used to customize the UI and fingerprint scanning options
            // See its usage in 'SettingsActivity' for details
            FingerprintConfig.Builder builder = new FingerprintConfig.Builder()
                    .setFingers(FingerprintConfig.Fingers.EIGHT_FINGERS)
                    .setMode(mode)
                    .setLiveness(true)
                    .setPackPng(true)
                    .setCustomUI(customUI);

            if (nadraConfig != null) {
                builder.setNadraConfig(nadraConfig);
            }

            FingerprintConfig fingerprintConfig = builder.build();

            // Setting intent data and launching scanner activity
            Intent intent = new Intent(FingerPrintActivity.this, FingerprintScannerActivity.class);
            if (mode == FingerprintConfig.Mode.ENROLL) {
                intent.putExtra(FingerprintScannerActivity.NAME_FOR_FINGERPRINT, name);
                intent.putExtra(FingerprintScannerActivity.CNIC_FOR_FINGERPRINT, cnicNumber);
            }
            if (mode == FingerprintConfig.Mode.NADRA) {
                intent.putExtra(FingerprintScannerActivity.USERNAME, username);
                intent.putExtra(FingerprintScannerActivity.PASSWORD, password);
                intent.putExtra(FingerprintScannerActivity.CNIC_FOR_FINGERPRINT, cnicNumber);
            }
            intent.putExtra(FingerprintScannerActivity.FACEOFF_FINGERPRINT_CONFIG, fingerprintConfig);
            startActivityForResult(intent, Config.REQ_SCAN_FINGERPRINT);

        } catch (LivenessNotSupportedException e) {
            Toast.makeText(FingerPrintActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Config.EXTERNAL_STORAGE_CODE) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) { // Camera permission not granted
                showPermissionDialog();
            }
        }
    }

    private void showPermissionDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog);

        ((TextView) dialog.findViewById(R.id.titleTV)).setText(getString(R.string.error));
        ((TextView) dialog.findViewById(R.id.msgTV)).setText(getString(R.string.please_grant_external_storage_permissions));

        dialog.setCanceledOnTouchOutside(false);

        Button dialogButton = dialog.findViewById(R.id.okButton);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestExternalStoragePermission();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}