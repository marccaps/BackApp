package com.example.marccaps.backups.Adapters;

/**
 * Created by MarcCaps on 17/4/17.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.marccaps.backups.R;

public class RecyclerViewHolders extends RecyclerView.ViewHolder{

    public TextView fileName;

    public RecyclerViewHolders(View itemView) {
        super(itemView);

        fileName = (TextView)itemView.findViewById(R.id.file_name);

    }
}
