package com.example.marccaps.backups.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marccaps.backups.Activities.MainActivity;
import com.example.marccaps.backups.Adapters.BackUpAdapter;
import com.example.marccaps.backups.Models.BackUpItem;
import com.example.marccaps.backups.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MarcCaps on 16/4/17.
 */

public class BackUpFragment extends Fragment {

    @BindView(R.id.my_recycler_view) RecyclerView mRecyclerView;

    private LinearLayoutManager lLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.backup_fragment, container, false);

        ButterKnife.bind(this,view);

        List<BackUpItem> rowListItem = getAllItemList();
        lLayout = new LinearLayoutManager(view.getContext());

        mRecyclerView.setLayoutManager(lLayout);

        BackUpAdapter rcAdapter = new BackUpAdapter(view.getContext(), rowListItem);
        mRecyclerView.setAdapter(rcAdapter);
        return view;
    }

    private List<BackUpItem> getAllItemList(){

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
}
