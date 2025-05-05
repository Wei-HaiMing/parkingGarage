package com.example.parkingGarage.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.parkingGarage.database.ParkingGarageDatabase;

import java.util.Objects;

@Entity(tableName = ParkingGarageDatabase.USER_TABLE)
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String username;
    private String password;

    private int occupiedSpaceId;
    private String email;
    private boolean isAdmin;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        isAdmin = false;
        occupiedSpaceId = -1;
    }

    public int getOccupiedSpaceId() {
        return occupiedSpaceId;
    }

    public void setOccupiedSpaceId(int occupiedSpaceId) {
        this.occupiedSpaceId = occupiedSpaceId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && occupiedSpaceId == user.occupiedSpaceId && isAdmin == user.isAdmin && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, occupiedSpaceId, email, isAdmin);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }




}
