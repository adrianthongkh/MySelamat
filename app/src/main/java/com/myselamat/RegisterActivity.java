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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    ImageView logo;
    TextView tvsignup;
    EditText rusername, remail, rpassword, rphonenum;
    Button btnregister;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        logo = findViewById(R.id.Logo);
        tvsignup = findViewById(R.id.tvSignup);
        rusername = findViewById(R.id.etRegisterUsername);
        remail = findViewById(R.id.etsingupemail);
        rpassword = findViewById(R.id.etRegisterPw);
        rphonenum = findViewById(R.id.etphone);
        btnregister = findViewById(R.id.btnregister);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String username = rusername.getText().toString();
                final String email = remail.getText().toString().trim();
                String password = rpassword.getText().toString().trim();
                final String phone    = rphonenum.getText().toString();

                if(TextUtils.isEmpty(username)){
                    remail.setError("Username is Required.");
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    remail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    rpassword.setError("Password is Required.");
                    return;
                }

                if(password.length() < 6){
                    rpassword.setError("Password Must be >= 6 Characters");
                    return;
                }

                if(TextUtils.isEmpty(phone)){
                    remail.setError("Phone Number is Required.");
                    return;
                }

                if(phone.length() < 10){
                    remail.setError("Enter a Valid Phone Number.");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        userID = fAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = fStore.collection("users").document(userID);
                        Map<String,Object> user = new HashMap<>();
                        user.put("Username",username);
                        user.put("Email",email);
                        user.put("Password",password);
                        user.put("Phone",phone);
                        user.put("status", false);
                        documentReference.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(RegisterActivity.this, "User Created.", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RegisterActivity.this, "Failed to Create.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));

                    }
                });
            }
        });

    }
}