package com.example.lingapp.ui.HomePage.Fragments.DashBoard;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lingapp.R;
import com.example.lingapp.ui.BMICalculator.BMICalculatorActivity;

public class DashBoardFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dash_board, container, false);
        TextView edit = view.findViewById(R.id.edit);
        edit.setOnClickListener(v -> startActivity(new Intent(getContext(), BMICalculatorActivity.class)));

        return view;
    }
}