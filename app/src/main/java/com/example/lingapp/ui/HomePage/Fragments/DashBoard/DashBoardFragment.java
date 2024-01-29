package com.example.lingapp.ui.HomePage.Fragments.DashBoard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lingapp.R;
import com.example.lingapp.ui.BMICalculator.BMICalculatorActivity;
import com.example.lingapp.utils.DashBoardFragmentModel;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DashBoardFragment extends Fragment implements IDashBoard{
    private PieChart pieChart;

    private Uri imageUri;
    private String displayName;
    private Context context;
    private TextView carbsTV, proTV, calTV, fatsTV;
    private double carbsPercentage = 0, calPercentage = 0, proPercentage = 0, fatsPercentage = 0;
    private double tCarbs = 0, tPro = 0, tCal = 0, tFats = 0;
    private double totalFrequency = 0;
    private String classificationString, resultString;

    public DashBoardFragment(Uri imageUri, String displayName, String classificationString, String resultString) {
        this.displayName = "Hello, " + displayName + "!";
        this.imageUri = imageUri;
        this.classificationString = classificationString;
        this.resultString = resultString;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dash_board, container, false);
        pieChart = view.findViewById(R.id.pieChart);
        carbsTV = view.findViewById(R.id.carbsTV);
        proTV = view.findViewById(R.id.proTV);
        calTV = view.findViewById(R.id.calTV);
        fatsTV = view.findViewById(R.id.fatsTV);

        TextView classification = view.findViewById(R.id.classification);
        TextView value = view.findViewById(R.id.value);

        value.setText(resultString);

        if (classificationString.equals("normal")) {
            classification.setText("Normal");
        }
        if (classificationString.equals("obese")) {
            classification.setText("Obese");
        }
        if (classificationString.equals("extremelyObese")) {
            classification.setText("Extremely Obese");
        }
        if (classificationString.equals("underWeight")) {
            classification.setText("Underweight");
        }
        if (classificationString.equals("overweight")) {
            classification.setText("Overweight");
        }
        TextView nameTV = view.findViewById(R.id.nameTV);
        TextView edit = view.findViewById(R.id.edit);
        ImageView profilePic = view.findViewById(R.id.profilePic);
        if (getContext() != null){
            context = getContext();
        }
        edit.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), BMICalculatorActivity.class));
        });
        nameTV.setText(displayName);
        Glide.with(context).load(imageUri).centerCrop().circleCrop().into(profilePic);
        DashBoardFragmentModel model = new DashBoardFragmentModel(this);
        LocalDate date = LocalDate.now();
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        try {
            model.getData(String.valueOf(year), String.valueOf(month), String.valueOf(day));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return view;
    }
    private void showPieChart(){
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        String label = "type";

        //initializing data
        Map<String, Double> typeAmountMap = new HashMap<>();
        typeAmountMap.put("Fats", fatsPercentage);
        typeAmountMap.put("Protein", proPercentage);
        typeAmountMap.put("Calories", calPercentage);
        typeAmountMap.put("Carbohydrates", carbsPercentage);

        //initializing colors for the entries
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#F6C557"));
        colors.add(Color.parseColor("#D08726"));
        colors.add(Color.parseColor("#CA6B6E"));
        colors.add(Color.parseColor("#90B05B"));

        //input data and fit data into pie chart entry
        for(String type: typeAmountMap.keySet()){
            pieEntries.add(new PieEntry(Objects.requireNonNull(typeAmountMap.get(type)).floatValue(), type));
        }

        //collecting the entries with label name
        PieDataSet pieDataSet = new PieDataSet(pieEntries,label);
        //setting text size of the value
        pieDataSet.setValueTextSize(12f);
        //providing color list for coloring different entries
        pieDataSet.setColors(colors);
        //grouping the data set from entry to chart
        PieData pieData = new PieData(pieDataSet);
        pieData.setValueTextColor(context.getColor(R.color.white));

        //showing the value of the entries, default true if not set
        pieData.setDrawValues(true);
        pieData.setValueFormatter(new PercentFormatter());

        pieChart.setData(pieData);
        pieChart.invalidate();
        pieChart.setDrawEntryLabels(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
    }

    @Override
    public void onGetData(ArrayList<DashBoardFragmentModel> models) {
        tCarbs = 0;
        tCal = 0;
        tPro = 0;
        tFats = 0;
        if (models != null) {
            for (DashBoardFragmentModel model: models) {
                tCarbs += model.getCarbs();
                tCal += model.getCalories();
                tPro += model.getProtein();
                tFats += model.getFats();
            }
            totalFrequency = tCarbs + tPro + tFats + tCal;
            String carbsText = tCarbs + " g";
            String proText = tPro + " g";
            String calText = tCal + " Kcal";
            String fatsText =  tFats + " g";

            carbsTV.setText(carbsText);
            proTV.setText(proText);
            calTV.setText(calText);
            fatsTV.setText(fatsText);
        }
        calPercentage = (double) tCal / totalFrequency;
        double PERCENTAGE = 100;
        calPercentage *= PERCENTAGE;

        proPercentage = (double) tPro / totalFrequency;
        proPercentage *= PERCENTAGE;

        carbsPercentage = (double) tCarbs / totalFrequency;
        carbsPercentage *= PERCENTAGE;

        fatsPercentage = (double) tFats / totalFrequency;
        fatsPercentage *= PERCENTAGE;
        Log.i("fsafasfsaf", "onGetData: " + (double) tCal / totalFrequency);
        showPieChart();

    }
}