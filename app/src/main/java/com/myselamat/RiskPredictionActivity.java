package com.myselamat;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.myselamat.database.PremisesDatabase;
import com.myselamat.model.Premises;
import com.myselamat.util.Risk;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_WIFI_STATE;

public class RiskPredictionActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    private Risk risk = new Risk();
    private double riskScore;
    private ArrayList<Premises> premises;
    private Location self_location;
    private static final String[] PERMISSIONS = {
            ACCESS_FINE_LOCATION,
            ACCESS_WIFI_STATE
    };
    private DecimalFormat df2 = new DecimalFormat("0.00");

    private static boolean hasPermissions(Context context, String... permissions) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Location> task) {
                    self_location = task.getResult();
                }
            });
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // check permissions
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, 100);
        }
        else {
            fusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Location> task) {
                    self_location = task.getResult();
                }
            });
        }
        setContentView(R.layout.activity_risk_prediction);

        // configure main toolbar
        ImageView btn_back = findViewById(R.id.backBtn);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView tv_header = findViewById(R.id.tv_rp_header);
        ProgressBar progressBar = findViewById(R.id.progress_anim);
        Button btn_return = findViewById(R.id.btn_risk_pred_return);
        Button btn_getRisk = findViewById(R.id.btn_getRisk);
        Button btn_explore = findViewById(R.id.btn_explore);

        // hide google map fragment
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.hide(fm.findFragmentById(R.id.google_map)).commit();

        premises = PremisesDatabase.readAllPremises();

        btn_getRisk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                tv_header.setVisibility(View.VISIBLE);
                btn_getRisk.setVisibility(View.INVISIBLE);
                updateRisk();
            }
        });

        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View popUpLayout = getLayoutInflater().inflate(R.layout.popup_window_explore, null);
                DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();

                PopupWindow popupWindow = new PopupWindow(popUpLayout, dm.widthPixels/ 4 * 3, dm.heightPixels / 2, true);
                popupWindow.showAtLocation(findViewById(R.id.riskPrediction), Gravity.CENTER, 0, 0);

                ImageView btn_dismiss = popUpLayout.findViewById(R.id.btn_dismiss);
                btn_dismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
            }
        });

    }

    private void updateRisk() {

        TextView tv_riskResult = findViewById(R.id.tv_riskResult);
        TextView tv_header = findViewById(R.id.tv_rp_header);
        TextView tv_riskMessage = findViewById(R.id.tv_riskMessage);
        ProgressBar progressBar = findViewById(R.id.progress_anim);
        Button btn_return = findViewById(R.id.btn_risk_pred_return);
        Button btn_explore = findViewById(R.id.btn_explore);

        Premises p = risk.findClosestInfected(new LatLng(self_location.getLatitude(), self_location.getLongitude()), premises);
        riskScore = risk.calculateRisk(new LatLng(self_location.getLatitude(), self_location.getLongitude()), p);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_riskResult.setVisibility(View.VISIBLE);
                tv_riskResult.setText("Risk:\n" + df2.format(riskScore) + "%");
                tv_header.setText("Calculated through proximity to the closest infected premise");

                progressBar.setVisibility(View.INVISIBLE);
                btn_return.setVisibility(View.VISIBLE);
                btn_explore.setVisibility(View.VISIBLE);
                if (riskScore != 0)
                    displayMap(p);
                else {
                    tv_riskMessage.setVisibility(View.VISIBLE);
                }
            }
        }, 2500);
    }

    private void displayMap(Premises p) {

        double lat = p.getPosition().getLatitude();
        double longitude = p.getPosition().getLongitude();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.show(fm.findFragmentById(R.id.google_map)).commit();

        // Initialize map fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);

        // Async map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull @org.jetbrains.annotations.NotNull GoogleMap googleMap) {

                UiSettings uiSettings = googleMap.getUiSettings();
                uiSettings.setMyLocationButtonEnabled(true);
                uiSettings.setZoomControlsEnabled(true);
                uiSettings.setZoomGesturesEnabled(true);
                uiSettings.setMapToolbarEnabled(false);

                final MarkerOptions premiseMarker = new MarkerOptions();
                final MarkerOptions userMarker = new MarkerOptions();

                // Configure marker at the premise
                premiseMarker.title("Nearest Infected Premise: ");
                premiseMarker.position(new LatLng(lat, longitude));
                premiseMarker.snippet(p.getName());
                premiseMarker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

                // configure user marker
                userMarker.title("You are here!");
                userMarker.position(new LatLng(self_location.getLatitude(), self_location.getLongitude()));


                // configure line between user and premise
                PolylineOptions line = new PolylineOptions()
                        .add(new LatLng(lat, longitude))
                        .add(new LatLng(self_location.getLatitude(), self_location.getLongitude()));

                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userMarker.getPosition(), 17));
                googleMap.addMarker(premiseMarker).showInfoWindow();
                googleMap.addMarker(userMarker).showInfoWindow();
                googleMap.addPolyline(line);
            }
        });
    }
}