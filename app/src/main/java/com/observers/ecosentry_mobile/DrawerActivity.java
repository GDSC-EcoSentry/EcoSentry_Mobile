package com.observers.ecosentry_mobile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;

public class DrawerActivity extends AppCompatActivity {

    // ================================
    // == Fields
    // ================================
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;

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
    }

    // ================================
    // == Methods
    // ================================

    /**
     * Setup Toolbar
     */
    private void setUpToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    /**
     * Setup Drawer
     */
    private void setUpDrawer() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, mToolbar, R.string.drawer_nav_open, R.string.drawer_nav_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }
}