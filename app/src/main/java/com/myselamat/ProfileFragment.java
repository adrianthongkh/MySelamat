package com.myselamat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ProfileFragment extends Fragment {

    private boolean isInfected;
    private FirebaseUser user;
    private FirebaseFirestore db;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        // check covid status
        db.collection("users").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {
                        isInfected = doc.getBoolean("status");
                    }
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CircularImageView img_history = view.findViewById(R.id.btn_history);
        CircularImageView img_risk_prediction = view.findViewById(R.id.btn_risk_pred);
        CircularImageView img_profile = view.findViewById(R.id.btn_updateDetails);
        CircularImageView img_update = view.findViewById(R.id.btn_update);

        img_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), HistoryActivity.class));
            }
        });

        img_risk_prediction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), RiskPredictionActivity.class));
            }
        });

        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ViewProfileActivity.class));
            }
        });

        img_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isInfected) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setCancelable(true);
                    builder.setMessage("By doing so, your covid status will be set to positive.\n\nPremises checked in within 14 days will have positive status.\n\nProceed?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (!isInfected) {
                                db.collection("users").document(user.getUid()).update("status", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        getActivity().getIntent().putExtra("status", true);
                                        isInfected = true;
                                        updatePremiseStatus();
                                    }
                                });
                            } else {
                                Toast.makeText(getContext(), "Covid-19 status is already positive.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    Toast.makeText(getContext(), "Covid-19 status is already positive.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updatePremiseStatus() {

        db.collection("History")
                .whereEqualTo("idd", user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                boolean isAffected;
                                try {
                                    isAffected = checkDays(document.getString("CheckOut"));
                                } catch (ParseException ex) {
                                    ex.printStackTrace();
                                    break;
                                }

                                if (isAffected) {
                                    db.collection("premises").whereEqualTo("name", document.getString("Location"))
                                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                document.getReference().update("status", true);
                                            }
                                            Toast.makeText(getContext(), "Status updated.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }

                        }

                    }
                });
    }

    private boolean checkDays(String checkOut) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        try {
            Date dCheckOut = format.parse(checkOut);
            Date dCurrent = Calendar.getInstance().getTime();

            long lCheckOut = dCheckOut.getTime();
            long lCurrent = dCurrent.getTime();

            long diff = lCurrent - lCheckOut;
            long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1;

            if (days >= 14)
                return false;

            return true;

        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return false;
    }
}