package com.example.lingapp.ui.CustomViews;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import com.example.lingapp.R;
import java.util.Calendar;
import java.util.Objects;

public class MonthYearPickerDialog extends DialogFragment {

    private static final int MAX_YEAR = 2099;
    private DatePickerDialog.OnDateSetListener listener;
    private String mode;
    public void setListener(DatePickerDialog.OnDateSetListener listener, String mode) {
        this.listener = listener;
        this.mode = mode;
    }

    @NonNull
    @SuppressLint("ResourceAsColor")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogStyle);
        LayoutInflater inflater = getActivity().getLayoutInflater();

        Calendar cal = Calendar.getInstance();

        View dialog = inflater.inflate(R.layout.month_year_picker, null);
        final NumberPicker monthPicker = (NumberPicker) dialog.findViewById(R.id.picker_month);
        final NumberPicker yearPicker = (NumberPicker) dialog.findViewById(R.id.picker_year);
        final TextView title = dialog.findViewById(R.id.title);

        if (mode.equals("Month")) {
            title.setText(mode);
            yearPicker.setVisibility(View.GONE);
            monthPicker.setVisibility(View.VISIBLE);
        }

        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setValue(cal.get(Calendar.MONTH) + 1);

        int year = cal.get(Calendar.YEAR);
        yearPicker.setMinValue(1900);
        yearPicker.setMaxValue(MAX_YEAR);
        yearPicker.setValue(year);

        builder.setView(dialog).setPositiveButton("Okay", (dialog1, which) -> listener.onDateSet(null, yearPicker.getValue(), monthPicker.getValue(), 0)).setNegativeButton("Cancel", (dialog12, id) -> Objects.requireNonNull(MonthYearPickerDialog.this.getDialog()).cancel());
        return builder.create();
    }
}