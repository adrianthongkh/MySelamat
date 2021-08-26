package com.myselamat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.myselamat.model.Isolation;
import com.myselamat.model.IsolationSurvey;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;

public class HomeIsolationActivity extends AppCompatActivity {

    private IsolationSurvey isolationSurvey = new IsolationSurvey();
    private Isolation isolation = new Isolation();
    private FirebaseUser user;
    private FirebaseFirestore db;
    private boolean isInfected;

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
        setContentView(R.layout.activity_home_isolation);

        if (!getIntent().getBooleanExtra("status", false)) {
            Toast.makeText(this, "This survey is for infected individuals only.\nKindly update your status to proceed", Toast.LENGTH_SHORT).show();
            finish();
        }

        // TODO: get information from database
        /*db.collection("isolation")
                .whereEqualTo("docID", user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                            }
                        }
                    }
                });*/

        // Warning message
        displayWarningMessage();

        // Configure main toolbar
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
                Intent intent = new Intent(HomeIsolationActivity.this, HomeIsolationResultActivity.class);
                intent.putExtra("result", isolationSurvey);

                startActivity(intent);
                finish();
            }
        });

    }

    private void displayWarningMessage() {

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

    }
}