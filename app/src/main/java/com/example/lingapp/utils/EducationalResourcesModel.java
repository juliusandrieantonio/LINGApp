package com.example.lingapp.utils;

public class EducationalResourcesModel {
    private String name, url;

    public EducationalResourcesModel() {

    }

    public EducationalResourcesModel(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
