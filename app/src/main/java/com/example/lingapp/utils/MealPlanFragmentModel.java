package com.example.lingapp.utils;

public class MealPlanFragmentModel {
    private String dayNumber;
    private String imageUrl;

    public MealPlanFragmentModel() {}
    public MealPlanFragmentModel(String dayNumber, String imageUrl) {
        this.dayNumber = dayNumber;
        this.imageUrl = imageUrl;
    }

    public String getDayNumber() {
        return dayNumber;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
