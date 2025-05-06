package com.example.parkingGarage.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.parkingGarage.database.entities.ParkingFloor;
import com.example.parkingGarage.database.entities.ParkingSpace;

import java.util.List;

@Dao
public interface ParkingSpaceDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ParkingSpace parkingSpace);

    @Query("DELETE FROM " + ParkingGarageDatabase.PARKING_SPACE_TABLE) void deleteAll();

    @Query("SELECT * FROM " + ParkingGarageDatabase.PARKING_SPACE_TABLE + " ORDER BY spaceId")
    List<ParkingSpace> getAllSpaces();

    @Query("SELECT * FROM " + ParkingGarageDatabase.PARKING_SPACE_TABLE + " WHERE spaceId == :spaceId")
    LiveData<ParkingSpace> getSpaceById(int spaceId);

    @Query("SELECT * FROM " + ParkingGarageDatabase.PARKING_SPACE_TABLE + " WHERE spaceId == :spaceId")
    LiveData<List<ParkingSpace>> getSpaceByIdLiveData(int spaceId);

    @Query("SELECT * FROM " + ParkingGarageDatabase.PARKING_FLOOR_TABLE + " WHERE floorId == :floorId")
    LiveData<ParkingFloor> getFloorBySpaceFloorId(int floorId);

}
