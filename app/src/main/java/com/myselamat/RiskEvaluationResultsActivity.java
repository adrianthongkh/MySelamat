package com.myselamat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RiskEvaluationResultsActivity extends AppCompatActivity {

    TextView rGrade;
    Button rRetryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk_evaluation_results);

        rGrade = (TextView)findViewById(R.id.rGrade);
        rRetryButton = (Button)findViewById(R.id.rRetry);


        Bundle bundle = getIntent().getExtras();
        int score = bundle.getInt("finalScore");

        if (score >= 5){
            rGrade.setText("High Risk \n You are high risk of infection, please go to the nearest medical center and get yourself tested for Covid-19");
        }else if (score >= 3 && score <= 4){
            rGrade.setText("Moderate Risk \n Stay indoors and if symptoms continue to get worse, please call for medical support");
        }else {
            rGrade.setText("Low Risk \n Just a common cold, no worries");
        }

        rRetryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RiskEvaluationResultsActivity.this, RiskEvaluationQuestionActivity.class));
                RiskEvaluationResultsActivity.this.finish();
            }
        });

    }
}