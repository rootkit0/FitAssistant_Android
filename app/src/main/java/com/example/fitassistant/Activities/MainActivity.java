package com.example.fitassistant.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.fitassistant.Fragments.DietsFragment;
import com.example.fitassistant.Fragments.HomeFragment;
import com.example.fitassistant.Fragments.MapsFragment;
import com.example.fitassistant.Fragments.SettingsFragment;
import com.example.fitassistant.Fragments.WorkoutFragment;
import com.example.fitassistant.Providers.AuthProvider;
import com.example.fitassistant.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private NavigationView drawerNavView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private AuthProvider authProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Init providers
        authProvider = new AuthProvider();
        checkLoggedUser();

        //Drawer stuff
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        drawerNavView = findViewById(R.id.drawer_navview);
        setupDrawerListener(drawerNavView);

        //Enable action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Call home fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new HomeFragment()).commit();
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

    @SuppressLint("NonConstantResourceId")
    private void selectMenuItem(MenuItem item) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch(item.getItemId()) {
            case R.id.drawer_home:
                fragmentTransaction.replace(R.id.fragment, new HomeFragment());
                break;
            case R.id.drawer_map:
                fragmentTransaction.replace(R.id.fragment, new MapsFragment());
                break;
            case R.id.drawer_diets:
                fragmentTransaction.replace(R.id.fragment, new DietsFragment());
                break;
            case R.id.drawer_workout:
                fragmentTransaction.replace(R.id.fragment, new WorkoutFragment());
                break;
            case R.id.drawer_settings:
                fragmentTransaction.replace(R.id.fragment, new SettingsFragment());
                break;
            case R.id.drawer_signout:
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
        }
        item.setChecked(true);
        drawerLayout.closeDrawers();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkLoggedUser();
    }

    private void checkLoggedUser() {
        if(!authProvider.getUserLogged()) {
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
        }
    }
}
