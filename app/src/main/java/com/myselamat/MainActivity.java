package com.myselamat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseUser user;
    private FirebaseFirestore db;
    private boolean isInfected;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView btn_back = findViewById(R.id.backBtn);
        btn_back.setVisibility(View.INVISIBLE);


        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();

        // Get information from database
        db = FirebaseFirestore.getInstance();

        db.collection("users").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {
                         isInfected = doc.getBoolean("status");
                         getIntent().putExtra("status", isInfected);

                    }
                }
            }
        });

        // Initialize bottom navigation view
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.qr_scan, menu);
        return true;
    }



}