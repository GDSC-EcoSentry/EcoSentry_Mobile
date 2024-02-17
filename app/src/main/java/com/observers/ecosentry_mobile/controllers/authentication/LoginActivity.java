package com.observers.ecosentry_mobile.controllers.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.common.SignInButton;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.observers.ecosentry_mobile.R;

public class LoginActivity extends AppCompatActivity {

    // ================================
    // == Fields
    // ================================

    SignInButton signInButtonGoogle;
    TextInputEditText textInputEditTextEmail, textInputEditTextPassword;
    MaterialButton buttonNormalLogin;
    MaterialTextView textViewRegisterNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}