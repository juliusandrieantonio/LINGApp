package com.example.lingapp.ui.HomePage.Fragments.History;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lingapp.R;
import com.example.lingapp.utils.HistoryFragmentModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.Random;

public class HistoryFragment extends Fragment {
    boolean isFilterUp = false;
    private Context context;
    private Activity activity;
    private BarChart barChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        ImageView filter = view.findViewById(R.id.filter);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        barChart = view.findViewById(R.id.barChart);

        if (getContext() != null) {
            context = getContext();
        }
        if (getActivity() != null) {
            activity = getActivity();
        }

        filter.setOnClickListener(v -> {
            if (isFilterUp) {
                filter.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.arrow_downward));
                isFilterUp = false;
                return;
            }
            filter.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.arrow_upward));
            isFilterUp = true;
        });

        ArrayList<HistoryFragmentModel> models = new ArrayList<>();
        HistoryFragmentModel model = new HistoryFragmentModel("Normal", 160.0, 57.0, "Male");
        models.add(model);
        models.add(model);
        models.add(model);
        models.add(model);
        models.add(model);
        models.add(model);
        HistoryFragmentAdapter historyFragmentAdapter = new HistoryFragmentAdapter(models, context);
        recyclerView.setAdapter(historyFragmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        ArrayList<BarEntry> carbs = new ArrayList<>();
        ArrayList<BarEntry> fats = new ArrayList<>();
        ArrayList<BarEntry> calories = new ArrayList<>();

        Random random = new Random();

        for (int i = 0; i < 20; i++) {
            carbs.add(new BarEntry(i, random.nextInt(100)));
            fats.add(new BarEntry(i, random.nextInt(100)));
            calories.add(new BarEntry(i, random.nextInt(100)));

        }
        generateBarData(carbs, fats, calories);
        return view;
    }

    private void generateBarData(ArrayList<BarEntry> carbs, ArrayList<BarEntry> calories, ArrayList<BarEntry> fats) {
        barChart.clear();
        barChart.invalidate();

        BarDataSet set1 = new BarDataSet(carbs, "Calories");
        set1.setValueTextColor(activity.getColor(R.color.black));
        set1.setValueTextSize(10f);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(activity.getColor(R.color.calories));

        BarDataSet set2 = new BarDataSet(calories, "Carbs");
        set2.setValueTextSize(10f);
        set2.setAxisDependency(YAxis.AxisDependency.LEFT);
        set2.setColor(activity.getColor(R.color.carbs));
        set2.setValueTextColor(activity.getColor(R.color.black));

        BarDataSet set3 = new BarDataSet(fats, "Fats");
        set2.setValueTextSize(10f);
        set2.setAxisDependency(YAxis.AxisDependency.LEFT);
        set2.setColor(activity.getColor(R.color.fats));
        set2.setValueTextColor(activity.getColor(R.color.black));

        YAxis y = barChart.getAxisLeft();
        y.setAxisMaximum(Math.max(set1.getYMax(), set2.getYMax()) + 10);
        y.setAxisMinimum(0);

        Legend legend = barChart.getLegend();
        legend.setYOffset(5f);
        legend.setTextColor(activity.getColor(R.color.black));
        legend.setTextSize(12f);

        float groupSpace = 0.15f;
        float barSpace = 0.01f; // x2 dataset
        float barWidth = 0.42f;

        BarData data = new BarData(set1, set2, set3);

        data.setValueTextSize(12f);
        data.setDrawValues(false);
        data.setBarWidth(barWidth);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setLabelCount(calories.size());
        xAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        xAxis.setGranularity(1);
        xAxis.setCenterAxisLabels(true);
        xAxis.setAxisMaximum(calories.size());

        barChart.setData(data);
        barChart.groupBars(0f,groupSpace,barSpace);
        barChart.getDescription().setEnabled(false);
        barChart.setTouchEnabled(false);
        barChart.animateY(1000);
        barChart.getAxisLeft().setTextColor(activity.getColor(R.color.black));
        barChart.getXAxis().setTextColor(activity.getColor(R.color.black));
        barChart.getAxisRight().setTextColor(activity.getColor(R.color.black));
        barChart.invalidate();

    }

}