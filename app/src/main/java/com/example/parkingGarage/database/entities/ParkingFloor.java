package com.example.parkingGarage.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.parkingGarage.database.ParkingGarageDatabase;

import java.util.Objects;

@Entity(tableName = ParkingGarageDatabase.PARKING_FLOOR_TABLE)
public class ParkingFloor {

    @PrimaryKey(autoGenerate = true)
    private int floorId;
    private int spaceCount;
    private int floorNum;
    private int spacesAvailable;

    private int garageId;

    public ParkingFloor(int spaceCount, int floorNum, int spacesAvailable, int garageId) {
        this.spaceCount = spaceCount;
        this.floorNum = floorNum;
        this.spacesAvailable = spacesAvailable;
        this.garageId = garageId;
    }

    @Override
    public String toString() {
        return "ParkingFloor{" +
                "floorId=" + floorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingFloor that = (ParkingFloor) o;
        return spaceCount == that.spaceCount && floorNum == that.floorNum && spacesAvailable == that.spacesAvailable && floorId == that.floorId && garageId == that.garageId;
    }

    public int getSpaceCount() {
        return spaceCount;
    }

    public void setSpaceCount(int spaceCount) {
        this.spaceCount = spaceCount;
    }

    public int getFloorNum() {
        return floorNum;
    }

    public void setFloorNum(int floorNum) {
        this.floorNum = floorNum;
    }

    public int getSpacesAvailable() {
        return spacesAvailable;
    }

    public void setSpacesAvailable(int spacesAvailable) {
        this.spacesAvailable = spacesAvailable;
    }

    public int getFloorId() {
        return floorId;
    }

    public void setFloorId(int floorId) {
        this.floorId = floorId;
    }

    public int getGarageId() {
        return garageId;
    }

    public void setGarageId(int garageId) {
        this.garageId = garageId;
    }
}