package com.example.marccaps.backups.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.marccaps.backups.Constant.UserInfo;
import com.example.marccaps.backups.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MarcCaps on 1/4/17.
 */

public class LoginActivity extends Activity implements View.OnClickListener {

    private static final String TAG = LoginActivity.class.getCanonicalName();

    @BindView(R.id.input_username) EditText mUserText;
    @BindView(R.id.input_password) EditText mPasswordText;
    @BindView(R.id.btn_login) Button mLoginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);

        mLoginButton.setOnClickListener(this);

    }

    public void login() {

        if (!validate()) {
            onLoginFailed();
            return;
        }

        mLoginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    private void onLoginSuccess() {
        mLoginButton.setEnabled(true);
        UserInfo.setIsConnected(this,true);
        UserInfo.setPassword(this,mPasswordText.getText().toString());
        UserInfo.setUsername(this,mUserText.getText().toString());
        Intent i = new Intent(this , MainActivity.class);
        startActivity(i);
    }

    private void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        mLoginButton.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;

        String email = mUserText.getText().toString();
        String password = mPasswordText.getText().toString();

        if (email.isEmpty() || !email.equals("admin")) {
            mUserText.setError("enter a valid email address");
            valid = false;
        } else {
            mUserText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            mPasswordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            mPasswordText.setError(null);
        }

        return valid;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_login:
                login();
                break;

            default:
                break;
        }
    }
}
