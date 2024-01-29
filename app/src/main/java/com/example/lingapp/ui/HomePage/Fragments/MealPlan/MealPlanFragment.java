package com.example.lingapp.ui.HomePage.Fragments.MealPlan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lingapp.R;
import com.example.lingapp.ui.DetailedMealPlan.DetailedMealPlanActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MealPlanFragment extends Fragment{
    private Context context;
    private Activity activity;
    private String classification = "";
    public MealPlanFragment(String classification) {
        this.classification = classification;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meal_plan, container, false);

        Date date = new Date();   // given date
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(date);   // assigns calendar to given date
        TextView greetings = view.findViewById(R.id.greetings);
        ImageView image = view.findViewById(R.id.image);

        if (getContext() != null) {
            context = getContext();
        }
        if (getActivity() != null) {
            activity = getActivity();
        }

        int time = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
        if (time >= 12) {
            greetings.setText(context.getString(R.string.good_afternoon));
        }
        if (time >= 17) {
            greetings.setText(context.getString(R.string.good_evening));
            image.setImageDrawable(ActivityCompat.getDrawable(context, R.drawable.moon_svgrepo_com));
        }

        CardView mealPlan1 = view.findViewById(R.id.mealPlan1);
        CardView mealPlan2 = view.findViewById(R.id.mealPlan2);
        CardView mealPlan3 = view.findViewById(R.id.mealPlan3);
        CardView mealPlan4 = view.findViewById(R.id.mealPlan4);
        CardView mealPlan5 = view.findViewById(R.id.mealPlan5);
        CardView mealPlan6 = view.findViewById(R.id.mealPlan6);

        Intent intent = new Intent(context, DetailedMealPlanActivity.class);
        mealPlan1.setOnClickListener(v -> {
            intent.putExtra("day", 1);
            intent.putExtra("weight", classification);
            activity.startActivity(intent);
        });

        mealPlan2.setOnClickListener(v -> {
            intent.putExtra("day", 2);
            intent.putExtra("weight", classification);
            activity.startActivity(intent);
        });

        mealPlan3.setOnClickListener(v -> {
            intent.putExtra("day", 3);
            intent.putExtra("weight", classification);
            activity.startActivity(intent);
        });

        mealPlan4.setOnClickListener(v -> {
            intent.putExtra("day", 4);
            intent.putExtra("weight", classification);
            activity.startActivity(intent);
        });

        mealPlan5.setOnClickListener(v -> {
            intent.putExtra("day", 5);
            intent.putExtra("weight", classification);
            activity.startActivity(intent);
        });

        mealPlan6.setOnClickListener(v -> {
            intent.putExtra("day", 6);
            intent.putExtra("weight", classification);
            activity.startActivity(intent);
        });

        return view;
    }
}