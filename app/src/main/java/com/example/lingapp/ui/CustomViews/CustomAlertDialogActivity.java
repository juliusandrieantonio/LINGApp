package com.example.lingapp.ui.CustomViews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.lingapp.R;

public class CustomAlertDialogActivity extends Dialog {

    String title, description;
    public CustomAlertDialogActivity(@NonNull Context context, String title, String description) {
        super(context);
        this.title = title;
        this.description = description;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_custom_alert_dialog);
        Button okay = findViewById(R.id.okay);
        TextView titleTV = findViewById(R.id.title);
        TextView warningTV = findViewById(R.id.warning);

        titleTV.setText(title);
        warningTV.setText(description);
        okay.setOnClickListener(view -> dismiss());
    }
}