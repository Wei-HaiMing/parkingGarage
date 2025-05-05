package com.example.parkingGarage.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.parkingGarage.database.entities.ParkingFloor;


import java.util.List;

@Dao
public interface ParkingFloorDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ParkingFloor parkingFloor);

    @Query("SELECT * FROM " + ParkingGarageDatabase.PARKING_FLOOR_TABLE + " ORDER BY floorId")
    List<ParkingFloor> getAllFloors();

    @Query("SELECT * FROM " + ParkingGarageDatabase.PARKING_FLOOR_TABLE + " WHERE floorId == :floorId")
    LiveData<ParkingFloor> getFloorByFloorId(int floorId);


}
