package com.blabla.structurize;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    public static String email;
    private TextView textViewEmail;
    private TextView textViewLogin;

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

    private void setImage() {
        Uri photoUrl = currentUser.getPhotoUrl();
        if(photoUrl != null) {
            Picasso.Builder builder = new Picasso.Builder(this);
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    exception.printStackTrace();
                    //Toast.makeText(MainActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("ds", "onImageLoadFailed: "+exception.getCause());
                }
            });
            builder.build().load(photoUrl).error(R.mipmap.ic_launcher).into(circleImageViewProfileImage);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            setImage();
    }

    private void initText() {
        textViewEmail = findViewById(R.id.content_settings_text_view_email);
        textViewLogin = findViewById(R.id.content_settings_text_view_login);

        currentUser = mAuth.getCurrentUser();

        textViewEmail.setText(currentUser.getEmail());
        textViewLogin.setText(currentUser.getDisplayName());

        email = textViewEmail.getText().toString();

    }

    private void initButton() {
        Button buttonLogOut = findViewById(R.id.content_settings_button_logout);

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
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_PROFILE_IMAGE);
            }
        });

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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

    public void buttonsSettingsHandler(View view) {
        int id = view.getId();
        Class aClass = null;
        switch (id){
            case R.id.content_settings_button_change_data:
                aClass = ChangingData.class;
                break;
            case R.id.content_settings_button_common:
                break;
            case R.id.content_settings_button_notifications:
                break;
            case R.id.content_settings_button_reset:
                break;
            case R.id.content_settings_button_about_us:
                break;
            case R.id.content_settings_button_write_author:
                break;
            case R.id.content_settings_button_logout:
                break;
        }

        Intent intent = new Intent(this, aClass);
        startActivity(intent);
    }
}
