package com.observers.ecosentry_mobile.controllers.drawer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.observers.ecosentry_mobile.R;
import com.observers.ecosentry_mobile.controllers.authentication.LoginActivity;
import com.observers.ecosentry_mobile.controllers.drawer.fragments.dashboard.DashboardFragment;
import com.observers.ecosentry_mobile.controllers.drawer.fragments.home.HomeFragment;
import com.observers.ecosentry_mobile.controllers.drawer.fragments.profile.ProfileFragment;
import com.observers.ecosentry_mobile.models.user.User;
import com.observers.ecosentry_mobile.utils.ActivityHelper;
import com.observers.ecosentry_mobile.utils.shared.DataLocalManager;

import de.hdodenhof.circleimageview.CircleImageView;

public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // ================================
    // == Fields
    // ================================
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolBar;
    private NavigationView mNavigationView;
    private CircleImageView mCircleImageView;
    private TextView mUserName;

    /**
     * Fields for keep tabs in current fragment
     */
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_DASHBOARD = 1;
    private static final int FRAGMENT_PROFILE = 2;
    private int mCurrentFragment;

    // ================================
    // == Life Cycle
    // ================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        // Toolbar Setup
        setUpToolbar();

        // Drawer Setup
        setUpDrawer();

        // Drawer Navigation View Setup
        mCurrentFragment = FRAGMENT_HOME;
        setUpDrawerNavigation();

        // Setting Home Fragment By Default
        replaceFragment(new HomeFragment());
        mNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    // ================================
    // == Methods
    // ================================

    /**
     * Setup Toolbar
     */
    private void setUpToolbar() {
        mToolBar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_items_toolbar, menu);
        return true;
    }

    /**
     * Setup Drawer
     */
    private void setUpDrawer() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, mToolBar, R.string.drawer_nav_open, R.string.drawer_nav_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    /**
     * Setup Navigation Drawer
     */
    private void setUpDrawerNavigation() {
        // Setup Navigation View
        mNavigationView = findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        // Get image view and text view from header
        View headerView = mNavigationView.getHeaderView(0);
        mCircleImageView = headerView.findViewById(R.id.circleImageViewProfileDrawer);
        mUserName = headerView.findViewById(R.id.textViewUserNameDrawer);

        // Get data after login from SharedPreference
        User user = DataLocalManager.getUser();

        if (user != null) {
            Glide.with(DrawerActivity.this).load(user.getPhotoURL())
                    .into(mCircleImageView);

            mUserName.setText(user.getUsername());
        } else {
            ActivityHelper.moveToNextActivity(DrawerActivity.this, LoginActivity.class, null);
        }
    }

    /**
     * Handling the fragment clicked from the user
     *
     * @param item The selected item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // If the current is home, then don't do anything
            // else, replace content view with fragment_home.xml (inflated to HomeFragment.class)
            if (mCurrentFragment != FRAGMENT_HOME) {
                replaceFragment(new HomeFragment());
                mCurrentFragment = FRAGMENT_HOME;
            }
        } else if (id == R.id.nav_dashboard) {
            if (mCurrentFragment != FRAGMENT_DASHBOARD) {
                replaceFragment(new DashboardFragment());
                mCurrentFragment = FRAGMENT_DASHBOARD;
            }
        } else if (id == R.id.nav_profile) {
            if (mCurrentFragment != FRAGMENT_PROFILE) {
                replaceFragment(new ProfileFragment());
                mCurrentFragment = FRAGMENT_PROFILE;
            }
        }

        // Close drawer after choosing a fragment (item selected)
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Handling the back button pushed
     */
    @Override
    public void onBackPressed() {
        // If Drawer is open, push back and close it
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            // If drawer is close, just push back as usual
            super.onBackPressed();
        }
    }

    /**
     * Replace the content_frame.xml with the fragment class (based on fragment xml)
     *
     * @param fragment
     */
    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }
}