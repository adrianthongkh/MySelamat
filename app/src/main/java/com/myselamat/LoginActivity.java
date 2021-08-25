package com.myselamat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.myselamat.R;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth fAuth;
    private SharedPreferences sharedPreferences;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);

        // first time user
        String exist_email = sharedPreferences.getString("email", null);
        String exist_pass = sharedPreferences.getString("password", null);

        // first time user -> display login page
        if (exist_email == null && exist_pass == null) {

            setContentView(R.layout.activity_login);

            Button btnlogin, btnregister;
            EditText lemail, lpassword;

            btnlogin = findViewById(R.id.btnlogin);
            btnregister = findViewById(R.id.btnRegisterPage);

            lemail = findViewById(R.id.etloginEmail);
            lpassword = findViewById(R.id.etloginPassword);

            btnlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String email = lemail.getText().toString().trim();
                    String password = lpassword.getText().toString().trim();

                    if (TextUtils.isEmpty(email)) {
                        lemail.setError("Email is Required.");
                        return;
                    }

                    if (TextUtils.isEmpty(password)) {
                        lpassword.setError("Password is Required.");
                        return;
                    }

                    if (password.length() < 6) {
                        lpassword.setError("Password Must be >= 6 Characters");
                        return;
                    }


                    fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();

                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                editor.putString("email", email);
                                editor.putString("password", password);

                                editor.commit();

                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            } else {
                                Toast.makeText(LoginActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }
            });

            btnregister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                }
            });

        } else {

            fAuth.signInWithEmailAndPassword(exist_email, exist_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            });

        }

    }

}