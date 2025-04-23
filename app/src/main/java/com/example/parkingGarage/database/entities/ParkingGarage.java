package com.example.parkingGarage.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.parkingGarage.database.ParkingGarageDatabase;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(tableName = ParkingGarageDatabase.PARKING_LOG_TABLE)
public class ParkingGarage {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String exercise;
    private double weight;
    private int reps;
    private LocalDateTime date;
    private int userId;

    public ParkingGarage(String exercise, double weight, int reps, int userId) {
        this.exercise = exercise;
        this.weight = weight;
        this.reps = reps;
        this.userId = userId;
        date = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return  "garage: " + exercise + '\n' +
                "floor: " + weight + '\n' +
                "spot" + reps + '\n' +
                '\n' +
                "=-=-=--=-=-=\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingGarage parkingGarage = (ParkingGarage) o;
        return id == parkingGarage.id && Double.compare(weight, parkingGarage.weight) == 0 && reps == parkingGarage.reps && userId == parkingGarage.userId && Objects.equals(exercise, parkingGarage.exercise) && Objects.equals(date, parkingGarage.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, exercise, weight, reps, date, userId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
