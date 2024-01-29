package com.example.lingapp.ui.ForgotPassword;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.lingapp.R;
import com.example.lingapp.utils.ForgotPasswordModel;

public class ForgotPasswordActivity extends AppCompatActivity implements IForgotPassword{
    private static final String REGEX_EMAIL = "^[a-z0-9](\\.?[a-z0-9]){5,}@g(oogle)?mail\\.com$";
    private int counterNumber = 30;
    private Button forgotPassword;
    private EditText emailET;
    private ForgotPasswordModel model;
    private ImageView customProgressBar;
    private Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        model = new ForgotPasswordModel(this);
        customProgressBar = findViewById(R.id.customProgressBar);
        forgotPassword = findViewById(R.id.forgotPassword);
        emailET = findViewById(R.id.emailET);

        forgotPassword.setOnClickListener(view -> {
            if (isCorrect()) {
                customProgressBar.startAnimation(animation);
                model.hasUser(emailET.getText().toString());
            }
        });
    }

    private void startCounter(){
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (counterNumber == 0){
                    forgotPassword.setBackgroundTintList(AppCompatResources.getColorStateList(getApplicationContext(), R.color.lightPrimary));
                    forgotPassword.setEnabled(true);
                    forgotPassword.setText(getString(R.string.forgot_password_prompt));
                    counterNumber = 30;
                    handler.removeCallbacks(this);
                    return;
                }
                counterNumber--;
                forgotPassword.setText(String.valueOf(counterNumber));
                forgotPassword.setTextColor(getColor(R.color.white));
                handler.removeCallbacks(this);
                handler.postDelayed(this, 1000);
            }
        });
    }

    private boolean isCorrect() {
        if (emailET.getText().toString().isEmpty()){
            emailET.setError("Please add your email address first");
            emailET.requestFocus();
            return false;
        }
        if (!emailET.getText().toString().matches(REGEX_EMAIL)){
            emailET.requestFocus();
            emailET.setError("Please enter valid email address");
            return false;
        }
        hideSoftKeyboard(emailET);
        return true;
    }
    public void hideSoftKeyboard(View view){
        InputMethodManager imm =(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void hasUser(boolean verdict) {
        customProgressBar.clearAnimation();
        if (verdict) {
            model.sendEmail(emailET.getText().toString());
            forgotPassword.setBackgroundTintList(AppCompatResources.getColorStateList(getApplicationContext(), R.color.black));
            forgotPassword.setEnabled(false);
            startCounter();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPasswordActivity.this);
        builder.setTitle("Forgot password warning")
                .setMessage("Sorry but there is no " + emailET.getText().toString() + " in our record, please double check if the provided email is correct.")
                .setPositiveButton("Try again", (dialogInterface, i) -> dialogInterface.dismiss())
                .create();

        builder.show();
    }

    @Override
    public void isSend(boolean verdict, String errorMessage) {

    }
}