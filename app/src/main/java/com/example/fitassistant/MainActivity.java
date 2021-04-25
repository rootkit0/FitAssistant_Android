package com.example.fitassistant;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private NavigationView drawerNavView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        drawerNavView = findViewById(R.id.drawer_navview);
        setupDrawerListener(drawerNavView);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        System.out.println(item);
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerListener(NavigationView navView) {
        navView.setNavigationItemSelectedListener(
                item -> {
                    selectMenuItem(item);
                    return true;
                }
        );
    }

    private void selectMenuItem(MenuItem item) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch(item.getItemId()) {
            case R.id.drawer_home:
                HomeFragment homeFragment = new HomeFragment();
                fragmentTransaction.replace(R.id.fragment_home, homeFragment);
                break;
            case R.id.drawer_map:
                MapsFragment mapsFragment = new MapsFragment();
                fragmentTransaction.replace(R.id.fragment_maps, mapsFragment);
                break;
            case R.id.drawer_diets:
                DietsFragment dietsFragment = new DietsFragment();
                fragmentTransaction.replace(R.id.fragment_diets, dietsFragment);
                break;
            case R.id.drawer_workout:
                WorkoutFragment workoutFragment = new WorkoutFragment();
                fragmentTransaction.replace(R.id.fragment_workout, workoutFragment);
                break;
            case R.id.drawer_chat:
                ChatFragment chatFragment = new ChatFragment();
                fragmentTransaction.replace(R.id.fragment_chat, chatFragment);
                break;
            case R.id.drawer_settings:
                SettingsFragment settingsFragment = new SettingsFragment();
                fragmentTransaction.replace(R.id.fragment_settings, settingsFragment);
                break;
        }

        item.setChecked(true);
        setTitle(item.getTitle());
        drawerLayout.closeDrawers();
        fragmentTransaction.commit();
    }
}
