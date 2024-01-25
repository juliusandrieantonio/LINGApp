
package com.example.lingapp.ui.LoginPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lingapp.R;
import com.example.lingapp.ui.HomePage.HomePageActivity;
import com.example.lingapp.ui.RegistrationPage.RegistrationPageActivity;
import com.example.lingapp.utils.LoginPageModel;

public class LoginPageActivity extends AppCompatActivity implements ILoginPage {
    private EditText usernameET, passwordET;
    private LoginPageModel model;
    private Animation animation;
    private ImageView customProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        customProgressBar = findViewById(R.id.customProgressBar);
        usernameET = findViewById(R.id.usernameET);
        passwordET = findViewById(R.id.passwordET);
        Button login = findViewById(R.id.logIn);
        Button signup = findViewById(R.id.signUp);
        model = new LoginPageModel(this);
        login.setOnClickListener(view -> {
            String username = usernameET.getText().toString();
            String password = passwordET.getText().toString();
            customProgressBar.startAnimation(animation);
            model.signIn(username, password);
        });
        signup.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), RegistrationPageActivity.class)));

    }

    @Override
    public void onLogin(boolean verdict, String message) {
        customProgressBar.clearAnimation();
        if (verdict) {
            Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
            finish();
            return;
        }
        Toast.makeText(this, "Login Failed, " + message, Toast.LENGTH_SHORT).show();
    }
}