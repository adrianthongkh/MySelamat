package com.myselamat.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.myselamat.model.IsolationSurvey;

public class HomeIsolationActivity extends AppCompatActivity {

    private IsolationSurvey isolationSurvey = new IsolationSurvey();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_isolation);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setMessage(isolationSurvey.getWarning());
        builder.setPositiveButton("I understand.", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // do nothing
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();


        getIntent().putExtra("survey", isolationSurvey);

        Button btn_submit = findViewById(R.id.btn_hi_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeIsolationActivity.this, HomeIsolationResultActivity.class);
                intent.putExtra("result", isolationSurvey);

                startActivity(intent);
                finish();
            }
        });

    }
}