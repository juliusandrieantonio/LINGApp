package com.example.lingapp.ui.HomePage.Fragments.History;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lingapp.R;
import com.example.lingapp.ui.CustomViews.MonthYearPickerDialog;
import com.example.lingapp.utils.HistoryFragmentModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class HistoryFragment extends Fragment implements IHistoryFragment {
    boolean isFilterUp = false;
    private Context context;
    private Activity activity;
    private BarChart barChart;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayout emptyCard;
    private final ArrayList<String> months = new ArrayList<>(
            Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Nov", "Oct", "Dec")
    );
    private int detailedPickedYear;
    private int detailedPickedMonth;
    private FragmentManager fragmentManager = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        if (getActivity() != null) {
            activity = getActivity();
            fragmentManager = getActivity().getSupportFragmentManager();
        }

        ImageView filter = view.findViewById(R.id.filter);
        recyclerView = view.findViewById(R.id.recyclerView);
        barChart = view.findViewById(R.id.barChart);
        emptyCard = view.findViewById(R.id.emptyCard);
        TextView monthPicker = view.findViewById(R.id.monthFilter);
        TextView yearPicker = view.findViewById(R.id.yearFilter);

        detailedPickedYear = Calendar.getInstance().get(Calendar.YEAR);
        detailedPickedMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        yearPicker.setText(String.valueOf(detailedPickedYear));
        monthPicker.setText(months.get(detailedPickedMonth - 1));

        HistoryFragmentModel model = new HistoryFragmentModel(this);

        monthPicker.setOnClickListener(v -> {
            MonthYearPickerDialog newFragment = new MonthYearPickerDialog();
            newFragment.setListener((view1, year, month, dayOfMonth) -> {
                monthPicker.setText(months.get(month - 1));
                detailedPickedMonth = month;

                // TODO: SETUP THE BAR CHART
                model.getIntakeStatus(String.valueOf(detailedPickedMonth), String.valueOf(detailedPickedYear));
            }, "Month");
            newFragment.show(fragmentManager, "DatePicker");
        });

        yearPicker.setOnClickListener(v -> {
            MonthYearPickerDialog newFragment = new MonthYearPickerDialog();
            newFragment.setListener((view1, year, month, dayOfMonth) -> {
                yearPicker.setText(String.valueOf(year));
                detailedPickedYear = year;

                // TODO: SETUP BARCHART
                model.getIntakeStatus(String.valueOf(detailedPickedMonth), String.valueOf(detailedPickedYear));

            }, "Year");
            newFragment.show(fragmentManager, "DatePicker");
        });

        if (getContext() != null) {
            context = getContext();
        }
        linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setReverseLayout(false);
        linearLayoutManager.setStackFromEnd(false);

        filter.setOnClickListener(v -> {
            if (isFilterUp) {
                filter.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.arrow_downward));
                isFilterUp = false;
                linearLayoutManager.setReverseLayout(false);
                linearLayoutManager.setStackFromEnd(false);
                recyclerView.setLayoutManager(linearLayoutManager);
                return;
            }
            filter.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.arrow_upward));
            isFilterUp = true;
            linearLayoutManager.setReverseLayout(true);
            linearLayoutManager.setStackFromEnd(true);
        });
        Date date = new Date(); // your date
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);

        model.getIntakeStatus(String.valueOf(month + 1), String.valueOf(year));
        model.getBMIHistory();
        return view;
    }

    private void generateBarData(ArrayList<BarEntry> carbs, ArrayList<BarEntry> calories, ArrayList<BarEntry> fats, ArrayList<BarEntry> protein, ArrayList<String> labels) {
        BarDataSet set1 = new BarDataSet(carbs, "carbs");
        set1.setValueTextColor(context.getColor(R.color.black));
        set1.setValueTextSize(10f);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(context.getColor(R.color.carbs));

        BarDataSet set2 = new BarDataSet(calories, "calories");
        set2.setValueTextSize(10f);
        set2.setAxisDependency(YAxis.AxisDependency.LEFT);
        set2.setColor(context.getColor(R.color.calories));
        set2.setValueTextColor(context.getColor(R.color.black));

        BarDataSet set3 = new BarDataSet(fats, "fats");
        set3.setValueTextSize(10f);
        set3.setAxisDependency(YAxis.AxisDependency.LEFT);
        set3.setColor(context.getColor(R.color.fats));
        set3.setValueTextColor(context.getColor(R.color.black));

        BarDataSet set4 = new BarDataSet(protein, "protein");
        set4.setValueTextSize(10f);
        set4.setAxisDependency(YAxis.AxisDependency.LEFT);
        set4.setColor(context.getColor(R.color.lightPrimary));
        set4.setValueTextColor(context.getColor(R.color.black));

        float groupSpace = 0.08f;
        float barSpace = 0.03f; // x4 DataSet
        float barWidth = 0.2f;
        int groupCount = labels.size();


        BarData data = new BarData(set1, set2, set3, set4);
        data.setValueFormatter(new LargeValueFormatter());

        barChart.setData(data);
        barChart.getDescription().setEnabled(false);
        barChart.setPinchZoom(false);
        barChart.setDrawBarShadow(false);
        barChart.setDrawGridBackground(false);
        barChart.setExtraOffsets(0f, 0f, 0f, 5f);
        Legend l = barChart.getLegend();
        Legend legend = barChart.getLegend();
        legend.setYOffset(5f);
        l.setTextSize(10f);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setEnabled(true);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(true);
        leftAxis.setSpaceTop(5f);
        leftAxis.setAxisMinimum(0f);


        barChart.getAxisRight().setEnabled(false);

        barChart.getBarData().setBarWidth(barWidth);
        barChart.getXAxis().setAxisMinimum(0);
        barChart.getXAxis().setAxisMaximum(0 + barChart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        barChart.groupBars(0, groupSpace, barSpace);
        barChart.invalidate();


    }

    @Override
    public void onGetBMIHistory(ArrayList<HistoryFragmentModel> models) {
        recyclerView.removeAllViews();
        HistoryFragmentAdapter adapter = new HistoryFragmentAdapter(models, context);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        emptyCard.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

    }

    @Override
    public void onGetIntakeStatus(ArrayList<HistoryFragmentModel> models, ArrayList<String> labels) {
        barChart.clear();
        barChart.invalidate();
        ArrayList<BarEntry> carbs = new ArrayList<>();
        ArrayList<BarEntry> fats = new ArrayList<>();
        ArrayList<BarEntry> calories = new ArrayList<>();
        ArrayList<BarEntry> protein = new ArrayList<>();
        Log.i("tester", "onDataChange: " + models.toString());
        for (int i = 0; i < models.size(); i++) {
            carbs.add(new BarEntry(i, models.get(i).getCarbs()));
            fats.add(new BarEntry(i, models.get(i).getFats()));
            calories.add(new BarEntry(i, models.get(i).getCalories()));
            protein.add(new BarEntry(i, models.get(i).getProtein()));
        }
        if (carbs.size() != 0) {
            generateBarData(carbs, calories, fats, protein, labels);
        }
    }
}