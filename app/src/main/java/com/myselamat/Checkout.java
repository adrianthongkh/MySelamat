package com.myselamat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.myselamat.model.TravelHistory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Checkout extends AppCompatActivity {
    private String id;
    private Button toout;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout);

        toout= findViewById(R.id.ckout);
        FirebaseFirestore fStore;
        FirebaseUser user;
        FirebaseAuth fAuth;
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();



        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference his = (CollectionReference) db.collection("History");



       toout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Task<QuerySnapshot> query = his.whereEqualTo("idd", ""+user.getUid())
                       .get()
                       .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                           @Override
                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                               for (QueryDocumentSnapshot document : task.getResult()) {

                                   if (document.get("CheckOut").toString().equals("False"))
                                     id= document.getId().toString();



                               }

                           }
                       });

               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
               Date date = new Date();
               HashMap checkout = new HashMap();

               checkout.put("CheckOut",""+formatter.format(date));
               db.collection("History").document(""+id).update(checkout)
                       .addOnSuccessListener(new OnSuccessListener() {
                           @Override
                           public void onSuccess(Object o) {
                               sharedpreferences = getSharedPreferences("CheckIN", Context.MODE_PRIVATE);

                               SharedPreferences.Editor editor = sharedpreferences.edit();
                               editor.putString("CheckIN","No");
                               editor.commit();

                               Toast.makeText(Checkout.this,"Successfully Checked Out", Toast.LENGTH_SHORT).show();
                               Intent intent = new Intent(Checkout.this,MainActivity.class);
                               startActivity(intent);
                           }
                       });

               Toast.makeText(Checkout.this,"Please Check Out Again", Toast.LENGTH_SHORT).show();











           }
       });

    }
}
