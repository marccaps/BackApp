package com.example.marccaps.backups.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.example.marccaps.backups.Constant.Constants;
import com.example.marccaps.backups.Constant.UserInfo;
import com.example.marccaps.backups.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Hide title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.splash_activity);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                if(UserInfo.isConnected(getApplicationContext())) goToMainActivity();
                else goToLoginActivity();

            }
        };

        // Simulate a long loading process on application startup.
        Timer timer = new Timer();
        timer.schedule(task, Constants.SPLASH_SCREEN_DELAY);
    }

    private void goToLoginActivity() {
        Intent mainIntent = new Intent().setClass(SplashActivity.this, LoginActivity.class);
        startActivity(mainIntent);
    }

    private void goToMainActivity() {
        Intent mainIntent = new Intent().setClass(SplashActivity.this, MainActivity.class);
        startActivity(mainIntent);
    }
}
