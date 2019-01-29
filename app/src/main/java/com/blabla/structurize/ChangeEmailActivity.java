package com.blabla.structurize;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangeEmailActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextInputLayout textInputLayoutEmail;
    private String newEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);
        initComponent();

    }

    private void initComponent() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initText();
        initButtons();

    }

    private void initText() {
        editTextPassword = findViewById(R.id.content_change_email_edit_text_password);
        editTextEmail = findViewById(R.id.content_change_email_edit_text_new_email);
        textInputLayoutEmail = findViewById(R.id.content_change_email_text_input_layout_new_email);

    }

    private void initButtons() {
        Button buttonChange = findViewById(R.id.content_change_email_button_change);

        buttonChange.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                newEmail = editTextEmail.getText().toString();
                String email = SettingsActivity.email;
                String password = editTextPassword.getText().toString();

                if ((!TextUtil.validateEmail(newEmail) && !newEmail.equals(""))) {
                    Toast.makeText(ChangeEmailActivity.this, "Fill the pools correctly.", Toast.LENGTH_SHORT).show();
                    return;
                }
                changeUserInformation(email, password);
            }
        });
    }

    private void changeUserInformation(final String email, final String password) {

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        AuthCredential credential = EmailAuthProvider
                .getCredential(email,password);

        if (!newEmail.equals("") && TextUtil.validateEmail(newEmail)) {
            user.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                user.updateEmail(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d("SuccesfulChangingEmail", "Email updated");
                                        } else {
                                            Log.d("UnsuccesfulChangingE", "Error email not updated");
                                        }
                                    }
                                });
                            }
                        }
                    });
        }


    }

}
