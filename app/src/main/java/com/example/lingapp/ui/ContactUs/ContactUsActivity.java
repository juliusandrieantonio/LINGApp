package com.example.lingapp.ui.ContactUs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lingapp.R;
import com.example.lingapp.ui.CustomViews.CustomAlertDialogActivity;
import com.example.lingapp.ui.UpdateUser.UpdateProfileActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ContactUsActivity extends AppCompatActivity {
    private EditText messageET, subjectET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        messageET = findViewById(R.id.messageET);
        subjectET = findViewById(R.id.subjectET);
        Button send = findViewById(R.id.send);
        send.setOnClickListener(view -> {
            if (isValid()) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String email = Objects.requireNonNull(auth.getCurrentUser()).getEmail();
                try {
                    String subject = subjectET.getText().toString() + " from: " + email;
                    GMailSender sender = new GMailSender("lingapp.pbj@gmail.com", subject, messageET.getText().toString());
                    sender.execute();
                    CustomAlertDialogActivity customAlertDialogActivity = new CustomAlertDialogActivity(ContactUsActivity.this, "Verification Notice", "An email has been sent to the new email provided. Please click the link to confirm changes, Thank you!");
                    Objects.requireNonNull(customAlertDialogActivity.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    customAlertDialogActivity.show();
                    send.setBackgroundColor(getColor(R.color.dark));
                    send.setEnabled(false);
                } catch (Exception e) {
                    Log.i("SendMail", e.getLocalizedMessage());
                }
            }
        });
    }
    public boolean isValid() {
        if (subjectET.getText().toString().isEmpty()) {
            subjectET.setError("Sorry but you need to add a message first to continue.");
            subjectET.requestFocus();
            return false;
        }
        if (subjectET.getText().toString().length() < 6) {
            subjectET.setError("Sorry but you need to add at least 6 characters to continue.");
            subjectET.requestFocus();
            return false;
        }
        if (messageET.getText().toString().isEmpty()) {
            messageET.setError("Sorry but you need to add a message first to continue.");
            return false;
        }

        return true;
    }
}