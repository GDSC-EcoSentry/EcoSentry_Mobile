package com.observers.ecosentry_mobile.controllers.authentication;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.SignInButton;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.observers.ecosentry_mobile.R;
import com.observers.ecosentry_mobile.controllers.drawer.DrawerActivity;
import com.observers.ecosentry_mobile.models.user.User;
import com.observers.ecosentry_mobile.utils.ActivityHelper;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    // ================================
    // == Fields
    // ================================

    private SignInButton mButtonGoogleRegister;
    private TextInputEditText mTextInputEditTextEmailRegister,
            mTextInputEditTextPasswordRegister,
            mTextInputEditTextConfirmPasswordRegister,
            mTextInputEditTextUserNameRegister;
    private MaterialButton mButtonNormalRegister;
    private MaterialTextView mTextViewLoginNowRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Setup View from xml components id
        mButtonGoogleRegister = findViewById(R.id.buttonGoogleRegister);
        mTextInputEditTextPasswordRegister = findViewById(R.id.textInputEditTextPasswordRegister);
        mTextInputEditTextConfirmPasswordRegister = findViewById(R.id.textInputEditTextConfirmPasswordRegister);
        mTextViewLoginNowRegister = findViewById(R.id.textViewLoginNowRegister);
        mTextInputEditTextEmailRegister = findViewById(R.id.textInputEditTextEmailRegister);
        mTextInputEditTextUserNameRegister = findViewById(R.id.textInputEditUserNameRegister);
        mButtonNormalRegister = findViewById(R.id.buttonNormalRegister);

        // Set up link ack to login activity
        setUpLinkToLoginActivity();

        // Set Listeners
        mButtonNormalRegister.setOnClickListener(setUpButtonNormalRegister());
        mButtonGoogleRegister.setOnClickListener(setUpButtonGoogleRegister());
    }

    // ======================
    // == Methods
    // ======================

    /**
     * A function to custom color the link and set up link to login
     */
    public void setUpLinkToLoginActivity() {
        mTextViewLoginNowRegister = findViewById(R.id.textViewLoginNowRegister);
        SpannableString text = new SpannableString(mTextViewLoginNowRegister.getText());
        int colorID = ContextCompat.getColor(this, R.color.green600);
        text.setSpan(new ForegroundColorSpan(colorID), 25, 32, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextViewLoginNowRegister.setText(text);

        // Listener for register link
        mTextViewLoginNowRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHelper.moveToNextActivity(RegisterActivity.this, LoginActivity.class, null);
            }
        });
    }


    /**
     * FIXME: Setup Normal Login using Firebase Authentication
     */
    public View.OnClickListener setUpButtonNormalRegister() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = String.valueOf(mTextInputEditTextEmailRegister.getText());
                String password = String.valueOf(mTextInputEditTextPasswordRegister.getText());
                String confirmedPassword = String.valueOf(mTextInputEditTextConfirmPasswordRegister.getText());
                String username = String.valueOf(mTextInputEditTextUserNameRegister);

                if (!TextUtils.isEmpty(email)
                        && !TextUtils.isEmpty(password)
                        && !TextUtils.isEmpty(confirmedPassword)
                        && !TextUtils.isEmpty(username)) {

                    // Check if reconfirm is matched
                    // FIXME: Add more logic if u have better idea
                    if (!password.equals(confirmedPassword)) {
                        Toast.makeText(RegisterActivity.this, "Password is not matched. Please try again", Toast.LENGTH_LONG).show();
                    } else {
                        // Register to the firebase here

                        // After register, go straight to DrawerActivity with registered user
                        // FIXME: Fake User
                        User user = new User();
                        Map<String, Object> data = new HashMap();
                        data.put("user", user);
                        ActivityHelper.moveToNextActivity(RegisterActivity.this, DrawerActivity.class, data);
                    }
                } else {
                    // If fail, showing toast
                    Toast.makeText(RegisterActivity.this, "Please enter all fields.", Toast.LENGTH_LONG).show();
                }

                // Clear the text of input;
                mTextInputEditTextEmailRegister.setText("");
                mTextInputEditTextPasswordRegister.setText("");
                mTextInputEditTextConfirmPasswordRegister.setText("");
                mTextInputEditTextUserNameRegister.setText("");
            }
        };
    }

    /**
     * FIXME: Setup Google Login
     */
    public View.OnClickListener setUpButtonGoogleRegister() {
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
                ActivityHelper.moveToNextActivity(RegisterActivity.this, DrawerActivity.class, data);
            }
        };
    }
}
