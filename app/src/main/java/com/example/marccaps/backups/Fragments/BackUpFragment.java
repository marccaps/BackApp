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
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marccaps.backups.Activities.MainActivity;
import com.example.marccaps.backups.Adapters.BackUpAdapter;
import com.example.marccaps.backups.Constant.Constants;
import com.example.marccaps.backups.Models.BackUpItem;
import com.example.marccaps.backups.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;

/**
 * Created by MarcCaps on 16/4/17.
 */

public class BackUpFragment extends Fragment {

    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.add_backup_file)
    FloatingActionButton mAddButton;

    private ArrayList<BackUpItem> mUserFiles;

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
