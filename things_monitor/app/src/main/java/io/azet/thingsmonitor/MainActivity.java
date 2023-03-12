package io.azet.thingsmonitor;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import io.azet.thingsmonitor.fragments.ChartsFragment;
import io.azet.thingsmonitor.fragments.DashboardFragment;
import io.azet.thingsmonitor.fragments.SettingsFragment;


public class MainActivity extends AppCompatActivity implements ChartsFragment.OnFragmentInteractionListener,
        DashboardFragment.OnFragmentInteractionListener, SettingsFragment.OnFragmentInteractionListener {


    private static final String DOWNLOAD_UPDATE = "azet.io.settings.download.update";
    private static final String NEW_FEED = "azet.io.feed.update";
    private static final String BAD_CHANNEL = "azet.io.feed.error";

    Fragment selectedFragment = null;
    ChartsFragment chartsFragment = null;
    DashboardFragment dashboardFragment = null;
    SettingsFragment settingsFragment = null;

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(DOWNLOAD_UPDATE)) {
                String access = intent.getExtras().getString("access");
                String channel = intent.getExtras().getString("channel");
                if (access.equals("private")) {
                    String apiKey = intent.getExtras().getString("apiKey");
                    chartsFragment.setApiKey(apiKey);
                }
                chartsFragment.setAccess(access);
                chartsFragment.setChannel(channel);
            }
        }
    };

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_charts:
                    selectedFragment = chartsFragment;
                    break;
                case R.id.navigation_dashboard:
                    selectedFragment = dashboardFragment;
                    break;
                case R.id.navigation_settings:
                    selectedFragment = settingsFragment;
                    break;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame, selectedFragment);
            transaction.commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chartsFragment = new ChartsFragment();
        dashboardFragment = new DashboardFragment();
        settingsFragment = new SettingsFragment();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame, settingsFragment).commit();
        navigation.getMenu().getItem(2).setChecked(true);

        IntentFilter filter = new IntentFilter();
        filter.addAction(DOWNLOAD_UPDATE);

        registerReceiver(receiver, filter);

        Intent intent = new Intent(this, RestResponseBroadcaster.class);
        startService(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
