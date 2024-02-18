package com.observers.ecosentry_mobile.controllers.authentication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
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
    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN = 20;

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

        // Setup Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // Setup FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        // Setup the Google sign-in proccess
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuth(account.getIdToken());

            } catch (Exception e) {
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }
    // ======================
    // == Methods
    // ======================

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
                    firebaseAuth.signInWithEmailAndPassword(email,password)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                    String email = firebaseUser.getEmail();
                                    User user = new User();
                                    user.setEmail(email);
                                    Map<String, Object> data = new HashMap();
                                    data.put("user", user);
                                    ActivityHelper.moveToNextActivity(LoginActivity.this, DrawerActivity.class, data);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(LoginActivity.this, "Incorrect email or password", Toast.LENGTH_LONG).show();
                                }
                            });

                    // After login sucesss, get user object
                    // then passing to intent,
                    // then move to Drawer Activity

                    // FIXME: Fake User, return the user, add to the HashMap, pass to function
//                    User user = new User();
//                    Map<String, Object> data = new HashMap();
//                    data.put("user", user);
//                    ActivityHelper.moveToNextActivity(LoginActivity.this, DrawerActivity.class, data);
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
                // Retrieve sign-in intent and start sign-in activity
                Intent intent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(intent,RC_SIGN_IN);


                // FIXME: Fake User, return the user, add to the HashMap, pass to function
//                User user = new User();
//                user.setEmail("abc@gmail.com");
//                user.setUsername("Cái này tui test thôi nha");
//                Map<String, Object> data = new HashMap();
//                data.put("user", user);
//                ActivityHelper.moveToNextActivity(LoginActivity.this, DrawerActivity.class, data);
            }
        };
    }
    /**
    Authenticates the user with Firebase using the provided ID token.
     */
    public void firebaseAuth(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            String email = firebaseUser.getEmail();
                            String userID = firebaseUser.getUid();
                            String userName = firebaseUser.getDisplayName();
                            String photoUrl = firebaseUser.getPhotoUrl().toString();
                            User user = new User(email,userID,userName,photoUrl,"user");
                            Map<String, Object> data = new HashMap();
                            data.put("user",user);
                            // Add user to firestore
                            DocumentReference userRef = db.collection("users").document(userID);
                            userRef.get().addOnSuccessListener(command -> {
                                if (!command.exists()) {
                                    userRef.set(user);
                                }
                            });
                            ActivityHelper.moveToNextActivity(LoginActivity.this, DrawerActivity.class, data);
                        }
                    }
                });
    }
}
