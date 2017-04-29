package com.example.marccaps.backups.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marccaps.backups.Activities.LoginActivity;
import com.example.marccaps.backups.Constant.Constants;
import com.example.marccaps.backups.Constant.UserInfo;
import com.example.marccaps.backups.Models.BackUpItem;
import com.example.marccaps.backups.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MarcCaps on 16/4/17.
 */

public class UserFragment extends Fragment {

    @BindView(R.id.username_label)
    TextView mUsername;
    @BindView(R.id.boy_image)
    ImageView mUserImage;
    @BindView(R.id.logout_button)
    FloatingActionButton mLogoutButton;
    @BindView(R.id.files_number)
    TextView mFilesNumber;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment,container, false);
        ButterKnife.bind(this,view);

        initView();

        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInfo.setIsConnected(getActivity(),false);
                UserInfo.setPassword(getActivity(),"");
                UserInfo.setUsername(getActivity(),"");
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
            }
        });

        return view;

    }

    private void initView() {

        mUsername.setText(UserInfo.getUsername(getActivity()));
        mFilesNumber.setText(mFilesNumber.getText() + " " + getUserFilesNumber());
    }

    private int getUserFilesNumber() {
        SharedPreferences mPrefs = getActivity().getSharedPreferences(Constants.FILE_LIST, getActivity().MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString(Constants.FILE_LIST, "");
        List<BackUpItem> mUserFiles;
        if (json.isEmpty()) {
            mUserFiles = new ArrayList<BackUpItem>();
        } else {
            Type type = new TypeToken<List<BackUpItem>>() {
            }.getType();
            mUserFiles = gson.fromJson(json, type);
        }
        return mUserFiles.size();
    }
}
