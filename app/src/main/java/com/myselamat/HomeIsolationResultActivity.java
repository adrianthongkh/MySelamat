package com.myselamat;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import com.myselamat.model.IsolationSurvey;

public class HomeIsolationResultActivity extends AppCompatActivity {

    private IsolationSurvey isolationSurvey;

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    @RequiresPermission(Manifest.permission.CALL_PHONE)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_isolation_result);


        TextView tv_result = findViewById(R.id.tv_hi_result);
        Button btn_homePage = findViewById(R.id.btn_hi_return);
        Button btn_call = findViewById(R.id.btn_call);

        isolationSurvey = (IsolationSurvey) getIntent().getSerializableExtra("result");

        tv_result.setText(isolationSurvey.getMessageFromScore());

        if (isolationSurvey.isSeverity()) {
            btn_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkPermission(Manifest.permission.CALL_PHONE, 100);
                }
            });
        }
        else {
            btn_call.setVisibility(View.INVISIBLE);
            btn_homePage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }
    public void checkPermission(String permission, int requestCode) {

        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(HomeIsolationResultActivity.this, permission) == PackageManager.PERMISSION_DENIED) {

            // Request permission
            ActivityCompat.requestPermissions(HomeIsolationResultActivity.this, new String[] { permission }, requestCode);
        }
        else {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:999"));
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:999"));
            startActivity(intent);
        }
    }
}