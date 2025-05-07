package com.example.parkingGarage.database.entities;

import static org.junit.Assert.*;

import org.junit.Test;

public class ParkingFloorTest {

    @Test
    public void testEquals(){
        ParkingFloor floor1 = new ParkingFloor(1,20,15,10,5);
        ParkingFloor floor2 = new ParkingFloor(1,12,30,11,5);
        assertEquals(floor1, floor1);
        assertNotEquals(floor1, floor2);
    }


    @Test
    public void getAndSet(){
            ParkingFloor floor = new ParkingFloor(100, 2,75,10,5);
            //random numbers to initiate obj

            floor.setSpaceCount(101);
            assertEquals(101, floor.getSpaceCount());

            floor.setFloorNum(3);
            assertEquals(3, floor.getFloorNum());

            floor.setSpacesAvailable(76);
            assertEquals(76, floor.getSpacesAvailable());

            floor.setFloorId(11);
            assertEquals(11, floor.getFloorId());

            floor.setGarageId(6);
            assertEquals(6, floor.getGarageId());
        }
    }
