package com.myselamat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ViewProfileActivity extends AppCompatActivity {

    TextView username, email, phone;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    Button btnUpdate;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        // configure main toolbar
        ImageView btn_back = findViewById(R.id.backBtn);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        phone = findViewById(R.id.vpphone);
        email = findViewById(R.id.vpemail);
        username = findViewById(R.id.vpusername);
        btnUpdate = findViewById(R.id.updateProfile);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

                    phone.setText(documentSnapshot.getString("Phone"));
                    username.setText(documentSnapshot.getString("Username"));
                    email.setText(documentSnapshot.getString("Email"));

            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.print(userId);

                startActivity(new Intent(ViewProfileActivity.this, ProfileActivity.class));
            }
        });

    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();

        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        editor.commit();

        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}