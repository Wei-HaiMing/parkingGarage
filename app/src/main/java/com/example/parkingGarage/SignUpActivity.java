package com.example.parkingGarage;

import static com.example.parkingGarage.LoginActivity.loginIntentFactory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.parkingGarage.database.ParkingGarageDatabase;
import com.example.parkingGarage.database.ParkingGarageRepository;
import com.example.parkingGarage.database.UserDAO;
import com.example.parkingGarage.database.entities.User;
import com.example.parkingGarage.databinding.ActivitySignUpBinding;


public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;

    private ParkingGarageRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = ParkingGarageRepository.getRepository(getApplication());



        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                verifyUser();
            }
        });

        binding.signUpBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LandingActivity.landingActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    private void verifyUser(){
        String username = binding.signUpUserNameEditText.getText().toString();
        String email = binding.signUpEmailEditText.getText().toString();
        String password = binding.signUpPasswordEditText.getText().toString();
        String confirmPassword = binding.signUpConfirmPasswordEditText.getText().toString();

        if(username.length() < 2){
            toastMaker("Username too small, please extend it.");
            return;
        }

        if(!email.contains("@")){
            toastMaker("Email has no service provider, please add it.");
            return;
        }

        if(password.length() < 3){
            toastMaker("Password too small, please extend it.");
            return;
        }

        if(!password.equals(confirmPassword)){
            toastMaker("The passwords do not match, please rewrite it.");
            return;
        }

        toastMaker("Hello " + username + "! Sign in to get Started!");
        uploadUserToDatabase(username, password, email);
        Intent intent = loginIntentFactory(getApplicationContext());
        startActivity(intent);

//        String username = binding.userNameLoginEditText.getText().toString();
//        if(username.isEmpty()){
//            toastMaker("Username should not be blank");
//            return;
//        }
//        LiveData<User> userObserver = repository.getUserByUserName(username);
//        userObserver.observe(this, user -> {
//            if(user != null){
//                String password = binding.passwordLoginEditText.getText().toString();
//                if(password.equals(user.getPassword())){
//                    startActivity(LandingActivity.mainActivityIntentFactory(getApplicationContext(), user.getId()));
//                } else {
//                    toastMaker("Invalid password");
//                    binding.passwordLoginEditText.setSelection(0);
//                }
//            }else{
//                toastMaker(String.format("%s is not a valid username.", username));
//                binding.userNameLoginEditText.setSelection(0);
//            }
//        });
    }

    private void uploadUserToDatabase(String username, String password, String email){
        //todo Upload the valid user data to the database. Use repository command and add functions. Use DAO and add functions
        User user = new User(username, password, email);
        repository.insertUser(user);
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    static Intent signUpIntentFactory(Context context){
        return new Intent(context, SignUpActivity.class);
    }
}