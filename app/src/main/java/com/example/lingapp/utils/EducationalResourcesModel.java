package com.example.lingapp.utils;

import android.graphics.drawable.Drawable;

public class EducationalResourcesModel {
    private String name;
    private String description;
    private int drawable;

    public EducationalResourcesModel() {

    }

    public EducationalResourcesModel(String name, int drawable, String description) {
        this.name = name;
        this.drawable = drawable;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public int getDrawable() {
        return drawable;
    }

    public String getDescription() {
        return description;
    }
}