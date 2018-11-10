package com.example.younes.hiddenfounderscodingchallenge.ui.ui.activities;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.younes.hiddenfounderscodingchallenge.R;
import com.example.younes.hiddenfounderscodingchallenge.ui.ui.fragments.SettingsFragment;
import com.example.younes.hiddenfounderscodingchallenge.ui.ui.fragments.TrendingFragment;
import com.example.younes.hiddenfounderscodingchallenge.ui.ui.listners.ScrollListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ScrollListener {


    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottomNavigation;

    SettingsFragment mSettingsFragment;
    TrendingFragment mTrendingFragment;


    private boolean fragmentseen = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initBottomNavigation();
        initFragments(getApplicationContext());
        initListeners();
        loadFragment(mTrendingFragment, "home");

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
        bottomNavigation.setInactiveColor(Color.parseColor("#000000"));
        bottomNavigation.setItemDisableColor(Color.parseColor("#FFFFFF"));
        bottomNavigation.disableItemAtPosition(2);

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
                fragmentseen = false;
                switch (position) {
                    case 0:
                        loadFragment(mTrendingFragment,"home");
                        break;
                    case 1:
                        loadFragment(mSettingsFragment,"cards");
                        break;
                    default:
                        loadFragment(mTrendingFragment,"home");
                }
                return true;
            }
        });
    }
    private void initFragments(Context context){
        mTrendingFragment = new TrendingFragment();
        mSettingsFragment = new SettingsFragment();
    }

    @Override
    public void onScrollChange(int dx, int dy) {
        if (dy < 1) {
            bottomNavigation.restoreBottomNavigation(true);
        } else {
            bottomNavigation.hideBottomNavigation(true);
        }
        fragmentseen = true;
        Log.e("test tag", "onScrollChange: scrolled" );
    }
}
