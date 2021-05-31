package com.example.fitassistant.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.fitassistant.Fragments.DietsFragment;
import com.example.fitassistant.Fragments.HomeFragment;
import com.example.fitassistant.Fragments.MapsFragment;
import com.example.fitassistant.Fragments.SettingsFragment;
import com.example.fitassistant.Fragments.WorkoutsFragment;
import com.example.fitassistant.Other.Constants;
import com.example.fitassistant.Providers.AuthProvider;
import com.example.fitassistant.R;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private AuthProvider authProvider;
    private AsyncTaskRunnerNetworkState runner;

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
        NavigationView drawerNavView = findViewById(R.id.drawer_navview);
        setupDrawerListener(drawerNavView);

        //Enable action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        //Call home fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new HomeFragment()).commit();
        //Async task check network
        runner = new AsyncTaskRunnerNetworkState();
        runner.execute();
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

        switch (item.getItemId()) {
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
                fragmentTransaction.replace(R.id.fragment, new WorkoutsFragment());
                break;
            case R.id.drawer_settings:
                fragmentTransaction.replace(R.id.fragment, new SettingsFragment());
                break;
            case R.id.drawer_signout:
                authProvider.signOut();
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
        runner = new AsyncTaskRunnerNetworkState();
        runner.execute();
    }

    private void checkLoggedUser() {
        if (!authProvider.getUserLogged()) {
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
        }
    }

    private class AsyncTaskRunnerNetworkState extends AsyncTask<String, String, String[]> {
        @Override
        protected String[] doInBackground(String... strings) {
            String activeNetwork = "";
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                try {
                    Network net = connectivityManager.getActiveNetwork();
                    NetworkCapabilities netCapabilities = connectivityManager.getNetworkCapabilities(net);
                    if (net != null && netCapabilities != null) {
                        if (netCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                            activeNetwork = "Xarxa wifi connectada!";
                        } else if (netCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                            activeNetwork = "Xarxa movil connectada!";
                        }
                    } else {
                        activeNetwork = "No tens cap xarxa activa!";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                    if (netInfo != null && netInfo.isConnected()) {
                        if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                            activeNetwork = "Xarxa wifi connectada!";
                        } else if (netInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                            activeNetwork = "Xarxa movil connectada!";
                        }
                    } else {
                        activeNetwork = "No tens cap xarxa activa!";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return new String[]{activeNetwork};
        }

        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);
            Constants.setNetworkState(strings[0]);
            Toast.makeText(getApplicationContext(), strings[0], Toast.LENGTH_SHORT).show();
        }
    }
}
