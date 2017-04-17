package com.example.marccaps.backups.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.marccaps.backups.Models.BackUpItem;
import com.example.marccaps.backups.R;

import java.util.List;

/**
 * Created by MarcCaps on 17/4/17.
 */

public class BackUpAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

    private List<BackUpItem> itemList;
    private Context context;

    public BackUpAdapter(Context context, List<BackUpItem> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.backup_list_item, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        holder.fileName.setText(itemList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}

