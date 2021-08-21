package com.myselamat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.myselamat.database.PremisesDatabase;
import com.myselamat.model.Premises;
import com.myselamat.util.Risk;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RiskPredictionActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    private Risk risk = new Risk();
    private double riskScore;
    private ArrayList<Premises> premises;
    private Location self_location;
    private LocationCallback locationCallback;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 12 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            requestNewLocationData();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestNewLocationData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk_prediction);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull @NotNull LocationResult locationResult) {
                if (locationResult == null) return;
                self_location = locationResult.getLastLocation();
            }
        };

        TextView tv_header = findViewById(R.id.tv_rp_header);
        TextView tv_riskResult = findViewById(R.id.tv_riskResult);
        ProgressBar progressBar = findViewById(R.id.progress_anim);
        Button btn_return = findViewById(R.id.btn_risk_pred_return);
        Button btn_getRisk = findViewById(R.id.btn_getRisk);

        premises = PremisesDatabase.readAllPremises();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request permission
            ActivityCompat.requestPermissions(RiskPredictionActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 12);

        } else {
            btn_getRisk.setVisibility(View.VISIBLE);
            requestNewLocationData();
        }

        btn_getRisk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                tv_header.setVisibility(View.VISIBLE);
                btn_getRisk.setVisibility(View.INVISIBLE);

                if (self_location != null) {
                    updateRisk();
                } else {
                    requestNewLocationData();
                }
            }
        });

        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    private void requestNewLocationData() {

        LocationRequest request = LocationRequest.create();
        request.setInterval(10000);
        request.setFastestInterval(5000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(request);

        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @SuppressLint("MissingPermission")
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                fusedLocationClient.requestLocationUpdates(request, locationCallback, Looper.getMainLooper());
            }
        });
        updateRisk();
    }

    private void updateRisk() {

        TextView tv_riskResult = findViewById(R.id.tv_riskResult);
        ProgressBar progressBar = findViewById(R.id.progress_anim);
        Button btn_return = findViewById(R.id.btn_risk_pred_return);

        Log.d("Test", "Hello");
        riskScore = risk.calculateRisk(new LatLng(self_location.getLatitude(), self_location.getLongitude()), premises);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_riskResult.setVisibility(View.VISIBLE);
                tv_riskResult.setText(riskScore + "%");

                progressBar.setVisibility(View.INVISIBLE);
                btn_return.setVisibility(View.VISIBLE);
            }
        }, 2500);
    }
}