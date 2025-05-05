package com.example.parkingGarage.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.parkingGarage.database.entities.ParkingGarage;
import com.example.parkingGarage.database.entities.User;


import java.util.List;
@Dao
public interface ParkingGarageDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ParkingGarage parkingLog);

    @Query("DELETE FROM " + ParkingGarageDatabase.PARKING_GARAGE_TABLE) void deleteAll();


    @Query("SELECT * FROM " + ParkingGarageDatabase.PARKING_GARAGE_TABLE + " ORDER BY garageId DESC")
    List<ParkingGarage> getAllGarages();

    @Query("SELECT * FROM " + ParkingGarageDatabase.PARKING_GARAGE_TABLE + " WHERE garageId == :garageId")
    LiveData<ParkingGarage> getGarageById(int garageId);
//    @Query("SELECT * FROM " + ParkingGarageDatabase.PARKING_GARAGE_TABLE + " WHERE userId = :userId ORDER BY date DESC")
//    LiveData<List<ParkingGarage>> getAllLogsByUserId(int userId);



//    @Query("SELECT * FROM " + ParkingGarageDatabase.PARKING_GARAGE_TABLE + " WHERE userId = :loggedInUserId ORDER BY date DESC")
//    List<ParkingGarage> getRecordsetUserId(int loggedInUserId);
//
//    @Query("SELECT * FROM " + ParkingGarageDatabase.PARKING_GARAGE_TABLE + " WHERE userId = :loggedInUserId ORDER BY date DESC")
//    LiveData<List<ParkingGarage>> getRecordsetUserIdLiveData(int loggedInUserId);

}
