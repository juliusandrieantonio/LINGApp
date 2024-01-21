package com.example.lingapp.StaticValues;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.appcompat.content.res.AppCompatResources;

import com.example.lingapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StaticClass {
    private final Map<String, ArrayList<Drawable>> images;

    public StaticClass (Context context) {
        images = new HashMap<>();
        ArrayList<Drawable> maleImages = new ArrayList<>();
        maleImages.add(AppCompatResources.getDrawable(context, R.drawable.male_under_weight));
        maleImages.add(AppCompatResources.getDrawable(context, R.drawable.male_normal));
        maleImages.add(AppCompatResources.getDrawable(context, R.drawable.male_over_weight));
        maleImages.add(AppCompatResources.getDrawable(context, R.drawable.male_obese));
        maleImages.add(AppCompatResources.getDrawable(context, R.drawable.male_extremely_obese));

        ArrayList<Drawable> femaleImages = new ArrayList<>();
        femaleImages.add(AppCompatResources.getDrawable(context, R.drawable.female_under_weight));
        femaleImages.add(AppCompatResources.getDrawable(context, R.drawable.female_normal));
        femaleImages.add(AppCompatResources.getDrawable(context, R.drawable.female_overweight));
        femaleImages.add(AppCompatResources.getDrawable(context, R.drawable.female_obese));
        femaleImages.add(AppCompatResources.getDrawable(context, R.drawable.female_extremely_obese));

        images.put("Male", maleImages);
        images.put("Female", femaleImages);
    }

    public Map<String, ArrayList<Drawable>> getImages() {
        return images;
    }
}
