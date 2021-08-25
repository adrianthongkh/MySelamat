package com.myselamat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class VaccineResultsActivity extends AppCompatActivity {

    TextView vGrade;
    Button vRetryButton;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_results);

        vGrade = (TextView)findViewById(R.id.vGrade);
        vRetryButton = (Button)findViewById(R.id.vRetry);


        Bundle bundle = getIntent().getExtras();
        int score = bundle.getInt("finalScore");

        if (score >= 3){
            vGrade.setText("Please show this message to the doctor \n Only Sinovac is Recommended");
        }else {
            vGrade.setText("All Vaccines are suitable \n Sinovac, AZ, Pfizer, J&J are Recommended");
        }

        vRetryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VaccineResultsActivity.this, VaccineQuestionActivity.class));
                VaccineResultsActivity.this.finish();
            }
        });

    }
}