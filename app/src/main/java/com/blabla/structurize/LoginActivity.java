package com.blabla.structurize;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private FirebaseAuth mAuth;
    private TextView textViewTextFailure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponents();
    }

    private void initComponents() {
        mAuth = FirebaseAuth.getInstance();
        initText();
        initButton();

    }

    private void initButton() {
        Button buttonLogin = findViewById(R.id.content_login_button_login);
        Button buttonSignUp = findViewById(R.id.content_login_button_to_sign_up);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newWindSignUp = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(newWindSignUp);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                signIn(email,password);
            }
        });
    }

    private void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intentMain = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intentMain);
                            finish();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        /*if(e instanceof com.google.firebase.auth.FirebaseAuthInvalidCredentialsException){
                            textViewTextFailure.setText("Данные введены неверно");
                        }*/
                        /*if(e instanceof com.google.firebase.auth.FirebaseAuthInvalidUserException){*/
                        //}

                        textViewTextFailure.setText("Данные введены неверно");
                    }
                });

    }

    private void initText() {
        editTextEmail = findViewById(R.id.content_login_edit_text_email);
        editTextPassword = findViewById(R.id.content_login_edit_text_password);
        textViewTextFailure = findViewById(R.id.content_login_text_view_text_failure);
    }

}
