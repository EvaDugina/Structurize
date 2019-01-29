package com.blabla.structurize;

import android.os.Bundle;
import android.support.annotation.NonNull;
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

public class ChangePasswordActivity extends AppCompatActivity {


    private EditText editTextPreviousPassword;
    private EditText editTextNewPassword;
    private TextInputLayout textInputLayoutPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initComponents();
    }

    private void initComponents() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initText();
        initButtons();
    }

    private void initText() {
        editTextPreviousPassword = findViewById(R.id.content_change_password_edit_text_previous_password);
        editTextNewPassword = findViewById(R.id.content_change_password_edit_text_new_password);
        textInputLayoutPassword = findViewById(R.id.content_change_password_text_input_layout_new_password);
    }

    private void initButtons() {
        Button buttonChange = findViewById(R.id.content_change_password_button_change);

        buttonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = editTextNewPassword.getText().toString();
                String email = SettingsActivity.email;

                if ((!TextUtil.validatePassword(newPassword) && !newPassword.equals(""))) {
                    Toast.makeText(ChangePasswordActivity.this, "Fill the pools correctly.", Toast.LENGTH_SHORT).show();
                    return;
                }
                changeUserInformation(email,newPassword);
            }
        });
    }

    private void changeUserInformation(final String email, final String password) {

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        AuthCredential credential = EmailAuthProvider
                .getCredential(email,editTextPreviousPassword.getText().toString() );

        if (!password.equals("") && TextUtil.validatePassword(password)) {
            user.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                user.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d("SuccesfulChangingP", "Password updated");
                                        } else {
                                            Log.d("UnsuccesfulChangingP", "Error password not updated");
                                        }
                                    }
                                });
                            }
                        }
                    });
        }


    }


}
