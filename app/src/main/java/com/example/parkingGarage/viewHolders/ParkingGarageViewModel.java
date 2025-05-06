package com.example.parkingGarage.viewHolders;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.parkingGarage.database.ParkingGarageRepository;
import com.example.parkingGarage.database.entities.ParkingFloor;
import com.example.parkingGarage.database.entities.ParkingGarage;
import com.example.parkingGarage.database.entities.ParkingSpace;

import java.util.List;

public class ParkingGarageViewModel extends AndroidViewModel {
    private final ParkingGarageRepository repository;


    public ParkingGarageViewModel(Application application){
        super(application);
        repository = ParkingGarageRepository.getRepository(application);
    }

//    public LiveData<ParkingFloor> getFloorByTheId(int floorId){
//        return repository.getFloorById(floorId);
//    }
//
//    public LiveData<ParkingSpace> getFloorByTheId(int spaceId){
//        return repository.getSpaceById(spaceId);
//    }
//    public LiveData<List<ParkingGarage>> getAllLogsById(int userId) {
//        return repository.getAllLogsByUserIdLiveData(userId);
//    }
//commented out
//    @Deprecated
//    public void insert(ParkingGarage log){
//        repository.insertUser(log);
//    }
}
