package com.example.parkingGarage.database;

import static com.example.parkingGarage.database.ParkingGarageDatabase.databaseWriteExecutor;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Database;

import com.example.parkingGarage.database.entities.ParkingFloor;
import com.example.parkingGarage.database.entities.ParkingGarage;
import com.example.parkingGarage.MainActivity;
import com.example.parkingGarage.database.entities.ParkingSpace;
import com.example.parkingGarage.database.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ParkingGarageRepository {
    private ParkingGarageDAO parkingGarageDAO;

    private ParkingFloorDAO parkingFloorDAO;

    private ParkingSpaceDAO parkingSpaceDAO;
    private final UserDAO userDAO;
    private ArrayList<ParkingGarage> allLogs;

    private static ParkingGarageRepository repository;

    private ParkingGarageRepository(Application application){
        ParkingGarageDatabase db = ParkingGarageDatabase.getDatabase(application);
        this.parkingGarageDAO = db.parkingGarageDAO();
        this.parkingFloorDAO = db.parkingFloorDAO();
        this.parkingSpaceDAO = db.parkingSpaceDAO();
        this.userDAO = db.userDAO();
//        this.allLogs = (ArrayList<ParkingGarage>) this.parkingGarageDAO.getAllRecords();
    }

    public LiveData<ParkingGarage> getGarageById(int garageId){
        return parkingGarageDAO.getGarageById(garageId);
    }
    public static ParkingGarageRepository getRepository(Application application){
        if(repository != null){
            return repository;
        }
        Future<ParkingGarageRepository> future = databaseWriteExecutor.submit(
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
        Future<ArrayList<ParkingGarage>> future = databaseWriteExecutor.submit(
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
    public ArrayList<ParkingFloor> getAllFloors(){
        Future<ArrayList<ParkingFloor>> future = databaseWriteExecutor.submit(
                new Callable<ArrayList<ParkingFloor>>() {
                    @Override
                    public ArrayList<ParkingFloor> call() throws Exception {
                        return (ArrayList<ParkingFloor>) parkingFloorDAO.getAllFloors();
                    }
                }
        );
        try{
            return future.get();
        }catch(InterruptedException | ExecutionException e){
            Log.i(MainActivity.TAG, "Problem when getting all ParkingFloors in the repository");
        }
        return null;
    }

    public LiveData<List<ParkingSpace>> getSpaceByFloorIdLiveData(int floorId){
        return parkingSpaceDAO.getSpaceByIdLiveData(floorId);
    }
    public ArrayList<ParkingSpace> getAllSpaces(){
        Future<ArrayList<ParkingSpace>> future = databaseWriteExecutor.submit(
                new Callable<ArrayList<ParkingSpace>>() {
                    @Override
                    public ArrayList<ParkingSpace> call() throws Exception {
                        return (ArrayList<ParkingSpace>) parkingSpaceDAO.getAllSpaces();
                    }
                }
        );
        try{
            return future.get();
        }catch(InterruptedException | ExecutionException e){
            Log.i(MainActivity.TAG, "Problem when getting all ParkingSpaces in the repository");
        }
        return null;
    }

    public void insertParkingLog(ParkingGarage parkingGarage){
        databaseWriteExecutor.execute(() ->
        {
            parkingGarageDAO.insert(parkingGarage);
        });
    }

    public void insertUser(User user){
        databaseWriteExecutor.execute(() ->
        {
            userDAO.insert(user);
        });
    }

    public LiveData<ParkingSpace> getSpaceById(int spaceId){
        return parkingSpaceDAO.getSpaceById(spaceId);
    }

    @Deprecated
    public void insertUser(User... user){
        databaseWriteExecutor.execute(() ->
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

    public LiveData<ParkingGarage> getGarageByFloorGarageId(int garageId){
        return parkingFloorDAO.getGarageByFloorGarageId(garageId);
    }

    public LiveData<ParkingFloor> getFloorBySpaceFloorId(int floorId){
        return parkingSpaceDAO.getFloorBySpaceFloorId(floorId);
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
