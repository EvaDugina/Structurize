package com.blabla.structurize;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    private static final int REQUEST_PROFILE_IMAGE = 1;
    private CircleImageView circleImageViewProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initComponents();
    }

    private void initComponents() {
        initToolbar();
        initButton();
    }

    private void initButton() {
        Button buttonLogOut = findViewById(R.id.content_settings_button_logout);
        Button buttonChangeData =findViewById(R.id.content_settings_button_change_data);


        buttonChangeData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newWind = new Intent(SettingsActivity.this,ChangeDataActivity.class);
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
