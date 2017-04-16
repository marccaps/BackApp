package com.example.marccaps.backups.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marccaps.backups.R;

/**
 * Created by MarcCaps on 16/4/17.
 */

public class BackUpFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.backup_fragment,
                container, false);
        return view;
    }
}
