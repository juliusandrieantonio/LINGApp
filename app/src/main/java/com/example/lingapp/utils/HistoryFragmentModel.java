package com.example.lingapp.utils;

import com.example.lingapp.ui.HomePage.Fragments.History.HistoryFragment;

public class HistoryFragmentModel {
    private String status, gender;
    private Double height, weight;

    public HistoryFragmentModel() {

    }
    public HistoryFragmentModel(String status, Double height, Double weight, String gender) {
        this.status = status;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public Double getHeight() {
        return height;
    }

    public String getGender() {
        return gender;
    }

    public Double getWeight() {
        return weight;
    }
}
