package com.example.parkingGarage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;

import com.example.parkingGarage.database.ParkingGarageRepository;
import com.example.parkingGarage.database.entities.User;
import com.example.parkingGarage.databinding.ActivityQuickFindBinding;
import com.example.parkingGarage.databinding.ActivitySignUpBinding;

public class QuickFindActivity extends AppCompatActivity {

    private static final int LOGGED_OUT = -1;
    private int loggedInUserId = -1;
    private ParkingGarageRepository repository;

    private static final String QUICK_FIND_ACTIVITY_USER_ID = "com.example.parkingGarage.QUICK_FIND_ACTIVITY_USER_ID";

    static final String SAVED_INSTANCE_STATE_USERID_KEY = "com.example.parkingGarage.SAVED_INSTANCE_STATE_USERID_KEY";

    private ActivityQuickFindBinding binding;

    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuickFindBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        EdgeToEdge.enable(this);
        repository = ParkingGarageRepository.getRepository(getApplication());
        loginUser(savedInstanceState);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.QuickFindTextView), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.backToWelcomeUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MenuActivity.menuActivityIntentFactory(getApplicationContext(), user.getId());
                startActivity(intent);
            }
        });
    }
    static Intent quickFindFactory(Context context){
        return new Intent(context, QuickFindActivity.class);
    }
    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
            loggedInUserId = getIntent().getIntExtra(QUICK_FIND_ACTIVITY_USER_ID, LOGGED_OUT);
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