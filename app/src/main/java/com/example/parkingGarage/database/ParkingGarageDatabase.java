package com.example.parkingGarage.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.parkingGarage.database.entities.ParkingFloor;
import com.example.parkingGarage.database.entities.ParkingGarage;
import com.example.parkingGarage.MainActivity;
import com.example.parkingGarage.database.entities.ParkingSpace;
import com.example.parkingGarage.database.entities.User;
import com.example.parkingGarage.database.typeConverters.LocalDateTypeConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@TypeConverters(LocalDateTypeConverter.class)
@Database(entities = {ParkingGarage.class, User.class, ParkingFloor.class, ParkingSpace.class}, version = 5, exportSchema = false)
public abstract class ParkingGarageDatabase extends RoomDatabase {

    public static final String USER_TABLE = "usertable";
    private static final String DATABASE_NAME = "ParkingGarageDatabase";
    public static final String PARKING_GARAGE_TABLE = "parkingGarageTable";

    public static final String PARKING_FLOOR_TABLE = "parkingFloorTable";

    public static final String PARKING_SPACE_TABLE = "parkingSpaceTable";

    private static volatile ParkingGarageDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    static ParkingGarageDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (ParkingGarageDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            ParkingGarageDatabase.class,
                                    DATABASE_NAME
                            )
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            Log.i(MainActivity.TAG, "DATABASE CREATED!"); //TODO: need to add separate route to get proper ids off garage and floors, add query to daos to retrieve id
            databaseWriteExecutor.execute(() -> {
                UserDAO dao = INSTANCE.userDAO();
                dao.deleteAll();
                User admin = new User("admin1", "admin1", "admin@admin.com");
                admin.setAdmin(true);
                dao.insert(admin);
                User testUser1 = new User("testUser1", "testUser1", "user1@users.com");
                dao.insert(testUser1);

                ParkingGarageDAO pgdao = INSTANCE.parkingGarageDAO();
                ParkingFloorDAO pfdao = INSTANCE.parkingFloorDAO();
                ParkingSpaceDAO psdao = INSTANCE.parkingSpaceDAO();
                pgdao.deleteAll();
                pfdao.deleteAll();
                psdao.deleteAll();

                ParkingGarage garage1 = new ParkingGarage("Salinas Parking Garage");
                pgdao.insert(garage1);

                ParkingFloor pg1f1 = new ParkingFloor(10, 1, 10, garage1.getGarageId());
                ParkingFloor pg1f2 = new ParkingFloor(10, 2, 10, garage1.getGarageId());
                ParkingFloor pg1f3 = new ParkingFloor(10, 3, 10, garage1.getGarageId());
                pfdao.insert(pg1f1);
                pfdao.insert(pg1f2);
                pfdao.insert(pg1f3);

                for(int j = 0; j < 10; j++){
                    ParkingSpace psgen = new ParkingSpace(j + 1, false, pg1f1.getFloorId());
                    psdao.insert(psgen);
                }
                for(int j = 0; j < 10; j++){
                    ParkingSpace psgen = new ParkingSpace(j + 1, false, pg1f2.getFloorId());
                    psdao.insert(psgen);
                }
                for(int j = 0; j < 10; j++){
                    ParkingSpace psgen = new ParkingSpace(j + 1, false, pg1f2.getFloorId());
                    psdao.insert(psgen);
                }
            });
        }
    };

    public abstract ParkingGarageDAO parkingGarageDAO();

    public abstract UserDAO userDAO();

    public abstract ParkingFloorDAO parkingFloorDAO();

    public abstract ParkingSpaceDAO parkingSpaceDAO();
}
