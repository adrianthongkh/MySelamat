package com.myselamat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.myselamat.model.VaccineQuestionBook;

public class VaccineQuestionActivity extends AppCompatActivity {

    private TextView vQuestion;
    private Button vYesButton, vNoButton;

    private boolean vAnswer;
    private int vScore = 0;
    private int vQuestionNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_question);

        vQuestion = findViewById(R.id.vQuestion);
        vYesButton = findViewById(R.id.vYesButton);
        vNoButton = findViewById(R.id.vNoButton);

        ImageView btn_back = findViewById(R.id.backBtn);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        updateQuestion();

        //Logic for true button
        vYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(vAnswer == true) {
                    vScore++;

                    //perform check before you update the question
                    if (vQuestionNumber == VaccineQuestionBook.questions.length) {
                        Intent intent = new Intent(VaccineQuestionActivity.this, VaccineResultsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("finalScore", vScore);
                        intent.putExtras(bundle);
                        VaccineQuestionActivity.this.finish();
                        startActivity(intent);
                    } else {
                        updateQuestion();
                    }
                }
                else {
                    if (vQuestionNumber == VaccineQuestionBook.questions.length) {
                        Intent intent = new Intent(VaccineQuestionActivity.this, VaccineResultsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("finalScore", vScore);
                        intent.putExtras(bundle);
                        VaccineQuestionActivity.this.finish();
                        startActivity(intent);
                    } else {
                        updateQuestion();
                    }
                }
            }
        });




        //Logic for false button
        vNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(vAnswer == false) {
                    vScore++;

                    //perform check before you update the question
                    if (vQuestionNumber == VaccineQuestionBook.questions.length) {
                        Intent intent = new Intent(VaccineQuestionActivity.this, VaccineResultsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("finalScore", vScore);
                        intent.putExtras(bundle);
                        VaccineQuestionActivity.this.finish();
                        startActivity(intent);
                    } else {
                        updateQuestion();
                    }
                }
                else {
                    if (vQuestionNumber == VaccineQuestionBook.questions.length) {
                        Intent intent = new Intent(VaccineQuestionActivity.this, VaccineResultsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("finalScore", vScore);
                        intent.putExtras(bundle);
                        VaccineQuestionActivity.this.finish();
                        startActivity(intent);
                    } else {
                        updateQuestion();
                    }
                }
            }
        });

    }

    private void updateQuestion() {

        vQuestion.setText(VaccineQuestionBook.questions[vQuestionNumber]);
        vAnswer = VaccineQuestionBook.answers[vQuestionNumber];
        vQuestionNumber++;
    }

    public void clickExit(View view) {
        askToClose();
    }


    @Override
    public void onBackPressed() {
        askToClose();
    }

    private void askToClose (){
        AlertDialog.Builder builder = new AlertDialog.Builder(VaccineQuestionActivity.this);
        builder.setMessage("Please finish answering the questions before you leave");
        builder.setCancelable(true);

        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

            }
        });        AlertDialog alert = builder.create();
        alert.show();
    }

}