package my.edu.utar.myselamat;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

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

        ImageButton[] imageButtons = {getView().findViewById(R.id.btn_history), getView().findViewById(R.id.btn_riskPrediction), getView().findViewById(R.id.btn_updateDetails)};

        imageButtons[0].setOnClickListener(v -> {
            // switch to history activity
            Intent intent = new Intent(view.getContext(), HistoryActivity.class);
            startActivity(intent);
        });

        imageButtons[1].setOnClickListener(v -> {
            // switch to risk prediction activity
            Intent intent = new Intent(view.getContext(), RiskPredictionActivity.class);
            startActivity(intent);
        });

        imageButtons[2].setOnClickListener(v -> {

            // switch to update details activity
            Intent intent = new Intent(view.getContext(), UpdateDetailsActivity.class);
            startActivity(intent);
        });
    }
}