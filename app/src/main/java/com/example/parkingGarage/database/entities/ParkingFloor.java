package com.example.parkingGarage.database.entities;

public class ParkingFloor {
    private int spaceCount;
    private int floorNum;
    private int spacesAvailable;
    private int floorId;
    private int garageId;

    public ParkingFloor(int spaceCount, int floorNum, int spacesAvailable, int floorId, int garageId) {
        this.spaceCount = spaceCount;
        this.floorNum = floorNum;
        this.spacesAvailable = spacesAvailable;
        this.floorId = floorId;
        this.garageId = garageId;
    }

}