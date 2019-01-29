package com.blabla.structurize;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private static final int REQUEST_PROFILE_IMAGE = 1;
    private CircleImageView circleImageViewProfileImage;
    private NavigationView navigationView;
    public static String email;
    private EditText editTextEmail;
    private EditText editTextLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mAuth = FirebaseAuth.getInstance();
        initComponents();

    }

    private void initComponents() {
        initToolbar();
        initText();
        initButton();
    }

    private void initText() {
        editTextEmail = findViewById(R.id.content_settings_edit_text_email);
        editTextLogin = findViewById(R.id.content_settings_edit_text_login);

        currentUser = mAuth.getCurrentUser();

        editTextEmail.setText(currentUser.getEmail());
        editTextLogin.setText(currentUser.getDisplayName());

        email = editTextEmail.getText().toString();

    }

    private void initButton() {
        Button buttonLogOut = findViewById(R.id.content_settings_button_logout);
        Button buttonChangePassword =findViewById(R.id.content_settings_button_change_password);
        Button buttonChangeEmail = findViewById(R.id.content_settings_button_change_email);
        final Button buttonSaveData =findViewById(R.id.content_settings_button_save_data);


        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newWind = new Intent(SettingsActivity.this,ChangePasswordActivity.class);
                startActivity(newWind);
            }
        });

        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newWindExit = new Intent(SettingsActivity.this,CheckActivity.class);
                FirebaseAuth.getInstance().signOut();
                startActivity(newWindExit);
                finishAffinity();
            }
        });

        circleImageViewProfileImage = findViewById(R.id.content_settings_profile_image);
        circleImageViewProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_PROFILE_IMAGE);
            }
        });

        buttonChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        editTextLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                buttonSaveData.setEnabled(false);
            }
        });

        buttonSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = mAuth.getCurrentUser();
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(editTextLogin.getText().toString())
                        .build();
                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("login_changed", "Login changed succesful");
                                    buttonSaveData.setEnabled(true);
                                }
                            }
                        });
            }
        });

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_PROFILE_IMAGE){
                final Uri uri = data.getData();
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setPhotoUri(uri)
                        .build();

                currentUser.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Picasso.get().load(uri).resize(100, 100).into(circleImageViewProfileImage);
                                }
                            }
                        });
            }
        }
    }
}
