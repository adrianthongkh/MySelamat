package com.myselamat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {

    Button btnlogin, btnregister;
    EditText lemail, lpassword;
    ImageView logo;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnlogin = findViewById(R.id.btnlogin);
        btnregister = findViewById(R.id.btnRegisterPage);

        lemail = findViewById(R.id.etloginEmail);
        lpassword= findViewById(R.id.etloginPassword);

        fAuth = FirebaseAuth.getInstance();

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = lemail.getText().toString().trim();
                String password = lpassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    lemail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    lpassword.setError("Password is Required.");
                    return;
                }

                if(password.length() < 6){
                    lpassword.setError("Password Must be >= 6 Characters");
                    return;
                }



                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
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

       /*"Night Bae, Mua" "Tmr call me arouond 8am like that <3"*/

    }
}