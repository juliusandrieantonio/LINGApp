package com.example.lingapp.utils;
public class HomeFragmentModel {
    private int drawable;
    private String Description;

    public HomeFragmentModel() {

    }
    public HomeFragmentModel(int drawable, String description) {
        this.drawable = drawable;
        Description = description;
    }

    public int getDrawable() {
        return drawable;
    }

    public String getDescription() {
        return Description;
    }
}
