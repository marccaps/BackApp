package com.example.marccaps.backups.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.marccaps.backups.Constant.UserInfo;
import com.example.marccaps.backups.Interfaces.ApiEndpointInterface;
import com.example.marccaps.backups.Models.Credentials;
import com.example.marccaps.backups.Models.ResponseLogin;
import com.example.marccaps.backups.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.marccaps.backups.Constant.Constants.BASE_URL;

/**
 * Created by MarcCaps on 1/4/17.
 */

public class LoginActivity extends Activity implements View.OnClickListener {

    private static final String TAG = LoginActivity.class.getCanonicalName();
    ProgressDialog progressDialog;

    public Retrofit retrofit;
    public ApiEndpointInterface apiService;

    @BindView(R.id.input_username) EditText mUserText;
    @BindView(R.id.input_password) EditText mPasswordText;
    @BindView(R.id.btn_login) Button mLoginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiEndpointInterface.class);

        mLoginButton.setOnClickListener(this);

    }

    public void login() {

        validate();

        progressDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        // onLoginFailed();
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

    private void validate() {
        final boolean[] valid = new boolean[1];

        String email = mUserText.getText().toString();
        String password = mPasswordText.getText().toString();

        Credentials credentials = new Credentials(email,password);

        Call<ResponseLogin> getSession = apiService.getAccess(credentials);

        getSession.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if(response.body().getSuccess().equals("true")){
                    onLoginSuccess();
                }
                else {
                    progressDialog.dismiss();
                    onLoginFailed();
                }

            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Log.e(TAG,"Error in login");
                onLoginFailed();
            }
        });
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
