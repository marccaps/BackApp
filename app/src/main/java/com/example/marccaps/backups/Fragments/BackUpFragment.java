package com.example.marccaps.backups.Fragments;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.marccaps.backups.Activities.MainActivity;
import com.example.marccaps.backups.Adapters.BackUpAdapter;
import com.example.marccaps.backups.Constant.Constants;
import com.example.marccaps.backups.Constant.UserInfo;
import com.example.marccaps.backups.Interfaces.ApiEndpointInterface;
import com.example.marccaps.backups.Models.BackUpItem;
import com.example.marccaps.backups.Models.FileObject;
import com.example.marccaps.backups.Models.ResponseLogin;
import com.example.marccaps.backups.Models.User;
import com.example.marccaps.backups.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.marccaps.backups.Constant.Constants.BASE_URL;

/**
 * Created by MarcCaps on 16/4/17.
 */

public class BackUpFragment extends Fragment {

    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.add_backup_file)
    FloatingActionButton mAddButton;

    private ArrayList<BackUpItem> mUserFiles;

    public Retrofit retrofit;
    public ApiEndpointInterface apiService;

    private static final String TAG = BackUpFragment.class.getCanonicalName();
    private ArrayList<String> mFilePaths;
    private LinearLayoutManager lLayout;
    private BackUpAdapter backUpAdapter;


    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.backup_fragment, container, false);

        ButterKnife.bind(this, view);

        lLayout = new LinearLayoutManager(view.getContext());

        mRecyclerView.setLayoutManager(lLayout);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiEndpointInterface.class);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (shouldShowRequestPermissionRationale(
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        // TODO: 23/4/17 Explicar porque necesitamos acceso a sus datos
                    }

                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            Constants.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                } else {
                    FilePickerBuilder.getInstance().setMaxCount(10)
                            .setSelectedFiles(mFilePaths)
                            .setActivityTheme(R.style.AppTheme)
                            .pickFile(getActivity());
                }
            }
        });

        getUserFiles();
        backUpAdapter = new BackUpAdapter(view.getContext(), mUserFiles);
        mUserFiles.add(new BackUpItem("lab2.pdf"));
        mRecyclerView.setAdapter(backUpAdapter);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case FilePickerConst.REQUEST_CODE_DOC:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    mFilePaths = new ArrayList<>();
                    mFilePaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS));
                    File dir = Environment.getExternalStorageDirectory();
                    File yourFile = new File(dir, mFilePaths.get(0));
                    FileObject fileObject = new FileObject(yourFile);
                    Call<ResponseBody> uploadFile = apiService.uploadFile(UserInfo.getUsername(getActivity()),fileObject);
                    uploadFile.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Log.d(TAG,"SUCCESS");
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.e(TAG,"ERROR");
                        }
                    });

                }
                break;
        }
        mUserFiles.addAll(filePahtToBackUpItem(mFilePaths));
        saveUserFiles(mUserFiles);
        backUpAdapter.notifyDataSetChanged();
    }

    private void saveUserFiles(ArrayList<BackUpItem> mUserFiles) {
        SharedPreferences appSharedPrefs = getActivity().getSharedPreferences(Constants.FILE_LIST,getActivity().MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mUserFiles);
        prefsEditor.putString(Constants.FILE_LIST, json);
        prefsEditor.apply();
    }

    private void getUserFiles() {
        SharedPreferences mPrefs = getActivity().getSharedPreferences(Constants.FILE_LIST, getActivity().MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString(Constants.FILE_LIST, "");
        if (json.isEmpty()) {
            mUserFiles = new ArrayList<BackUpItem>();
        } else {
            Type type = new TypeToken<List<BackUpItem>>() {
            }.getType();
            mUserFiles = gson.fromJson(json, type);
        }
    }

    private ArrayList<BackUpItem> filePahtToBackUpItem(ArrayList<String> mFilePaths) {
        ArrayList<BackUpItem> newFiles = new ArrayList<>();
        for (String file : mFilePaths) {
            BackUpItem backUpItem = new BackUpItem(file.substring(file.lastIndexOf("/") + 1));
            newFiles.add(backUpItem);
        }
        return newFiles;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Constants.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay!
                    FilePickerBuilder.getInstance().setMaxCount(10)
                            .setSelectedFiles(mFilePaths)
                            .setActivityTheme(R.style.AppTheme)
                            .pickFile(getActivity());

                }
            }
        }
    }
}
