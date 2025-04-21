package com.example.parkingGarage.viewHolders;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.parkingGarage.database.entities.ParkingGarage;

public class ParkingGarageAdapter extends ListAdapter<ParkingGarage, ParkingGarageViewHolder> {
    public ParkingGarageAdapter(@NonNull DiffUtil.ItemCallback<ParkingGarage> diffCallBack){
        super(diffCallBack);
    }

    @NonNull
    @Override
    public ParkingGarageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return ParkingGarageViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ParkingGarageViewHolder holder, int position) {
        ParkingGarage current = getItem(position);
        holder.bind(current.toString());
    }

    public static class ParkingGarageDiff extends DiffUtil.ItemCallback<ParkingGarage>{
        @Override
        public boolean areItemsTheSame(@NonNull ParkingGarage oldItem, @NonNull ParkingGarage newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull ParkingGarage oldItem, @NonNull ParkingGarage newItem) {
            return oldItem.equals(newItem);
        }
    }
}
