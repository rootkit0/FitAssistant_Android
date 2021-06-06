package com.example.fitassistant.Activities;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.fitassistant.Fragments.ChatFragment;
import com.example.fitassistant.Fragments.DietsFragment;
import com.example.fitassistant.Fragments.HomeFragment;
import com.example.fitassistant.Fragments.MapsFragment;
import com.example.fitassistant.Fragments.SettingsFragment;
import com.example.fitassistant.Fragments.WorkoutsFragment;
import com.example.fitassistant.Other.Constants;
import com.example.fitassistant.Providers.AuthProvider;
import com.example.fitassistant.R;
import com.example.fitassistant.Services.MyFirebaseMessagingService;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Objects;

import static com.google.android.datatransport.cct.internal.NetworkConnectionInfo.NetworkType.WIFI;

public class MainActivity extends AppCompatActivity {
    private AuthProvider authProvider;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NetworkReceiver receiver = new NetworkReceiver();

    private String sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // Registers BroadcastReceiver to track network connection changes.
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkReceiver();
        this.registerReceiver(receiver, filter);
        //Init providers
        authProvider = new AuthProvider();
        //Drawer stuff
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        NavigationView drawerNavView = findViewById(R.id.drawer_navview);
        setupDrawerListener(drawerNavView);
        //Firebase messaging token
        getFirebaseMessagingToken();
        //Call home fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new HomeFragment()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerListener(NavigationView navView) {
        navView.setNavigationItemSelectedListener(
                item -> {
                    selectMenuItem(item);
                    return true;
                });
    }

    @SuppressLint("NonConstantResourceId")
    private void selectMenuItem(MenuItem item) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out);

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
            case R.id.drawer_chat:
                fragmentTransaction.replace(R.id.fragment, new ChatFragment());
                break;
            case R.id.drawer_settings:
                fragmentTransaction.replace(R.id.fragment, new SettingsFragment());
                break;
            case R.id.drawer_signout:
                goToLoginPage();
                Animatoo.animateFade(this);
        }
        item.setChecked(true);
        drawerLayout.closeDrawers();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void getFirebaseMessagingToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(
                task -> {
                    if (!task.isSuccessful()) {
                        Log.w(getString(R.string.error_tag), getString(R.string.fcm_token_failed), task.getException());
                        return;
                    }
                    MyFirebaseMessagingService mFBS = new MyFirebaseMessagingService();
                    mFBS.sendRegistrationToServer(task.getResult());
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkLoggedUser();
    }

    private void checkLoggedUser() {
        if (!authProvider.getUserLogged()) {
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        // Retrieves a string value for the preferences. The second parameter
        // is the default value to use if a preference value is not found.
        sPref = sharedPrefs.getString("listPref", getString(R.string.wifi));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Unregisters BroadcastReceiver when app is destroyed.
        if (receiver != null) {
            this.unregisterReceiver(receiver);
        }
    }


    public class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String activeNetwork = "";
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                try {
                    Network net = connectivityManager.getActiveNetwork();
                    NetworkCapabilities netCapabilities = connectivityManager.getNetworkCapabilities(net);
                    if (net != null && netCapabilities != null) {
                        if (netCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                            activeNetwork = getString(R.string.wifi);
                        } else if (netCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                            activeNetwork = getString(R.string.data_active);
                        }
                    } else {
                        activeNetwork = getString(R.string.no_network);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                    if (netInfo != null && netInfo.isConnected()) {
                        if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                            activeNetwork = getString(R.string.wifi);

                        } else if (netInfo.getType() == ConnectivityManager.TYPE_MOBILE) {

                            activeNetwork = getString(R.string.data_active);
                        }
                    } else {

                        activeNetwork = getString(R.string.no_network);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (activeNetwork.equals(getString(R.string.no_network))) {
                Toast.makeText(getApplicationContext(), R.string.connexion_lost, Toast.LENGTH_LONG).show();
            }
            else if (sPref.equals(getString(R.string.wifi)) && activeNetwork.equals(getString(R.string.data_active))) {
                Toast.makeText(getApplicationContext(), R.string.wifi_selected_deactivated, Toast.LENGTH_LONG).show();
                goToSettingsFragment();
            }
            Toast.makeText(getApplicationContext(), activeNetwork, Toast.LENGTH_SHORT).show();

        }
    }

    private void goToSettingsFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out);
        fragmentTransaction.replace(R.id.fragment, new SettingsFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    private void goToLoginPage() {
        authProvider.signOut();
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);
    }
}
