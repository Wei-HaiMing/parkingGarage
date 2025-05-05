package com.example.parkingGarage.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.parkingGarage.database.entities.ParkingFloor;
import com.example.parkingGarage.database.entities.ParkingGarage;
import com.example.parkingGarage.MainActivity;
import com.example.parkingGarage.database.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ParkingGarageRepository {
    private ParkingGarageDAO parkingGarageDAO;

    private ParkingFloorDAO parkingFloorDAO;

//    private ParkingSpaceDAO parkingSpaceDAO;
    private final UserDAO userDAO;
    private ArrayList<ParkingGarage> allLogs;

    private static ParkingGarageRepository repository;

    private ParkingGarageRepository(Application application){
        ParkingGarageDatabase db = ParkingGarageDatabase.getDatabase(application);
        this.parkingGarageDAO = db.parkingLotDAO();
        this.parkingFloorDAO = db.parkingFloorDAO();
        this.userDAO = db.userDAO();
//        this.allLogs = (ArrayList<ParkingGarage>) this.parkingGarageDAO.getAllRecords();
    }

    public static ParkingGarageRepository getRepository(Application application){
        if(repository != null){
            return repository;
        }
        Future<ParkingGarageRepository> future = ParkingGarageDatabase.databaseWriteExecutor.submit(
                new Callable<ParkingGarageRepository>() {
                    @Override
                    public ParkingGarageRepository call() throws Exception {
                        return new ParkingGarageRepository(application);
                    }
                }
        );
        try{
            return future.get();
        }catch(InterruptedException | ExecutionException e){
            Log.d(MainActivity.TAG, "Problem getting ParkingGarageRepository, thread error.");
        }
        return null;
    }

    public ArrayList<ParkingGarage> getAllLogs(){
        Future<ArrayList<ParkingGarage>> future = ParkingGarageDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<ParkingGarage>>() {
                    @Override
                    public ArrayList<ParkingGarage> call() throws Exception {
                        return (ArrayList<ParkingGarage>) parkingGarageDAO.getAllGarages();
                    }
                }
        );
        try{
            return future.get();
        }catch(InterruptedException | ExecutionException e){
            Log.i(MainActivity.TAG, "Problem when getting all ParkingGarageLog in the repository");
        }
        return null;
    }

    public void insertParkingLog(ParkingGarage parkingGarage){
        ParkingGarageDatabase.databaseWriteExecutor.execute(() ->
        {
            parkingGarageDAO.insert(parkingGarage);
        });
    }

    public void insertUser(User user){
        ParkingGarageDatabase.databaseWriteExecutor.execute(() ->
        {
            userDAO.insert(user);
        });
    }



    @Deprecated
    public void insertUser(User... user){
        ParkingGarageDatabase.databaseWriteExecutor.execute(() ->
        {
            userDAO.insert(user);
        });
    }

    public LiveData<User> getUserByUserName(String username) {
        return userDAO.getUserByName(username);
    }

    public LiveData<ParkingFloor> getFloorById(int floorId){
        return parkingFloorDAO.getFloorByFloorId(floorId);
    }

    public LiveData<User> getUserByUserId(int userId) {
        return userDAO.getUserByUserId(userId);
    }
    public LiveData<Boolean> getAdminStatus(int userId) {
        return userDAO.getAdminStatus(userId);
    }

//    public LiveData<List<ParkingGarage>> getAllLogsByUserIdLiveData(int loggedInUserId){
//        return parkingGarageDAO.getRecordsetUserIdLiveData(loggedInUserId);
//    }

//    @Deprecated
//    public ArrayList<ParkingGarage> getAllLogsByUserId(int loggedInUserId) {
//        Future<ArrayList<ParkingGarage>> future = ParkingGarageDatabase.databaseWriteExecutor.submit(
//                new Callable<ArrayList<ParkingGarage>>() {
//                    @Override
//                    public ArrayList<ParkingGarage> call() throws Exception {
//                        return (ArrayList<ParkingGarage>) parkingGarageDAO.getRecordsetUserId(loggedInUserId);
//                    }
//                }
//        );
//        try{
//            return future.get();
//        }catch(InterruptedException | ExecutionException e){
//            Log.i(MainActivity.TAG, "Problem when getting all ParkingLogs in the repository");
//        }
//        return null;
//    }
}
