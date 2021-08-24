package com.myselamat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.myselamat.model.Premises;
import com.myselamat.model.User;

public class ViewProfileActivity extends AppCompatActivity {

    TextView username, email, phone;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    Button btnUpdate, btnlogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

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

//                final User[] u = {new User()};
//                u[0] = documentSnapshot.toObject(User.class);
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
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}