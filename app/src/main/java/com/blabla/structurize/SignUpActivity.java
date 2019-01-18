package com.blabla.structurize;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUpActivity extends AppCompatActivity {
    private EditText editTextLogin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private FirebaseAuth mAuth;
    private TextView textFailure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initComponents();
    }

    private void initComponents() {
        mAuth = FirebaseAuth.getInstance();
        textFailure = findViewById(R.id.content_sign_up_text_view_text_failure);
        initText();
        initButtons();
    }

    private void createUser(String email,String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(editTextLogin.toString())
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d("===============", "User profile updated.123" );
                                            }
                                        }
                                    });
                            Toast.makeText(SignUpActivity.this, "SUCCESFUL"+user, Toast.LENGTH_SHORT).show();
                            Intent intentSignUp = new Intent(SignUpActivity.this,CheckActivity.class);
                            startActivity(intentSignUp);
                            finish();

                        }
                    }
                })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(e instanceof com.google.firebase.auth.FirebaseAuthUserCollisionException){
                    textFailure.setText("Такой пользователь уже существует");
                }
            }
        });

    }

    /*private void setNameUser(){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(editTextLogin.toString())
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("===============", "User profile updated.123" );
                        }
                    }
                });
    }*/

    private void initButtons() {
        Button buttonToLogIn = findViewById(R.id.content_sign_up_button_sign_up);

        buttonToLogIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String login = editTextLogin.getText().toString();
                String password = editTextPassword.getText().toString();

                if (!TextUtil.validateEmail(email) && !TextUtil.validatePassword(password)) {
                    Toast.makeText(SignUpActivity.this, "Fill the pools correctly.", Toast.LENGTH_SHORT).show();
                    return ;
                }

                createUser(email,password);
            }
        });

    }

    private void initText() {
        editTextLogin = findViewById(R.id.content_sign_up_edit_text_login);
        editTextEmail = findViewById(R.id.content_sign_up_edit_text_email);
        editTextPassword = findViewById(R.id.content_sign_up_edit_text_password);

        textInputLayoutEmail = findViewById(R.id.content_sign_up_text_input_layout_email);
        textInputLayoutPassword = findViewById(R.id.content_sign_up_text_input_layout_password);


        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String email = editTextEmail.getText().toString();
                if (!TextUtil.validateEmail(email)) {
                    textInputLayoutEmail.setError(getString(R.string.login_activity_string_error_email));
                    textInputLayoutEmail.setErrorEnabled(true);
                } else
                    textInputLayoutEmail.setErrorEnabled(false);
            }
        });

        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String password = editTextPassword.getText().toString();
                if(!TextUtil.validatePassword(password)){
                    textInputLayoutPassword.setError(getString(R.string.login_activity_string_error_password));
                    textInputLayoutPassword.setErrorEnabled(true);
                }
                else
                    textInputLayoutPassword.setErrorEnabled(false);
            }
        });

    }


}
