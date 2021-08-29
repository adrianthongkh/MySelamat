package com.myselamat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.myselamat.database.HomeIsolationDatabase;
import com.myselamat.model.Isolation;
import com.myselamat.model.IsolationSurvey;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class HomeIsolationActivity extends AppCompatActivity {

    private IsolationSurvey isolationSurvey = new IsolationSurvey();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private Isolation isolation = new Isolation();
    private boolean isCompleted;

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Please submit the survey before going back.");
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // do nothing
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getIsolation();

        // Configure main toolbar
        setContentView(R.layout.activity_home_isolation);
        View toolbar = findViewById(R.id.main_toolbar);
        toolbar.findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Pass object for control to fragment
        getIntent().putExtra("survey", isolationSurvey);

        Button btn_submit = findViewById(R.id.btn_hi_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag = 0;
                String temp = isolationSurvey.getMessageFromScore();

                if (!isolationSurvey.isSeverity())
                    flag = 1;

                Intent intent = new Intent(HomeIsolationActivity.this, HomeIsolationResultActivity.class);

                if (isolation.getDay_count() == 13) {
                    // remove from database --> day count already 13
                    HomeIsolationDatabase.deleteIsolation(isolation.getDocId());

                    // put survey result as completed
                    intent.putExtra("completed", true);

                } else {
                    // Update database
                    HomeIsolationDatabase.updateIsolation(isolation, flag);

                }
                Toast.makeText(HomeIsolationActivity.this, "Assessment submitted", Toast.LENGTH_SHORT).show();

                // put survey result as serializable
                intent.putExtra("result", isolationSurvey);

                startActivity(intent);
                finish();
            }
        });

    }

    private void displayCompletedMessage() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("It seems like you have completed today's survey.\nPlease come back tomorrow.");
        builder.setPositiveButton("I understand.", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void displayWarningMessage(int day_count) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);

        if (day_count == 0)
            builder.setMessage(isolationSurvey.getWarning() + "\nPlease complete the assessment every 24 hours.");
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

    }

    private void getIsolation () {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("isolation")
                .whereEqualTo("uid", user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                isolation.setUid(document.getString("uid"));
                                isolation.setDocId(document.getId());
                                isolation.setDay_count(document.getLong("day_count").intValue());
                                isolation.setStartDate(document.getDate("startDate"));
                                isolation.setSeverityStatus(document.getBoolean("severityStatus"));
                                isCompleted = isCompleted();

                                if (isCompleted) {
                                    displayCompletedMessage();
                                } else {
                                    displayWarningMessage(isolation.getDay_count());
                                }
                                return;
                            }

                            // Create new isolation
                            isolation = new Isolation(user.getUid());
                            HomeIsolationDatabase.addIsolation(isolation);
                            displayWarningMessage(0);
                        }

                    }
                });
    }


    private boolean isCompleted() {

        Date currentDate = Calendar.getInstance().getTime();

        long current = currentDate.getTime();
        long start = isolation.getStartDate().getTime();

        long diff = current - start;
        long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1;

        if (isolation.getDay_count() == days)
            return true;

        return false;
    }
}