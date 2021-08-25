package com.myselamat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.myselamat.model.RiskEvaluationQuestionBook;

public class RiskEvaluationQuestionActivity extends AppCompatActivity {

    private TextView rQuestion;
    private Button rYesButton, rNoButton;

    private boolean rAnswer;
    private int rScore = 0;
    private int rQuestionNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk_evaluation_question);

        rQuestion = (TextView)findViewById(R.id.question);
        rYesButton = (Button)findViewById(R.id.rYesButton);
        rNoButton = (Button)findViewById(R.id.rNoButton);

        updateQuestion();

        //Logic for true button
        rYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rAnswer == true) {
                    rScore++;

                    //perform check before you update the question
                    if (rQuestionNumber == RiskEvaluationQuestionBook.questions.length) {
                        Intent intent = new Intent(RiskEvaluationQuestionActivity.this, RiskEvaluationResultsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("finalScore", rScore);
                        intent.putExtras(bundle);
                        RiskEvaluationQuestionActivity.this.finish();
                        startActivity(intent);
                    } else {
                        updateQuestion();
                    }
                }
                else {
                    if (rQuestionNumber == RiskEvaluationQuestionBook.questions.length) {
                        Intent intent = new Intent(RiskEvaluationQuestionActivity.this, RiskEvaluationResultsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("finalScore", rScore);
                        intent.putExtras(bundle);
                        RiskEvaluationQuestionActivity.this.finish();
                        startActivity(intent);
                    } else {
                        updateQuestion();
                    }
                }
            }
        });




        //Logic for false button
        rNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rAnswer == false) {
                    rScore++;

                    //perform check before you update the question
                    if (rQuestionNumber == RiskEvaluationQuestionBook.questions.length) {
                        Intent intent = new Intent(RiskEvaluationQuestionActivity.this, RiskEvaluationResultsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("finalScore", rScore);
                        intent.putExtras(bundle);
                        RiskEvaluationQuestionActivity.this.finish();
                        startActivity(intent);
                    } else {
                        updateQuestion();
                    }
                }
                else {
                    if (rQuestionNumber == RiskEvaluationQuestionBook.questions.length) {
                        Intent intent = new Intent(RiskEvaluationQuestionActivity.this, RiskEvaluationResultsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("finalScore", rScore);
                        intent.putExtras(bundle);
                        RiskEvaluationQuestionActivity.this.finish();
                        startActivity(intent);
                    } else {
                        updateQuestion();
                    }
                }
            }
        });

    }

    private void updateQuestion() {

        rQuestion.setText(RiskEvaluationQuestionBook.questions[rQuestionNumber]);
        rAnswer = RiskEvaluationQuestionBook.answers[rQuestionNumber];
        rQuestionNumber++;
    }

    public void clickExit(View view) {
        askToClose();
    }


    @Override
    public void onBackPressed() {
        askToClose();
    }

    private void askToClose (){
        AlertDialog.Builder builder = new AlertDialog.Builder(RiskEvaluationQuestionActivity.this);
        builder.setMessage("Are you sure you want to quit?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

}