package com.example.parkingGarage.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.parkingGarage.database.ParkingGarageDatabase;

import java.util.Objects;

@Entity(tableName = ParkingGarageDatabase.PARKING_SPACE_TABLE)
public class ParkingSpace{
    private int spaceNum;
    private boolean occupied;

    @PrimaryKey(autoGenerate = true)
    private int spaceId;
    private int floorId;
    public ParkingSpace(int spaceNum, boolean occupied, int floorId) {
        this.spaceNum = spaceNum;
        this.occupied = occupied;
        this.floorId = floorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSpace that = (ParkingSpace) o;
        return spaceNum == that.spaceNum && occupied == that.occupied && spaceId == that.spaceId && floorId == that.floorId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(spaceNum, occupied, spaceId, floorId);
    }

    public int getSpaceNum() {
        return spaceNum;
    }

    public void setSpaceNum(int spaceNum) {
        this.spaceNum = spaceNum;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public int getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(int spaceId) {
        this.spaceId = spaceId;
    }

    public int getFloorId() {
        return floorId;
    }

    public void setFloorId(int floorId) {
        this.floorId = floorId;
    }
}

