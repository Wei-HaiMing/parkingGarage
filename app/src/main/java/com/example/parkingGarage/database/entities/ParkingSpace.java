package com.example.parkingGarage.database.entities;

public class ParkingSpace{
    private int spaceNum;
    private boolean occupied;
    private int spaceId;
    private int floorId;
    public ParkingSpace(int spaceNum, boolean occupied, int spaceId, int floorId) {
        this.spaceNum = spaceNum;
        this.occupied = occupied;
        this.spaceId = spaceId;
        this.floorId = floorId;
    }
}

