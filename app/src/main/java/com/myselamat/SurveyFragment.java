package com.myselamat;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikhaellopez.circularimageview.CircularImageView;

import org.jetbrains.annotations.NotNull;

public class SurveyFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_survey, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        CircularImageView btn_riskEvaluation = view.findViewById(R.id.btn_riskEvaluation);
        CircularImageView btn_homeIsolation = view.findViewById(R.id.btn_homeIsolation);
        CircularImageView btn_vaccine = view.findViewById(R.id.btn_vaccine);

        btn_riskEvaluation.setOnClickListener(v -> {
            startActivity(new Intent(view.getContext(), RiskEvaluationQuestionActivity.class));
        });

        btn_homeIsolation.setOnClickListener(v -> {
            startActivity(new Intent(view.getContext(), HomeIsolationActivity.class));
        });

        btn_vaccine.setOnClickListener(v -> {
            startActivity(new Intent(view.getContext(), VaccineQuestionActivity.class));
        });

    }
}