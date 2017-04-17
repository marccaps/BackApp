package com.example.marccaps.backups.Activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.IdRes;

import com.example.marccaps.backups.Fragments.BackUpFragment;
import com.example.marccaps.backups.Fragments.SettingsFragment;
import com.example.marccaps.backups.Fragments.UserFragment;
import com.example.marccaps.backups.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

/**
 * Created by MarcCaps on 1/4/17.
 */

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_backup:
                        Fragment backUpFragment = new BackUpFragment();
                        FragmentTransaction backUpTransition = getFragmentManager().beginTransaction();
                        backUpTransition.setCustomAnimations(
                                R.animator.fragment_animation_in,
                                R.animator.fragment_animation_out);
                        backUpTransition.replace(R.id.contentContainer, backUpFragment);
                        backUpTransition.addToBackStack(null);
                        backUpTransition.commit();
                        break;
                    case R.id.tab_user:
                        Fragment userFragment = new UserFragment();
                        FragmentTransaction userTransition = getFragmentManager().beginTransaction();
                        userTransition.setCustomAnimations(
                                R.animator.fragment_animation_in,
                                R.animator.fragment_animation_out);
                        userTransition.replace(R.id.contentContainer, userFragment);
                        userTransition.addToBackStack(null);
                        userTransition.commit();
                        break;
                    case R.id.tab_settings:
                        Fragment settingsFragment = new SettingsFragment();
                        FragmentTransaction settingsTransition = getFragmentManager().beginTransaction();
                        settingsTransition.setCustomAnimations(
                                R.animator.fragment_animation_in,
                                R.animator.fragment_animation_out);
                        settingsTransition.replace(R.id.contentContainer, settingsFragment);
                        settingsTransition.addToBackStack(null);
                        settingsTransition.commit();
                        break;

                    default:
                        //Nothing to do here
                        break;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        //Nothing to do here
    }
}