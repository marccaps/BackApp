package com.example.marccaps.backups.Fragments;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
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

    private ArrayList<String> mFilePaths;
    private LinearLayoutManager lLayout;


    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.backup_fragment, container, false);

        ButterKnife.bind(this, view);

        List<BackUpItem> rowListItem = getAllItemList();
        lLayout = new LinearLayoutManager(view.getContext());

        mRecyclerView.setLayoutManager(lLayout);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    if (shouldShowRequestPermissionRationale(
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        // TODO: 23/4/17 Explicar porque necesitamos acceso a sus datos
                    }

                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            Constants.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                }
                else {
                    FilePickerBuilder.getInstance().setMaxCount(10)
                            .setSelectedFiles(mFilePaths)
                            .setActivityTheme(R.style.AppTheme)
                            .pickFile(getActivity());
                }
            }
        });

        BackUpAdapter rcAdapter = new BackUpAdapter(view.getContext(), rowListItem);
        mRecyclerView.setAdapter(rcAdapter);
        return view;
    }

    private List<BackUpItem> getAllItemList() {

        List<BackUpItem> allItems = new ArrayList<BackUpItem>();
        allItems.add(new BackUpItem("File 1"));
        allItems.add(new BackUpItem("File 2"));
        allItems.add(new BackUpItem("File 3"));
        allItems.add(new BackUpItem("File 4"));
        allItems.add(new BackUpItem("File 5"));
        allItems.add(new BackUpItem("File 6"));
        allItems.add(new BackUpItem("File 7"));
        allItems.add(new BackUpItem("File 8"));
        allItems.add(new BackUpItem("File 9"));
        allItems.add(new BackUpItem("File 10"));

        return allItems;
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
        // TODO: 23/4/17 Add them to the view
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

                } else {
                    // TODO: 23/4/17 Tell the user that this is necessary
                }
            }
        }
    }
}
