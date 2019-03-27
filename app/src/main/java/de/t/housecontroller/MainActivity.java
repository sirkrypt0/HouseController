package de.t.housecontroller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static MainActivity singleton;
    private int currentActiveFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            if(item.getItemId() == currentActiveFragment)
                return false;

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new HomeFragment();
                    currentActiveFragment = R.id.navigation_home;
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_doorbell:
                    fragment = new DoorbellFragment();
                    currentActiveFragment = R.id.navigation_doorbell;
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_light:
                    fragment = new LightFragment();
                    currentActiveFragment = R.id.navigation_light;
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_weather:
                    fragment = new WeatherFragment();
                    currentActiveFragment = R.id.navigation_weather;
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(singleton == null)
            singleton = this;

        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // load the home fragment by default
        loadFragment(new HomeFragment());
    }

    private void loadFragment(Fragment fragment){
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
