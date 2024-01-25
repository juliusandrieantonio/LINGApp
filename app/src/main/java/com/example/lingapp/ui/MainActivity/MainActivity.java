package com.example.lingapp.ui.MainActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.lingapp.R;
import com.example.lingapp.ui.BMICalculator.BMICalculatorActivity;
import com.example.lingapp.ui.HomePage.HomePageActivity;
import com.example.lingapp.ui.LoginPage.LoginPageActivity;
import com.example.lingapp.ui.RegistrationPage.RegistrationPageActivity;
import com.example.lingapp.utils.MainActivityModel;

public class MainActivity extends AppCompatActivity implements IMainActivity{
    private MainActivityModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        model = new MainActivityModel(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        model.isSignedIn();
    }

    @Override
    public void isSignedIn(boolean verdict) {
        if (verdict) {
            startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
            finish();
            return;
        }
        startActivity(new Intent(getApplicationContext(), LoginPageActivity.class));
        finish();
    }
}