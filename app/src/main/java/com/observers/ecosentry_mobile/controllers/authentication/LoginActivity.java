package com.observers.ecosentry_mobile.controllers.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.observers.ecosentry_mobile.R;
import com.observers.ecosentry_mobile.controllers.drawer.DrawerActivity;
import com.observers.ecosentry_mobile.models.user.User;
import com.observers.ecosentry_mobile.utils.ActivityHelper;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class LoginActivity extends AppCompatActivity {

    // ================================
    // == Fields
    // ================================

    private SignInButton mButtonGoogleLogin;
    private TextInputEditText mTextInputEditTextEmailLogin, mTextInputEditTextPasswordLogin;
    private MaterialButton mButtonNormalLogin;
    private MaterialTextView mTextViewRegisterNowLogin;

    // ======================
    // == Life Cycle
    // ======================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Setup View from xml components id
        mButtonGoogleLogin = findViewById(R.id.buttonGoogleLogin);
        mTextInputEditTextEmailLogin = findViewById(R.id.textInputEditTextEmailLogin);
        mTextInputEditTextPasswordLogin = findViewById(R.id.textInputEditTextPasswordLogin);
        mTextViewRegisterNowLogin = findViewById(R.id.textViewRegisterNowLogin);
        mButtonNormalLogin = findViewById(R.id.buttonNormalLogin);

        // Setup Link to Register
        setUpLinkToRegisterActivity();

        // Setup listeners
        mButtonNormalLogin.setOnClickListener(setUpButtonNormalLogin());
        mButtonGoogleLogin.setOnClickListener(setUpButtonGoogleLogin());
    }

    // ======================
    // == Methods
    // ======================

    /**
     * A function to custom color the link and set up link listener
     */
    public void setUpLinkToRegisterActivity() {
        mTextViewRegisterNowLogin = findViewById(R.id.textViewRegisterNowLogin);
        SpannableString text = new SpannableString(mTextViewRegisterNowLogin.getText());
        int colorID = ContextCompat.getColor(this, R.color.green600);
        text.setSpan(new ForegroundColorSpan(colorID), 23, 35, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextViewRegisterNowLogin.setText(text);

        // Listener for register link
        mTextViewRegisterNowLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHelper.moveToNextActivity(LoginActivity.this, RegisterActivity.class, null);
            }
        });
    }


    /**
     * FIXME: Setup Normal Login using Firebase Authentication
     */
    public View.OnClickListener setUpButtonNormalLogin() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = String.valueOf(mTextInputEditTextEmailLogin.getText());
                String password = String.valueOf(mTextInputEditTextPasswordLogin.getText());

                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    // If success, do the firebase authentication here


                    // After login sucesss, get user object
                    // then passing to intent,
                    // then move to Drawer Activity

                    // FIXME: Fake User, return the user, add to the HashMap, pass to function
                    User user = new User();
                    user.setUsername("Vu Kim Duy");
                    user.setPhotoURL("https://firebasestorage.googleapis.com/v0/b/gdsc-ecosentry.appspot.com/o/images%2Fprofile%2FLrzDEPyt4aN6VFQYjTZEsjMBtsC3?alt=media&token=0b60e0e3-cbbf-4529-8efb-1de2f93555c5");
                    Map<String, Object> data = new HashMap();
                    data.put("user", user);
                    ActivityHelper.moveToNextActivity(LoginActivity.this, DrawerActivity.class, data);
                } else {
                    // If fail, showing toast
                    Toast.makeText(LoginActivity.this, "Please input email and password", Toast.LENGTH_LONG).show();
                }

                // Clear the text of input
                mTextInputEditTextEmailLogin.setText("");
                mTextInputEditTextPasswordLogin.setText("");
            }
        };
    }

    /**
     * FIXME: Setup Google Login
     */
    public View.OnClickListener setUpButtonGoogleLogin() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Put your google authentication code in here


                // FIXME: Fake User, return the user, add to the HashMap, pass to function
                User user = new User();
                user.setEmail("abc@gmail.com");
                user.setUsername("Cái này tui test thôi nha");
                Map<String, Object> data = new HashMap();
                data.put("user", user);
                ActivityHelper.moveToNextActivity(LoginActivity.this, DrawerActivity.class, data);
            }
        };
    }
}
