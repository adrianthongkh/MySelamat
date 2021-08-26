package com.myselamat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.jetbrains.annotations.NotNull;

public class QRFragment extends Fragment {

    private FirebaseUser user;
    private FirebaseFirestore db;
    private SharedPreferences preferences;

    public QRFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize view
        View view = inflater.inflate(R.layout.fragment_qr, container, false);

        TextView tv_username = view.findViewById(R.id.tv_username);
        TextView tv_risk = view.findViewById(R.id.tv_risk_qr);

        // get username
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();

        DocumentReference documentReference =  db.collection("users").document(user.getUid());
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {

                tv_username.setText(value.getString("Username"));
            }
        });

        // get risk
        preferences = getActivity().getSharedPreferences("risk", Context.MODE_PRIVATE);

        String risk_score = preferences.getString("score", "-- --");
        tv_risk.setText(risk_score + "%");

        if (!risk_score.equals("-- --")) {

            Double dRisk = Double.valueOf(risk_score);

            if (dRisk > 70)
                tv_risk.setTextColor(Color.RED);
            else if (dRisk > 20 && dRisk <= 70)
                tv_risk.setTextColor(Color.YELLOW);
            else
                tv_risk.setTextColor(Color.GREEN);

        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        Button newbtn = view.findViewById(R.id.btn_checkin);
        newbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toqr = new Intent(getContext(),TOckotcam.class);
                startActivity(toqr);
            }
        });


    }
}