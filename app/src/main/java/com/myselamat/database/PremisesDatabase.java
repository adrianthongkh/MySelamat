package com.myselamat.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.myselamat.model.Premises;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PremisesDatabase {

    public static ArrayList<Premises> readAllPremises() {

        ArrayList<Premises> premises = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("premises")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Premises p = new Premises();
                                p.setDocID(document.getId());
                                p.setPosition(document.getGeoPoint("position"));
                                p.setName(document.getString("name"));
                                p.setStatus(document.getBoolean("status"));
                                premises.add(p);
                            }
                        } else {
                            Log.d("Premises Error", "Error getting premises: " + task.getException());
                        }

                    }
                });
        return premises;
    }

}
