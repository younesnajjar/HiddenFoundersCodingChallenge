package com.example.younes.hiddenfounderscodingchallenge.ui.ui.activities;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.younes.hiddenfounderscodingchallenge.R;
import com.example.younes.hiddenfounderscodingchallenge.ui.ui.fragments.SettingsFragment;
import com.example.younes.hiddenfounderscodingchallenge.ui.ui.fragments.TrendingFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    SettingsFragment mSettingsFragment;
    TrendingFragment mTrendingFragment;

    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initBottomNavigation();
        initFragments(getApplicationContext());
        initListeners();

        // Loading the Trending Fragment
        loadFragment(mTrendingFragment, "Trending");

    }
    private void initBottomNavigation() {

        //Items Creation
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.trending_framgement, R.drawable.ic_action_star, R.color.colorAccent);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.settings_fragment  , R.drawable.ic_action_settings, R.color.colorAccent);

        // Add items
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);

        // Settings
        bottomNavigation.setBehaviorTranslationEnabled(true);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setAccentColor(ContextCompat.getColor(this, R.color.colorAccent));

    }
    private boolean loadFragment(Fragment fragment, String tag) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_fragment_container, fragment, tag)
                    .commit();
            return true;
        }
        return false;
    }
    private void initListeners(){
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if(position == 0)
                    loadFragment(mTrendingFragment,"Trending");
                else
                    loadFragment(mSettingsFragment,"Settings");

                return true;
            }
        });
    }
    private void initFragments(Context context){
        mTrendingFragment = new TrendingFragment();
        mSettingsFragment = new SettingsFragment();
    }
}
