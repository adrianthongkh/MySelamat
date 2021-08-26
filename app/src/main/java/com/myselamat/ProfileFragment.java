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

import com.mikhaellopez.circularimageview.CircularImageView;

import org.jetbrains.annotations.NotNull;

public class ProfileFragment extends Fragment {
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setMessage("By doing so, your covid status will be set to positive.\nProceed?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: update users database
                        Toast.makeText(getContext(), "Covid status set to positive.", Toast.LENGTH_SHORT).show();

                        // TODO: update premises database
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
            }
        });
    }
}