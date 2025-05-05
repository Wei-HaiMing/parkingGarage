package com.example.parkingGarage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;

import com.example.parkingGarage.database.ParkingGarageRepository;
import com.example.parkingGarage.database.entities.User;
import com.example.parkingGarage.databinding.ActivityCurrentSpaceBinding;
import com.example.parkingGarage.databinding.ActivityQuickFindBinding;

public class CurrentSpaceActivity extends AppCompatActivity {

    private static final int LOGGED_OUT = -1;
    private int loggedInUserId = -1;
    private ParkingGarageRepository repository;

    private static final String CURRENT_SPACE_ACTIVITY_ID = "com.example.parkingGarage.CURRENT_SPACE_ACTIVITY_ID";

    static final String SAVED_INSTANCE_STATE_USERID_KEY = "com.example.parkingGarage.SAVED_INSTANCE_STATE_USERID_KEY";

    private ActivityCurrentSpaceBinding binding;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCurrentSpaceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = ParkingGarageRepository.getRepository(getApplication());
        EdgeToEdge.enable(this);
        loginUser(savedInstanceState);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.CurrentSpaceTextView), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.CurrentSpaceBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MenuActivity.menuActivityIntentFactory(getApplicationContext(), user.getId());
                startActivity(intent);
            }
        });
    }
    static Intent currentSpaceFactory(Context context){
        return new Intent(context, CurrentSpaceActivity.class);
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
            loggedInUserId = getIntent().getIntExtra(CURRENT_SPACE_ACTIVITY_ID, LOGGED_OUT);
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
}