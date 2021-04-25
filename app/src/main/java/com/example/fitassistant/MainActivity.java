package com.example.fitassistant;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

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
        Fragment fragment = null;
        Class fragmentClass;
        switch(item.getItemId()) {
            case R.id.nav_first_fragment:
                fragmentClass = HomeFragment.class;
                break;
            case R.id.nav_second_fragment:
                fragmentClass = MapsFragment.class;
                break;
            case R.id.nav_third_fragment:
                fragmentClass = DietsFragment.class;
                break;
            case R.id.nav_fourth_fragment:
                fragmentClass = WorkoutFragment.class;
                break;
            case R.id.nav_fifth_fragment:
                fragmentClass = ChatFragment.class;
                break;
            case R.id.nav_sixth_fragment:
                fragmentClass = OptionsFragment.class;
                break;
            default:
                fragmentClass = HomeFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        item.setChecked(true);
        setTitle(item.getTitle());
        drawerLayout.closeDrawers();
    }
}
