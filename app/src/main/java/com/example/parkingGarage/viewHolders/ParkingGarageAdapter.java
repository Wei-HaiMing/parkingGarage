package com.example.parkingGarage.viewHolders;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.parkingGarage.database.entities.ParkingGarage;
import com.example.parkingGarage.database.entities.ParkingSpace;

public class ParkingGarageAdapter extends ListAdapter<ParkingSpace, ParkingGarageViewHolder> {
    public ParkingGarageAdapter(@NonNull DiffUtil.ItemCallback<ParkingSpace> diffCallBack){
        super(diffCallBack);
    }

    @NonNull
    @Override
    public ParkingGarageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return ParkingGarageViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ParkingGarageViewHolder holder, int position) {
        ParkingSpace current = getItem(position);
        holder.bind(current.toString());
    }

    public static class ParkingGarageDiff extends DiffUtil.ItemCallback<ParkingSpace>{
        @Override
        public boolean areItemsTheSame(@NonNull ParkingSpace oldItem, @NonNull ParkingSpace newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull ParkingSpace oldItem, @NonNull ParkingSpace newItem) {
            return oldItem.equals(newItem);
        }
    }
}
