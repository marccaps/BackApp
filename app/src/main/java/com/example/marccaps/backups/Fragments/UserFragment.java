package com.example.marccaps.backups.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marccaps.backups.Constant.UserInfo;
import com.example.marccaps.backups.R;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment,container, false);
        ButterKnife.bind(this,view);

        initView();

        return view;

    }

    private void initView() {

        mUsername.setText(UserInfo.getUsername(getActivity()));

    }
}
