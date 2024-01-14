package com.example.lingapp.ui.RegistrationPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.ViewCompat;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lingapp.R;
import com.example.lingapp.utils.RegistrationPageModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RegistrationPageActivity extends AppCompatActivity {
    private final Calendar calendar = Calendar.getInstance();
    private final String myDateFormat = "MM/dd/yy";
    private final SimpleDateFormat dateFormat = new SimpleDateFormat(myDateFormat, Locale.US);
    private ArrayList<View> levels;
    private ArrayList<LinearLayout> prompts;
    private TextView progress, birthday, headerPrompt;
    private int currentLevel = 0, userAge = 0;
    private EditText nameET, middleNameET, surnameET, weightET, heightET, ageET, usernameET, emailET, passwordET;
    private String finalName = "", finalMiddleName = "", finalSurname = "", finalWeight = "", finalHeight = "", finalBirthday = "", finalAge = "", finalGender = "", finalExperience = "", finalUsername = "", finalEmail = "", finalPassword = "";
    private Button navButton;
    private boolean isStart = true, isValid = false, isValidUsername = false;

    private static final String REGEX_EMAIL = "^[a-z0-9](\\.?[a-z0-9]){5,}@g(oogle)?mail\\.com$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        MaterialToolbar toolbar = findViewById(R.id.materialToolbar);

        LinearLayout namePrompt = findViewById(R.id.namePrompt);
        LinearLayout birthdayPrompt = findViewById(R.id.birthdayPrompt);
        LinearLayout birthdayPicker = findViewById(R.id.birthdatePicker);
        LinearLayout bmiPrompt = findViewById(R.id.bmiPrompt);
        LinearLayout genderPrompt = findViewById(R.id.genderPrompt);
        LinearLayout credentialsPrompt = findViewById(R.id.credentialsPrompt);


        View level1 = findViewById(R.id.level1);
        View level2 = findViewById(R.id.level2);
        View level3 = findViewById(R.id.level3);
        View level4 = findViewById(R.id.level4);


        nameET = findViewById(R.id.nameET);
        middleNameET = findViewById(R.id.middleNameET);
        surnameET = findViewById(R.id.surnameET);
        weightET = findViewById(R.id.weightET);
        heightET = findViewById(R.id.heightET);
        ageET = findViewById(R.id.ageET);
        birthday = findViewById(R.id.birthday);
        headerPrompt = findViewById(R.id.headerPrompt);
        emailET = findViewById(R.id.emailET);
        usernameET = findViewById(R.id.usernameET);
        passwordET = findViewById(R.id.passwordET);
        progress = findViewById(R.id.progress);

        LinearLayout male = findViewById(R.id.male);
        LinearLayout female = findViewById(R.id.female);

        ArrayList<LinearLayout> genders = new ArrayList<>();
        genders.add(male);
        genders.add(female);

        levels = new ArrayList<>();
        levels.add(level1);
        levels.add(level2);
        levels.add(level3);
        levels.add(level4);


        prompts = new ArrayList<>();
        prompts.add(namePrompt);
        prompts.add(bmiPrompt);
        prompts.add(birthdayPrompt);
        prompts.add(genderPrompt);
        prompts.add(credentialsPrompt);

        // clicking next
        navButton = findViewById(R.id.navButton);
        navButton.setOnClickListener(view -> {
            if (isStart){
                progressView(levels.get(currentLevel), R.color.lightPrimary);
                progressLayout(prompts.get(currentLevel), 1);
                isStart = false;
                headerPrompt.setText(getString(R.string.header_prompt));
                navButton.setText(getString(R.string.next));
                String levelText = ((currentLevel + 1) * 12.5) + "%";
                progress.setText(levelText);
                return;
            }

            if (isCorrect()){
                if (currentLevel >= 5){
//                    customProgressBar.startAnimation(animation);
//                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                    String imageSrc = (isUser ? user.getPhotoUrl().toString(): null);
//                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
//                    Date date = new Date();
//                    RegistrationPageModel model = new RegistrationPageModel(finalName, finalMiddleName, finalSurname, finalName, finalWeight, finalHeight, finalBirthday, finalAge, finalGender, finalExperience, finalUsername, finalEmail, formatter.format(date), imageSrc);
//                    presenter.createNewUser(finalEmail, finalPassword, model, isUser);
                    return;
                }
                progress();
            }

        });
// initializing birthday text by the current date
        birthday.setText(dateFormat.format(calendar.getTime()));
        int curYear = calendar.get(Calendar.YEAR);

        // getting the picked birthday
        DatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH,month);
            calendar.set(Calendar.DAY_OF_MONTH,day);
            birthday.setTextColor(getColor(R.color.black));
            birthday.setText(dateFormat.format(calendar.getTime()));
            userAge = curYear - year;
            String ageString = userAge + " yrs old";
            ageET.setText(ageString);
            finalBirthday = birthday.getText().toString();
        };


        // picking birthdate
        birthdayPicker.setOnClickListener(view -> {
            new DatePickerDialog(RegistrationPageActivity.this, date,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH))
                    .show();
            ageET.setError(null);
        });

        // controls for picking gender
        male.setOnClickListener(view -> {
            finalGender = "Male";
            removePicks(genders);
//            male.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.selector_border));
        });

        female.setOnClickListener(view -> {
            finalGender = "Female";
            removePicks(genders);
//            female.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.selector_border));
        });

        toolbar.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationPageActivity.this);
            builder.setTitle("Warning Notice")
                    .setMessage("Are you sure you want to go back to sign in page?")
                    .setPositiveButton("Yes", (dialogInterface, i) -> super.onBackPressed())
                    .setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss())
                    .create();

            builder.show();
        });

    }
    private void removePicks(ArrayList<LinearLayout> layouts) {
        for (LinearLayout layout: layouts){
            layout.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.color.white));
        }
    }
    private boolean isCorrect(){
        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationPageActivity.this);
        switch (currentLevel) {
            case 0:
                if (finalName.isEmpty() && finalSurname.isEmpty()) {
                    if (nameET.getText().toString().isEmpty()){
                        nameET.setError("Please add your given name first");
                        nameET.requestFocus();
                        return false;
                    }
                    if (surnameET.getText().toString().isEmpty()){
                        surnameET.setError("Please add your surname first");
                        surnameET.requestFocus();
                        return false;
                    }
                    finalName = nameET.getText().toString();
                    finalMiddleName = middleNameET.getText().toString();
                    finalSurname = surnameET.getText().toString();
                }
                hideSoftKeyboard(surnameET);
                break;

            case 1:
                if (finalWeight.isEmpty() && finalHeight.isEmpty()) {
                    if (weightET.getText().toString().isEmpty()){
                        weightET.setError("Please add your weight first");
                        weightET.requestFocus();
                        return false;
                    }
                    if (heightET.getText().toString().isEmpty()){
                        heightET.setError("Please add your height first");
                        heightET.requestFocus();
                        return false;
                    }
                    finalWeight = weightET.getText().toString();
                    finalHeight = heightET.getText().toString();
                }
                hideSoftKeyboard(heightET);
                break;

            case 2:
                if (finalAge.isEmpty()){
                    if (userAge == 0){
                        builder.setTitle("Warning Notice")
                                .setMessage("Sorry but birthday is a required field, please add your birthday to continue.")
                                .setPositiveButton("Okay", (dialogInterface, i) -> dialogInterface.dismiss()).create();
                        builder.show();
                        return false;
                    }
                    if (userAge < 17){
                        builder.setTitle("Warning Notice")
                                .setMessage("Sorry but you must be 17 or above to use this application.")
                                .setPositiveButton("Okay", (dialogInterface, i) -> dialogInterface.dismiss()).create();
                        builder.show();
                        return false;
                    }
                    finalAge = String.valueOf(userAge);
                }
                break;

            case 3:
                if (finalGender.isEmpty()){
                    builder.setTitle("Warning Notice")
                            .setMessage("Sorry but gender is a required field, please select your gender to continue!")
                            .setPositiveButton("Okay", (dialogInterface, i) -> dialogInterface.dismiss()).create();
                    builder.show();
                    return false;
                }
                navButton.setText(getString(R.string.validate));
                break;


            case 5:
                if (finalEmail.isEmpty() && finalPassword.isEmpty()){
                    if (usernameET.getText().toString().isEmpty()){
                        usernameET.setError("Username cannot be empty!");
                        usernameET.requestFocus();
                        return false;
                    }
                    if (usernameET.getText().toString().length() < 6){
                        usernameET.requestFocus();
                        usernameET.setError("Please enter at least 6 characters");
                        return false;
                    }
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

                    if (passwordET.getText().toString().isEmpty()){
                        passwordET.setError("Please add your password first");
                        passwordET.requestFocus();
                        return false;
                    }
                    if (passwordET.getText().toString().length() < 8){
                        passwordET.setError("Please add at least 8 character password");
                        passwordET.requestFocus();
                        return false;
                    }
                    hideSoftKeyboard(passwordET);
                    if (!isValid) {
//                        customProgressBar.startAnimation(animation);
//                        presenter.hasUser("username", usernameET.getText().toString());
                        return false;
                    }
                }
                break;

        }

        return true;

    }

    private void progress() {
        progressView(levels.get(currentLevel), R.color.white);
        progressLayout(prompts.get(currentLevel), 0);
        currentLevel += 1;
        String levelText = ((currentLevel + 1) * 12.5) + "%";
        progress.setText(levelText);
        progressView(levels.get(currentLevel), R.color.lightPrimary);
        progressLayout(prompts.get(currentLevel), 1);
    }
    private void progressView(View view, int color) {
        ViewCompat.setBackgroundTintList(view, getColorStateList(color));
    }

    private void progressLayout(LinearLayout layout, int weight) {
        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, weight));
    }

    public void hideSoftKeyboard(View view){
        InputMethodManager imm =(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}