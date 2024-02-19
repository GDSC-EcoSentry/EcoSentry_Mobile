package com.observers.ecosentry_mobile.controllers.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.observers.ecosentry_mobile.R;
import com.observers.ecosentry_mobile.controllers.drawer.DrawerActivity;
import com.observers.ecosentry_mobile.models.user.User;
import com.observers.ecosentry_mobile.utils.ActivityHelper;
import com.observers.ecosentry_mobile.utils.shared.DataLocalManager;

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
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private FirebaseFirestore db;
    private GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN = 20;
    private User user;

    // ================================
    // == Life Cycle
    // ================================
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

        // Set up link to login activity
        setUpLinkToLoginActivity();

        // Set Listeners
        mButtonNormalRegister.setOnClickListener(setUpButtonNormalRegister());
        mButtonGoogleRegister.setOnClickListener(setUpButtonGoogleRegister());

        // Setup Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        // Setup Firebase Firestore
        db = FirebaseFirestore.getInstance();

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

    /**
     * A function to custom color the link and set up link to login
     */
    public void setUpLinkToLoginActivity() {

        // Custom Link
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
     * FIXME: Setup Register using Firebase Authentication (Normarl Register)
     */
    public View.OnClickListener setUpButtonNormalRegister() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = String.valueOf(mTextInputEditTextEmailRegister.getText());
                String password = String.valueOf(mTextInputEditTextPasswordRegister.getText());
                String confirmedPassword = String.valueOf(mTextInputEditTextConfirmPasswordRegister.getText());
                String username = String.valueOf(mTextInputEditTextUserNameRegister.getText());

                // FIXME: Add more logic if u have better idea
                if (!TextUtils.isEmpty(email)
                        && !TextUtils.isEmpty(password)
                        && !TextUtils.isEmpty(confirmedPassword)
                        && !TextUtils.isEmpty(username)) {

                    // Check if reconfirm is matched
                    if (!password.equals(confirmedPassword)) {
                        Toast.makeText(RegisterActivity.this, "Password is not matched. Please try again", Toast.LENGTH_LONG).show();
                    } else {
                        // FIXME: Register to the firebase here
                        firebaseAuth.createUserWithEmailAndPassword(email,password).
                                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    currentUser = firebaseAuth.getCurrentUser();
                                    String userID = currentUser.getUid();
                                    User user = new User(email,userID,username,"user");
                                    // Add user to firestore
                                    DocumentReference userRef = db.collection("users").document(userID);
                                    userRef.get().addOnSuccessListener(command -> {
                                        if (!command.exists()) {
                                            userRef.set(user);
                                        }
                                    });
                                    Toast.makeText(RegisterActivity.this, "Register Successfully", Toast.LENGTH_LONG).show();
                                    ActivityHelper.moveToNextActivity(RegisterActivity.this, LoginActivity.class, null);
                                }
                                else if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    Toast.makeText(RegisterActivity.this, "Email already exists!", Toast.LENGTH_LONG).show();
                                }
                                else {
                                    Toast.makeText(RegisterActivity.this, "Registration fail! " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                } else {
                    // If fail, showing toast
                    Toast.makeText(RegisterActivity.this, "Please enter all fields", Toast.LENGTH_LONG).show();
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
     * FIXME: Setup Register using Firebase Authentication (OAuth)
     */
    public View.OnClickListener setUpButtonGoogleRegister() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // FIXME: Put your google authentication code in here
                // Retrieve sign-in intent and start sign-in activity
                Intent intent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(intent,RC_SIGN_IN);
            }
        };
    }

    public void firebaseAuth(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            String uid = firebaseUser.getUid();
                            // Check user whether exists in Firestore or not
                            DocumentReference userRef = db.collection("users").document(uid);
                            userRef.get().addOnSuccessListener(documentSnapshot -> {
                                if (documentSnapshot.exists()) {
                                    user = documentSnapshot.toObject(User.class);
                                } else {
                                    String email = firebaseUser.getEmail();
                                    String userID = firebaseUser.getUid();
                                    String userName = firebaseUser.getDisplayName();
                                    String photoUrl = firebaseUser.getPhotoUrl().toString();
                                    User user = new User(email,userID,userName,photoUrl,"user");
                                }
                                // Save data to local preference
                                if (DataLocalManager.getUser() == null) {
                                    DataLocalManager.setUser(user);
                                }
                                // Go to DrawerActivity
                                ActivityHelper.sendDataToNextActivity("user", user, RegisterActivity.this, DrawerActivity.class);
                            });
                        }
                    }
                });
    }
}
