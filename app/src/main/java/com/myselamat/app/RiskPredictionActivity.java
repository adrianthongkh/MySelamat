package com.myselamat.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class RiskPredictionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk_prediction);

        TextView tv_riskResult = findViewById(R.id.tv_riskResult);
        ProgressBar progressBar = findViewById(R.id.progress_anim);
        Button btn_return = findViewById(R.id.btn_risk_pred_return);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_riskResult.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                btn_return.setVisibility(View.VISIBLE);
            }
        }, 2500);

        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}