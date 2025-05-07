package com.example.parkingGarage.database.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class ParkingSpaceTest {

    @Test
    public void testEquals(){
        ParkingSpace space1 = new ParkingSpace(1, true, 3);
        ParkingSpace space2 = new ParkingSpace(1, true, 3);
        assertEquals(space1, space2);
        assertNotEquals(space1, space2);
    }

    @Test
    public void getAndSet(){
        ParkingSpace space = new ParkingSpace(1, true, 1);
        space.setFloorId(4);
        space.setSpaceNum(5);
        space.setOccupied(false);

        assertEquals(space.getFloorId(), 4);
        assertEquals(space.getSpaceNum(), 5);
        assertFalse(space.isOccupied());
    }
}
