package com.example.parkingGarage.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.parkingGarage.database.ParkingGarageDatabase;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(tableName = ParkingGarageDatabase.PARKING_GARAGE_TABLE)
public class ParkingGarage {
    @PrimaryKey(autoGenerate = true)
    private int garageId;
    private String name;

    public ParkingGarage(String name, int garageId) {
        this.name = name;
        this.garageId = garageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingGarage that = (ParkingGarage) o;
        return garageId == that.garageId && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, garageId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGarageId() {
        return garageId;
    }

    public void setGarageId(int garageId) {
        this.garageId = garageId;
    }

}

