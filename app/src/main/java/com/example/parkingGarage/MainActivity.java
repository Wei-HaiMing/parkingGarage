package com.example.parkingGarage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parkingGarage.database.ParkingGarageRepository;
import com.example.parkingGarage.database.entities.ParkingFloor;
import com.example.parkingGarage.database.entities.ParkingSpace;
import com.example.parkingGarage.database.entities.User;
import com.example.parkingGarage.databinding.ActivityMainBinding;
import com.example.parkingGarage.viewHolders.ParkingGarageAdapter;
import com.example.parkingGarage.viewHolders.ParkingGarageViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String MAIN_ACTIVITY_USER_ID = "com.example.parkingGarage.MAIN_ACTIVITY_USER_ID";

    static final String SAVED_INSTANCE_STATE_USERID_KEY = "com.example.parkingGarage.SAVED_INSTANCE_STATE_USERID_KEY";
    private static final int LOGGED_OUT = -1;
    private ActivityMainBinding binding;
    private ParkingGarageRepository repository;
    private ParkingGarageViewModel parkingGarageViewModel;
    public static final String TAG = "DAC_GYMLOG";

    String parkingGarageName;
    private int garageId = -1;

    private String garageName = null;

    private int floorNum = -1;
    private int spaceNum = -1;
    private ArrayList<ParkingFloor> floorSearch = new ArrayList<>();
    private ArrayList<ParkingSpace> spaceSearch = new ArrayList<>();
    private int parkingGarageUserId;
    private int loggedInUserId = -1;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.parkingGarage.databinding.ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        parkingGarageViewModel = new ViewModelProvider(this).get(ParkingGarageViewModel.class);


        RecyclerView recyclerView = binding.logDisplayRecyclerView;
        final ParkingGarageAdapter adapter = new ParkingGarageAdapter(new ParkingGarageAdapter.ParkingGarageDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        repository = ParkingGarageRepository.getRepository(getApplication());
        loginUser(savedInstanceState);


//        parkingGarageViewModel.getAllFloorsById(loggedInUserId).observe(this, gymLogs -> {
//            adapter.submitList(gymLogs);
//        });

        // User is not logged in at this point, go to login screen
//        if(loggedInUserId == -1){
//            Intent intent = LandingActivity.landingActivityIntentFactory(getApplicationContext());
//            startActivity(intent);
//        }
//        else{
//            LiveData<Boolean> userObserver = repository.getAdminStatus(loggedInUserId);
//            userObserver.observe(this, adminStatus -> {
//                if(adminStatus){
//                    binding.adminAreaButton.setVisibility(View.VISIBLE);
//                }
//                else{
//                    binding.adminAreaButton.setVisibility(View.INVISIBLE);
//                }
//            });
//        }
        updateSharedPreference();

        // TODO: implement search for a parking spot
        binding.searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getInformationFromDisplay();
                if(floorSearch.size() != 0){
                    floorSearch.clear();
                }
                if(spaceSearch.size() != 0){
                    spaceSearch.clear();
                }
                if(garageId != -1){
                    ArrayList<ParkingFloor> allFloors = repository.getAllFloors();
                    ArrayList<ParkingSpace> allSpaces = repository.getAllSpaces();
                    if(floorNum == -1 && spaceNum == -1){ // search all floors for all open spaces
                        for(int i = 0; i < allFloors.size(); i++) {
                            if (allFloors.get(i).getGarageId() == garageId) {
                                floorSearch.add(allFloors.get(i));
                            }
                        }
                        for(int i = 0; i < allSpaces.size(); i++){
                            if(allSpaces.get(i).getFloorId() == floorNum && !allSpaces.get(i).isOccupied()){
                                spaceSearch.add(allSpaces.get(i));
                            }
                        }
                        Toast.makeText(MainActivity.this, "Floor and Space were empty", Toast.LENGTH_SHORT).show();
                        printFloors(floorSearch);
                        printSpaces(spaceSearch);
                    }else if(spaceNum == -1){ // search everything in floor
                        floorSearch.add(repository.getFloorById(floorNum).getValue());
                        for(int i = 0; i < allSpaces.size(); i++){
                            if(allSpaces.get(i).getFloorId() == floorNum && !allSpaces.get(i).isOccupied()){
                                spaceSearch.add(allSpaces.get(i));
                            }
                        }
                        Toast.makeText(MainActivity.this, "Space was empty", Toast.LENGTH_SHORT).show();
                        printFloors(floorSearch);
                        printSpaces(spaceSearch);
                    }else{

                        try {
                            floorSearch.add(repository.getFloorById(floorNum).getValue());
                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Error Reading Floor Number " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        try {
                            spaceSearch.add(repository.getSpaceById(spaceNum).getValue());
                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Error Reading Space Number " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(MainActivity.this, "Found Space since space was entered", Toast.LENGTH_SHORT).show();

                        printFloors(floorSearch);
                        printSpaces(spaceSearch);
                    }


                }else{
                    Toast.makeText(MainActivity.this, "You must enter a Garage Name", Toast.LENGTH_SHORT).show();
                }
//                insertGymLogRecord();
            }
        });

        binding.mainBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MenuActivity.menuActivityIntentFactory(getApplicationContext(), user.getId());
                startActivity(intent);
            }
        });
    }

    private void getInformationFromDisplay(){
        try {
            garageId = Integer.parseInt(binding.exerciseInputEditText.getText().toString());
        }catch(Exception e){
            Log.d(TAG, "Error reading value from garage number edit text.");
            garageId = -1;
        }
        try {
            floorNum = Integer.parseInt(binding.weightInputEditText.getText().toString());
        }catch(NumberFormatException e){
            Log.d(TAG, "Error reading value from floor number edit text.");
            floorNum = -1;
        }

        try {
            spaceNum = Integer.parseInt(binding.repInputEditText.getText().toString());
        }catch(NumberFormatException e){
            Log.d(TAG, "Error reading value from space number edit text.");
            spaceNum = -1;
        }
    }

    public void printFloors(ArrayList<ParkingFloor> floors){
        for(int i = 0; i < floors.size(); i++){
            System.out.println(floors.get(i));
        }
    }

    public void printSpaces(ArrayList<ParkingSpace> spaces){
        for(int i = 0; i < spaces.size(); i++){
            System.out.println(spaces.get(i));
        }
    }


    private void loginUser(Bundle savedInstanceState) {
        //check shared preference for logged in user
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        loggedInUserId = sharedPreferences.getInt(getString(R.string.preference_userId_key), LOGGED_OUT);
        if(loggedInUserId == LOGGED_OUT & savedInstanceState != null && savedInstanceState.containsKey(SAVED_INSTANCE_STATE_USERID_KEY)){
            loggedInUserId = savedInstanceState.getInt(SAVED_INSTANCE_STATE_USERID_KEY, LOGGED_OUT);
        }
        if(loggedInUserId == LOGGED_OUT){
            loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
        }
        if(loggedInUserId == LOGGED_OUT){
            return;
        }
        LiveData<User> userObserver = repository.getUserByUserId(loggedInUserId);
        userObserver.observe(this, user -> {
            this.user = user;
            if(this.user != null){
                invalidateOptionsMenu();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_INSTANCE_STATE_USERID_KEY, loggedInUserId);
        updateSharedPreference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.logoutMenuItem);
        item.setVisible(true);
        if(user == null){
            return false;
        }
        item.setTitle(user.getUsername());
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                showLogoutDialog();
                return false;
            }
        });
        return true;
    }

    private void showLogoutDialog(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        final AlertDialog alertDialog = alertBuilder.create();

        alertBuilder.setMessage("Logout?");

        alertBuilder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        });

        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertBuilder.create().show();
    }

    private void logout() {
        loggedInUserId = LOGGED_OUT;
        updateSharedPreference();
        getIntent().putExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);

        startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
    }

    private void updateSharedPreference(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(getString(R.string.preference_userId_key), loggedInUserId);
        sharedPrefEditor.apply();

    }

    static Intent mainActivityIntentFactory(Context context){
        return new Intent(context, MainActivity.class);
    }

//    private void insertGymLogRecord(){
//        if(mExercise.isEmpty()){
//            return;
//        }
//        ParkingGarage log = new ParkingGarage();
//        repository.insertParkingLog(log);
//    }

//    @Deprecated
//    private void updateDisplay(){
//        ArrayList<ParkingGarage> allLogs = repository.getAllLogsByUserId(loggedInUserId);
//        if(allLogs.isEmpty()){
////            binding.logDisplayTextView.setText(R.string.nothing_to_show_time_to_hit_the_gym);
//        }
//        StringBuilder sb = new StringBuilder();
//        for(ParkingGarage log : allLogs){
//            sb.append(log);
//        }
////        binding.logDisplayTextView.setText(sb.toString());
//    }


}