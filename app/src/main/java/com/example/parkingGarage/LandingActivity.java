package com.example.parkingGarage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.parkingGarage.databinding.ActivityLandingBinding;

public class LandingActivity extends AppCompatActivity {

    private ActivityLandingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityLandingBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());


        binding.activityLandingLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
            }
        });

        binding.activityLandingSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(SignUpActivity.signUpIntentFactory(getApplicationContext()));
            }
        });

    }



    static Intent landingActivityIntentFactory(Context context){
        return new Intent(context, LandingActivity.class);
    }
}