package com.example.evan.travelerio;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import static android.support.constraint.Constraints.TAG;
import static com.example.evan.travelerio.FragmentCheckIn.PICK_IMAGE;

public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /**
         * IF API IS 19 NEED TO ENABLE THIS OTHERWISE IMAGEBUTTONS WONT WORK!
         */

        int SDK_INT = android.os.Build.VERSION.SDK_INT;

        if (SDK_INT <= 19){
            Log.d(TAG, "onCreate: version 19 or less");
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        }

        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }

        /**
         *  If phone API is after 23 you need to use WRITE EXTERNAL STORAGE AFTER API 23
         */

        //ActivityCompat.requestPermissions(this,new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        getSupportFragmentManager().addOnBackStackChangedListener(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                    new FragmentHome(), "HOME_FRAGMENT").commit();
        }

        setUpBottomNavigationView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult: MAINACTIVITY");
        Log.d(TAG, "onActivityResult: RequestCode: " + requestCode);

        if(requestCode == 1){
            FragmentCheckIn fragment = (FragmentCheckIn) getSupportFragmentManager().findFragmentByTag("FRAGMENT_CHECKIN");
            if(fragment != null){
                fragment.CheckInLoadImage(data);
            }
        }


        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null){
            if(result.getContents() == null){
                Toast.makeText(this, "No result found", Toast.LENGTH_LONG).show();
            }else{
                Log.d(TAG, "onActivityResult: " + result.getContents());
                Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                FragmentCitySpots fragmentCitySpots = (FragmentCitySpots) getSupportFragmentManager().findFragmentByTag("FRAGMENT_CITYSPOTS");
                if(fragmentCitySpots == null){

                    FragmentCitySpots fragmentCitySpots1 = (FragmentCitySpots) new FragmentCitySpots();
                    Bundle args = new Bundle();
                    args.putString("city_id", result.getContents());
                    fragmentCitySpots1.setArguments(args);
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragmentCitySpots1, "FRAGMENT_CITYSPOTS").addToBackStack(null).commitAllowingStateLoss();
                }
            }

        }


    }

    private void setUpBottomNavigationView(){
        final BottomNavigationView bottomNavigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){

                    case R.id.icon_home:

                        if (getSupportFragmentManager().findFragmentByTag("HOME_FRAGMENT") != null) {
                            getSupportFragmentManager().beginTransaction().show(getSupportFragmentManager().findFragmentByTag("HOME_FRAGMENT")).commit();
                        } else {
                            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                                    new FragmentHome(), "HOME_FRAGMENT").commit();
                        }
                        if (getSupportFragmentManager().findFragmentByTag("SEARCH_FRAGMENT") != null) {
                            getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag("SEARCH_FRAGMENT")).commit();
                        }
                        if (getSupportFragmentManager().findFragmentByTag("ADD_FRAGMENT") != null) {
                            getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag("ADD_FRAGMENT")).commit();
                        }
                        if (getSupportFragmentManager().findFragmentByTag("NOTIFICATIONS_FRAGMENT") != null) {
                            getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag("NOTIFICATIONS_FRAGMENT")).commit();
                        }
                        if (getSupportFragmentManager().findFragmentByTag("PROFILE_FRAGMENT") != null) {
                            getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag("PROFILE_FRAGMENT")).commit();
                        }


                        return true;

                    case R.id.icon_search:
                        if (getSupportFragmentManager().findFragmentByTag("SEARCH_FRAGMENT") != null) {
                            getSupportFragmentManager().beginTransaction().show(getSupportFragmentManager().findFragmentByTag("SEARCH_FRAGMENT")).commit();
                        } else {
                            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                                    new FragmentSearch(), "SEARCH_FRAGMENT").commit();
                        }


                        if (getSupportFragmentManager().findFragmentByTag("HOME_FRAGMENT") != null) {
                            getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag("HOME_FRAGMENT")).commit();
                        }
                        if (getSupportFragmentManager().findFragmentByTag("ADD_FRAGMENT") != null) {
                            getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag("ADD_FRAGMENT")).commit();
                        }
                        if (getSupportFragmentManager().findFragmentByTag("NOTIFICATIONS_FRAGMENT") != null) {
                            getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag("NOTIFICATIONS_FRAGMENT")).commit();
                        }
                        if (getSupportFragmentManager().findFragmentByTag("PROFILE_FRAGMENT") != null) {
                            getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag("PROFILE_FRAGMENT")).commit();
                        }


                        return true;

                    case R.id.icon_add:

                        FragmentAdd fragmentAdd = new FragmentAdd();
                        fragmentAdd.show(getSupportFragmentManager(), "ADD_FRAGMENT");

                        return false;

                    case R.id.icon_notifications:

                        if (getSupportFragmentManager().findFragmentByTag("NOTIFICATIONS_FRAGMENT") != null) {
                            getSupportFragmentManager().beginTransaction().show(getSupportFragmentManager().findFragmentByTag("NOTIFICATIONS_FRAGMENT")).commit();
                        } else {
                            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                                    new FragmentNotifications(), "NOTIFICATIONS_FRAGMENT").commit();
                        }
                        if (getSupportFragmentManager().findFragmentByTag("HOME_FRAGMENT") != null) {
                            getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag("HOME_FRAGMENT")).commit();
                        }
                        if (getSupportFragmentManager().findFragmentByTag("SEARCH_FRAGMENT") != null) {
                            getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag("SEARCH_FRAGMENT")).commit();
                        }
                        if (getSupportFragmentManager().findFragmentByTag("ADD_FRAGMENT") != null) {
                            getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag("ADD_FRAGMENT")).commit();
                        }
                        if (getSupportFragmentManager().findFragmentByTag("PROFILE_FRAGMENT") != null) {
                            getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag("PROFILE_FRAGMENT")).commit();
                        }

                        return true;

                    case R.id.icon_profile:

                        if (getSupportFragmentManager().findFragmentByTag("PROFILE_FRAGMENT") != null) {
                            getSupportFragmentManager().beginTransaction().show(getSupportFragmentManager().findFragmentByTag("PROFILE_FRAGMENT")).commit();
                        } else {
                            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                                    new FragmentProfile(), "PROFILE_FRAGMENT").commit();
                        }
                        if (getSupportFragmentManager().findFragmentByTag("HOME_FRAGMENT") != null) {
                            getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag("HOME_FRAGMENT")).commit();
                        }
                        if (getSupportFragmentManager().findFragmentByTag("SEARCH_FRAGMENT") != null) {
                            getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag("SEARCH_FRAGMENT")).commit();
                        }
                        if (getSupportFragmentManager().findFragmentByTag("ADD_FRAGMENT") != null) {
                            getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag("ADD_FRAGMENT")).commit();
                        }
                        if (getSupportFragmentManager().findFragmentByTag("NOTIFICATIONS_FRAGMENT") != null) {
                            getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag("NOTIFICATIONS_FRAGMENT")).commit();
                        }

                        return true;


                }

                return false;
            }
        });
    }

    @Override
    public void onBackStackChanged() {

        Log.d(TAG, "onBackStackChanged: " + getSupportFragmentManager().getBackStackEntryCount());
        if(getSupportFragmentManager().getBackStackEntryCount() > 0){
            if(findViewById(R.id.bottom_navigation).getVisibility() == View.VISIBLE){
                findViewById(R.id.bottom_navigation).setVisibility(View.INVISIBLE);
                findViewById(R.id.bottomnav_divider).setVisibility(View.INVISIBLE);
            }
        }else if(getSupportFragmentManager().getBackStackEntryCount() == 0){
            if(findViewById(R.id.bottom_navigation).getVisibility() == View.INVISIBLE){
                findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
                findViewById(R.id.bottomnav_divider).setVisibility(View.VISIBLE);
            }
        }
    }


    @Override
    public void onBackPressed() {


        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            getSupportFragmentManager().executePendingTransactions();
            Log.d(TAG, "onBackPressed: " + getSupportFragmentManager().getBackStackEntryCount());

        }else if(getSupportFragmentManager().getBackStackEntryCount() == 0 && !getSupportFragmentManager().findFragmentByTag("HOME_FRAGMENT").isVisible()){
            Log.d(TAG, "onBackPressed: not visible home");

            if (getSupportFragmentManager().findFragmentByTag("HOME_FRAGMENT") != null) {
                getSupportFragmentManager().beginTransaction().show(getSupportFragmentManager().findFragmentByTag("HOME_FRAGMENT")).commit();
            } else {
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                        new FragmentHome(), "HOME_FRAGMENT").commit();
            }

            if (getSupportFragmentManager().findFragmentByTag("SEARCH_FRAGMENT") != null) {
                getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag("SEARCH_FRAGMENT")).commit();
            }
            if (getSupportFragmentManager().findFragmentByTag("ADD_FRAGMENT") != null) {
                getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag("ADD_FRAGMENT")).commit();
            }
            if (getSupportFragmentManager().findFragmentByTag("NOTIFICATIONS_FRAGMENT") != null) {
                getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag("NOTIFICATIONS_FRAGMENT")).commit();
            }
            if (getSupportFragmentManager().findFragmentByTag("PROFILE_FRAGMENT") != null) {
                getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag("PROFILE_FRAGMENT")).commit();
            }

        }else if(getSupportFragmentManager().getBackStackEntryCount() == 0 && getSupportFragmentManager().findFragmentByTag("HOME_FRAGMENT").isVisible()){
            Log.d(TAG, "onBackPressed: home is visible, exiting app");
            super.onBackPressed();
        }


    }
}
