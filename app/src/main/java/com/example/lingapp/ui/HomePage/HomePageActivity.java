package com.example.lingapp.ui.HomePage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.lingapp.R;
import com.example.lingapp.ui.HomePage.Fragments.DashBoardFragment;
import com.example.lingapp.ui.HomePage.Fragments.EditFragment;
import com.example.lingapp.ui.HomePage.Fragments.EducationalResourcesFragment;
import com.example.lingapp.ui.HomePage.Fragments.HomeFragment.HomeFragment;
import com.example.lingapp.ui.HomePage.Fragments.MealPlanFragment.MealPlanFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomePageActivity extends AppCompatActivity {

    private static final int MENU = R.id.menu;
    private static final int HOME = R.id.home;
    private static final int DASHBOARD = R.id.dashboard;
    private static final int RESTAURANT = R.id.restaurant;
    private static final int EDIT = R.id.edit;
    private static final int EDUCATIONAL_RESOURCES = R.id.educationalResources;

    private HomeFragment homeFragment;
    private MealPlanFragment mealPlanFragment;
    private EditFragment editFragment;
    private DashBoardFragment dashBoardFragment;
    private EducationalResourcesFragment educationalResourcesFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavBar);
        DrawerLayout mDrawer =  findViewById(R.id.drawerLayout);
        NavigationView navView = findViewById(R.id.navView);
        Toolbar toolbar = findViewById(R.id.toolbar);

        homeFragment = new HomeFragment();
        editFragment = new EditFragment();
        mealPlanFragment = new MealPlanFragment();
        dashBoardFragment = new DashBoardFragment();
        educationalResourcesFragment = new EducationalResourcesFragment();

        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == MENU){
                mDrawer.openDrawer(GravityCompat.START);
            }
            return true;
        });

        // switch fragment to default home
        switchFragment(homeFragment);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == HOME) {
                switchFragment(homeFragment);
            }
            if (item.getItemId() == DASHBOARD) {
                switchFragment(dashBoardFragment);
            }
            if (item.getItemId() == EDIT) {
                switchFragment(editFragment);
            }
            if (item.getItemId() == RESTAURANT) {
                switchFragment(mealPlanFragment);
            }
            if (item.getItemId() == EDUCATIONAL_RESOURCES) {
                switchFragment(educationalResourcesFragment);
            }
            return true;
        });
    }

    public void switchFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }


}