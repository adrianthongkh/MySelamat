package com.myselamat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class TOckotcam extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    String ck=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        sharedpreferences = getSharedPreferences("CheckIN", Context.MODE_PRIVATE);

        ck=sharedpreferences.getString("CheckIN","No");

        if (ck.toString().equals("Yes"))
        {

            Intent toout = new Intent(TOckotcam.this, Checkout.class);
            finish();
            startActivity(toout);

        }
        if (ck.toString().equals("No"))
        {

            Intent toin = new Intent(TOckotcam.this, QRScanActivity.class);
            finish();
            startActivity(toin);

        }



    };



}
