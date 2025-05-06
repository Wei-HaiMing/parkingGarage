package com.example.parkingGarage;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.parkingGarage.database.entities.ParkingGarage;
import com.example.parkingGarage.database.entities.User;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    // User START
    @Test
    public void userFunctions(){
        User user1 = new User("alex", "alex1234", "alex@gmail.com");

        assertNotNull(user1);
        assertEquals("alex", user1.getUsername());
        assertEquals("alex1234", user1.getPassword());
        assertEquals("alex@gmail.com", user1.getEmail());
        assertFalse(user1.isAdmin());
        assertEquals(-1, user1.getOccupiedSpaceId());

        user1.setUsername("jocy");
        assertEquals("jocy", user1.getUsername());
        user1.setPassword("jocy1234");
        assertEquals("jocy1234", user1.getPassword());
        user1.setEmail("jocy@gmail.com");
        assertEquals("jocy@gmail.com", user1.getEmail());
        user1.setAdmin(true);
        assertTrue(user1.isAdmin());
        user1.setOccupiedSpaceId(43);
        assertEquals(43, user1.getOccupiedSpaceId());

        User user2 = new User("jocy", "jocy1234", "jocy@gmail.com");
        user2.setAdmin(true);
        user2.setOccupiedSpaceId(43);
        assertEquals(user1, user2);
    }
    // User END

    // Garage START
    @Test
    public void garageFunctions(){
        ParkingGarage garage1 = new ParkingGarage("Salinas Garage");

        assertEquals("Salinas Garage", garage1.getName());

        garage1.setName("Some Garage");
        assertEquals("Some Garage", garage1.getName());

        ParkingGarage garage2 = new ParkingGarage("Some Garage");

        assertEquals(garage1, garage2);
    }
    // Garage END
}