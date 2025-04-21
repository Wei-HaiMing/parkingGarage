package com.example.parkingGarage.viewHolders;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.parkingGarage.database.GymLogRepository;
import com.example.parkingGarage.database.entities.ParkingGarage;

import java.util.List;

public class GymLogViewModel extends AndroidViewModel {
    private final GymLogRepository repository;


    public GymLogViewModel(Application application){
        super(application);
        repository = GymLogRepository.getRepository(application);
    }

    public LiveData<List<ParkingGarage>> getAllLogsById(int userId) {
        return repository.getAllLogsByUserIdLiveData(userId);
    }

    public void insert(ParkingGarage log){
        repository.insertGymLog(log);
    }
}
