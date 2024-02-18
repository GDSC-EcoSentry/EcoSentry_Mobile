package com.observers.ecosentry_mobile.controllers.drawer.fragments.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.observers.ecosentry_mobile.R;
import com.observers.ecosentry_mobile.controllers.authentication.LoginActivity;
import com.observers.ecosentry_mobile.models.user.User;
import com.observers.ecosentry_mobile.utils.ActivityHelper;
import com.observers.ecosentry_mobile.utils.shared.DataLocalManager;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    // ======================
    // == Fields
    // ======================
    private CircleImageView mCircleImageViewProfileProfile;
    private TextView mTextViewRoleProfile;
    private TextInputEditText mTextInputEditTextUserNameProfile,
            mTextInputEditTextFirstNameProfile,
            mTextInputEditTextLastNameProfile,
            mTextInputEditTextEmailProfile,
            mTextInputEditTextPhoneProfile,
            mTextInputEditTextAddressProfile;
    private MaterialButton mButtonSaveProfile;

    // ======================
    // == Life Cycle
    // ======================
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Setup Views
        mCircleImageViewProfileProfile = view.findViewById(R.id.circleImageViewProfileProfile);
        mTextViewRoleProfile = view.findViewById(R.id.textViewRoleProfile);
        mTextInputEditTextUserNameProfile = view.findViewById(R.id.textInputEditTextUserNameProfile);
        mTextInputEditTextFirstNameProfile = view.findViewById(R.id.textInputEditTextFirstNameProfile);
        mTextInputEditTextLastNameProfile = view.findViewById(R.id.textInputEditTextLastNameProfile);
        mTextInputEditTextEmailProfile = view.findViewById(R.id.textInputEditTextEmailProfile);
        mTextInputEditTextPhoneProfile = view.findViewById(R.id.textInputEditTextPhoneProfile);
        mTextInputEditTextAddressProfile = view.findViewById(R.id.textInputEditTextAddressProfile);
        mButtonSaveProfile = view.findViewById(R.id.buttonSaveProfile);

        // Set current User to views (get from shared preference)
        User user = DataLocalManager.getUser();
        if (user != null) {
            setCurrentUserOnAppToViews(user);
        } else {
            ActivityHelper.moveToNextActivity(view.getContext(), LoginActivity.class, null);
        }

        // Setup Listeners
        mButtonSaveProfile.setOnClickListener(saveEditedUser());
    }

    // ======================
    // == Methods
    // ======================

    /**
     * Attach current user infor to corresponding views
     *
     * @param user: user object in store preference
     */
    public void setCurrentUserOnAppToViews(User user) {

        // Using glide to load image from url
        Glide.with(ProfileFragment.this).load(user.getPhotoURL())
                .into(mCircleImageViewProfileProfile);

        // Add content from User object
        mTextViewRoleProfile.setText(user.getRole());
        mTextInputEditTextUserNameProfile.setText(user.getUsername());
        mTextInputEditTextFirstNameProfile.setText(user.getFirstName());
        mTextInputEditTextLastNameProfile.setText(user.getLastName());
        mTextInputEditTextEmailProfile.setText(user.getEmail());
        mTextInputEditTextPhoneProfile.setText(user.getPhone());
        mTextInputEditTextAddressProfile.setText(user.getAddress());
    }

    /**
     * FIXME: Save User object to Firebase
     *
     * @return
     */
    public View.OnClickListener saveEditedUser() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get user from views
                User user = new User();
                mTextInputEditTextUserNameProfile.getText();
                mTextInputEditTextFirstNameProfile.getText();
                mTextInputEditTextLastNameProfile.getText();
                mTextInputEditTextPhoneProfile.getText();
                mTextInputEditTextAddressProfile.getText();

                // Set new User object to shared Preference
                DataLocalManager.setUser(user);

                // FIXME: Update new User to Firebase

                // Notify User to save sucessfully
                Toast.makeText(v.getContext(), "Save User Sucessfully", Toast.LENGTH_LONG).show();
            }
        };
    }
}
