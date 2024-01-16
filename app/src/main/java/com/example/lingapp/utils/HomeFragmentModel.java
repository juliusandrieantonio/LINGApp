package com.example.lingapp.utils;
public class HomeFragmentModel {
    private String imageUrl;
    private String Description;

    public HomeFragmentModel() {

    }
    public HomeFragmentModel(String imageUrl, String description) {
        this.imageUrl = imageUrl;
        Description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return Description;
    }
}
