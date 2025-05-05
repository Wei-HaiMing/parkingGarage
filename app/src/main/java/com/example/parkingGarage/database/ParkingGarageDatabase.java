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
            Log.i(MainActivity.TAG, "DATABASE CREATED!");
            databaseWriteExecutor.execute(() -> {
                UserDAO dao = INSTANCE.userDAO();
                dao.deleteAll();
                User admin = new User("admin1", "admin1", "admin@admin.com");
                admin.setAdmin(true);
                dao.insert(admin);
                User testUser1 = new User("testUser1", "testUser1", "user1@users.com");
                dao.insert(testUser1);
            });
        }
    };

    public abstract ParkingGarageDAO parkingLotDAO();

    public abstract UserDAO userDAO();

    public abstract ParkingFloorDAO parkingFloorDAO();
}
