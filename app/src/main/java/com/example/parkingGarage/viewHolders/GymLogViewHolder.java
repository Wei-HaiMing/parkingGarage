package com.example.parkingGarage.viewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.parkingGarage.R;

public class GymLogViewHolder extends RecyclerView.ViewHolder {
    private final TextView gymLogViewItem;
    private GymLogViewHolder(View gymLogView){
        super(gymLogView);
        gymLogViewItem = gymLogView.findViewById(R.id.recyclerItemTextview);
    }

    public void bind(String text){
        gymLogViewItem.setText(text);
    }

    static GymLogViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.parkinggarage_recycler_item, parent, false);
        return new GymLogViewHolder(view);
    }
}
