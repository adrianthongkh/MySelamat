package com.myselamat.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.myselamat.model.Isolation;

import org.jetbrains.annotations.NotNull;

public class HomeIsolationDatabase {

    private final static String TAG = "IsolationDatabase";

    public static void deleteIsolation(String docId) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("isolation").document(docId).delete();

    }

    public static void updateIsolation(Isolation isolation, int flag) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference ref = db.collection("isolation").document(isolation.getDocId());

        /**Flag to determine update operation
         * flag == 1 : update day count
         * else : update day_count and severity **/

        switch (flag) {

            case 1:
                ref.update("day_count", Integer.valueOf(isolation.getDay_count()+1)).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "Day count updated");
                    }
                });
                break;

            default:
                ref.update("day_count", Integer.valueOf(isolation.getDay_count()+1)).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "Day count updated");
                    }
                });
                ref.update("severityStatus", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "Severity Status updated");
                    }
                });
        }

    }

    public static void addIsolation(Isolation isolation) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("isolation").add(isolation).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "Successfully added to database");
                isolation.setDocId(documentReference.getId());
            }
        });

    }
}
