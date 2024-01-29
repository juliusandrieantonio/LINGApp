package com.example.lingapp.ui.HomePage;

import com.example.lingapp.utils.HomePageActivityModel;

public interface IHomePageActivity {
    void onGetUserData(HomePageActivityModel model);
    void onGetData(String classification, String result);
}
