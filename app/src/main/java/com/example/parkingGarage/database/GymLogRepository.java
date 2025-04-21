package com.example.parkingGarage.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.parkingGarage.database.entities.ParkingGarage;
import com.example.parkingGarage.LandingActivity;
import com.example.parkingGarage.database.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class GymLogRepository {
    private GymLogDAO gymLogDAO;
    private final UserDAO userDAO;
    private ArrayList<ParkingGarage> allLogs;

    private static GymLogRepository repository;

    private GymLogRepository(Application application){
        GymLogDatabase db = GymLogDatabase.getDatabase(application);
        this.gymLogDAO = db.gymLogDAO();
        this.userDAO = db.userDAO();
        this.allLogs = (ArrayList<ParkingGarage>) this.gymLogDAO.getAllRecords();
    }

    public static GymLogRepository getRepository(Application application){
        if(repository != null){
            return repository;
        }
        Future<GymLogRepository> future = GymLogDatabase.databaseWriteExecutor.submit(
                new Callable<GymLogRepository>() {
                    @Override
                    public GymLogRepository call() throws Exception {
                        return new GymLogRepository(application);
                    }
                }
        );
        try{
            return future.get();
        }catch(InterruptedException | ExecutionException e){
            Log.d(LandingActivity.TAG, "Problem getting GymLogRepository, thread error.");
        }
        return null;
    }

    public ArrayList<ParkingGarage> getAllLogs(){
        Future<ArrayList<ParkingGarage>> future = GymLogDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<ParkingGarage>>() {
                    @Override
                    public ArrayList<ParkingGarage> call() throws Exception {
                        return (ArrayList<ParkingGarage>) gymLogDAO.getAllRecords();
                    }
                }
        );
        try{
            return future.get();
        }catch(InterruptedException | ExecutionException e){
            Log.i(LandingActivity.TAG, "Problem when getting all GymLogs in the repository");
        }
        return null;
    }

    public void insertGymLog(ParkingGarage parkingGarage){
        GymLogDatabase.databaseWriteExecutor.execute(() ->
        {
            gymLogDAO.insert(parkingGarage);
        });
    }

    public void insertUser(User... user){
        GymLogDatabase.databaseWriteExecutor.execute(() ->
        {
            userDAO.insert(user);
        });
    }

    public LiveData<User> getUserByUserName(String username) {
        return userDAO.getUserByName(username);
    }

    public LiveData<User> getUserByUserId(int userId) {
        return userDAO.getUserByUserId(userId);
    }

    public LiveData<List<ParkingGarage>> getAllLogsByUserIdLiveData(int loggedInUserId){
        return gymLogDAO.getRecordsetUserIdLiveData(loggedInUserId);
    }
    @Deprecated
    public ArrayList<ParkingGarage> getAllLogsByUserId(int loggedInUserId) {
        Future<ArrayList<ParkingGarage>> future = GymLogDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<ParkingGarage>>() {
                    @Override
                    public ArrayList<ParkingGarage> call() throws Exception {
                        return (ArrayList<ParkingGarage>) gymLogDAO.getRecordsetUserId(loggedInUserId);
                    }
                }
        );
        try{
            return future.get();
        }catch(InterruptedException | ExecutionException e){
            Log.i(LandingActivity.TAG, "Problem when getting all GymLogs in the repository");
        }
        return null;
    }
}
