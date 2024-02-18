package com.observers.ecosentry_mobile.controllers.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Application;
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
import com.observers.ecosentry_mobile.utils.shared.DataLocalManager;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class LoginActivity extends AppCompatActivity {

    // ================================
    // == Fields
    // ================================
    private SignInButton mButtonGoogleLogin;
    private TextInputEditText mTextInputEditTextEmailLogin,
            mTextInputEditTextPasswordLogin;
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

    /**
     * @formatter:off
     *
     * FIXME: Reasons I use Shared Preference in LoginActivity
     * Scenarios if I don't use Shared Preference
     *      If User logged in, swipe back to LoginActivity from DrawerActivity
     *          => login again
     *      If in STOP State, go back to RUNNING
     *          => login again
     *      If in PAUSE State, go back to RUNNING
     *          => login again
     *      If User logged in, next time go to the app
     *          => login again
     *      If edit data on ProfilePage
     *          => Lose User object since using Intent
     *          get destroyed after reaching DrawerActivity
     *
     * Similarity to Java Web
     *      Passing user through Intent
     *          => like RequestScope
     *      Passing user through SharedPreference
     *          => like Context Scope
     *
     * How to delete Shared Preference
     *      Delete the application
     *      Go to setting/ Choose our application/ Delete cached data, user data
     *
     * Checkout the keyword will you want to understand:
     *      SharedPreference
     *      Activity Lifecycle, Fragment Lifecycle, Application Lifecycle
     *      Singleton Design Pattern
     *
     * @formatter:on
     */

    /**
     * PAUSE, STOP life cycle  will check user within shared preference
     */
    @Override
    protected void onStart() {
        super.onStart();

        // If user in local storage, retrieve form it
        User user = DataLocalManager.getUser();
        if (user != null) {
            ActivityHelper.sendDataToNextActivity("user", user, LoginActivity.this, DrawerActivity.class);
        }
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
                    // FIXME: If success, do the firebase authentication here

                    // FIXME: After login successful, do the following
                    // 1. Set to the preference: setUser(User user) at DataLocalManager
                    //      1.1 If already set, go next
                    //      1.2 If not set, then set User object
                    // 2. Move to DrawerActivity

                    // Fake Data (This object is returned from Firestore)
                    User user = new User();
                    user.setUsername("Vu Kim Duy");
                    user.setEmail(email);
                    user.setPhotoURL("https://firebasestorage.googleapis.com/v0/b/gdsc-ecosentry.appspot.com/o/images%2Fprofile%2FLrzDEPyt4aN6VFQYjTZEsjMBtsC3?alt=media&token=0b60e0e3-cbbf-4529-8efb-1de2f93555c5");

                    // Save data to local preference
                    if (DataLocalManager.getUser() == null) {
                        DataLocalManager.setUser(user);
                    }

                    // Go to DrawerActivity
                    ActivityHelper.sendDataToNextActivity("user", user, LoginActivity.this, DrawerActivity.class);
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
                // FIXME: If success, do the firebase authentication here


                // FIXME: After login successful, do the following
                // 1. Set to the preference: setUser(User user) at DataLocalManager
                //      1.1 If already set, go next
                //      1.2 If not set, then set User object
                // 2. Move to DrawerActivity

                // Fake Data (This object is returned from Firestore)
                User user = new User();
                user.setUsername("Vu Kim Duy");
                user.setEmail("abc@gmail.com");
                user.setPhotoURL("https://firebasestorage.googleapis.com/v0/b/gdsc-ecosentry.appspot.com/o/images%2Fprofile%2FLrzDEPyt4aN6VFQYjTZEsjMBtsC3?alt=media&token=0b60e0e3-cbbf-4529-8efb-1de2f93555c5");

                // Save data to local preference
                if (DataLocalManager.getUser() == null) {
                    DataLocalManager.setUser(user);
                }

                // Go to DrawerActivity
                ActivityHelper.sendDataToNextActivity("user", user, LoginActivity.this, DrawerActivity.class);
            }
        };
    }
}
