package com.blabla.structurize;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangingData extends AppCompatActivity {

    private EditText editTextLogin;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private Button buttonChangeLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changing_data);
        mAuth = FirebaseAuth.getInstance();
        initComponents();
    }

    private void initComponents() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initText();
        initButtons();
    }

    private void initText() {
        editTextLogin = findViewById(R.id.content_changing_data_edit_text_login);

        currentUser = mAuth.getCurrentUser();
        editTextLogin.setText(currentUser.getDisplayName());

    }

    private void initButtons() {
        Button buttonChangePassword =findViewById(R.id.content_changing_data_button_change_password);
        Button buttonChangeEmail = findViewById(R.id.content_changing_data_button_change_email);
        buttonChangeLogin = findViewById(R.id.content_changing_data_button_change_login);
        buttonChangeLogin.setEnabled(false);

        buttonChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChangingData.this, "---------------", Toast.LENGTH_SHORT).show();
                Intent newWind = new Intent(ChangingData.this,ChangeEmailActivity.class);
                startActivity(newWind);
            }
        });

        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newWind = new Intent(ChangingData.this,ChangePasswordActivity.class);
                startActivity(newWind);
            }
        });

        buttonChangeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });

        editTextLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                buttonChangeLogin.setEnabled(true);
                Toast.makeText(ChangingData.this, "---------", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

}
