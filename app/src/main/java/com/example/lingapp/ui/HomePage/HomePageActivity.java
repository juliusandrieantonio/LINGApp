package com.example.lingapp.ui.HomePage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lingapp.R;
import com.example.lingapp.ui.HomePage.Fragments.DashBoard.DashBoardFragment;
import com.example.lingapp.ui.HomePage.Fragments.History.HistoryFragment;
import com.example.lingapp.ui.HomePage.Fragments.EducationalResources.EducationalResourcesFragment;
import com.example.lingapp.ui.HomePage.Fragments.Home.HomeFragment;
import com.example.lingapp.ui.HomePage.Fragments.MealPlan.MealPlanFragment;
import com.example.lingapp.ui.LoginPage.LoginPageActivity;
import com.example.lingapp.utils.HomePageActivityModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomePageActivity extends AppCompatActivity implements IHomePageActivity {

    private static final int MENU = R.id.menu;
    private static final int HOME = R.id.home;
    private static final int DASHBOARD = R.id.dashboard;
    private static final int RESTAURANT = R.id.restaurant;
    private static final int EDIT = R.id.edit;
    private static final int EDUCATIONAL_RESOURCES = R.id.educationalResources;
    private HomeFragment homeFragment;
    private MealPlanFragment mealPlanFragment;
    private HistoryFragment editFragment;
    private DashBoardFragment dashBoardFragment;
    private EducationalResourcesFragment educationalResourcesFragment;
    private ImageView search, manageUser;
    private HomePageActivityModel model;
    private TextView name, email;
    private ImageView profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavBar);
        model = new HomePageActivityModel(this);

        DrawerLayout mDrawer =  findViewById(R.id.drawerLayout);
        NavigationView navView = findViewById(R.id.navView);
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView logout = findViewById(R.id.logout);

        View header = navView.getHeaderView(0);
        name = header.findViewById(R.id.nameTV);
        email = header.findViewById(R.id.emailTV);
        profilePic = header.findViewById(R.id.profilePic);

        logout.setOnClickListener(view -> {
            model.logout();
            startActivity(new Intent(getApplicationContext(), LoginPageActivity.class));
            finish();
        });
        homeFragment = new HomeFragment();
        editFragment = new HistoryFragment();
        mealPlanFragment = new MealPlanFragment();
        dashBoardFragment = new DashBoardFragment();
        educationalResourcesFragment = new EducationalResourcesFragment();

        search = findViewById(R.id.personSearch);
        manageUser = findViewById(R.id.manageAcc);

        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == MENU){
                mDrawer.openDrawer(GravityCompat.START);
            }
            return true;
        });

        // switch fragment to default home
        switchFragment(homeFragment);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            search.setVisibility(View.GONE);
            manageUser.setVisibility(View.GONE);
            if (item.getItemId() == HOME) {
                switchFragment(homeFragment);
            }
            if (item.getItemId() == DASHBOARD) {
                switchFragment(dashBoardFragment);
                manageUser.setVisibility(View.VISIBLE);
            }
            if (item.getItemId() == EDIT) {
                switchFragment(editFragment);
            }
            if (item.getItemId() == RESTAURANT) {
                switchFragment(mealPlanFragment);
            }
            if (item.getItemId() == EDUCATIONAL_RESOURCES) {
                switchFragment(educationalResourcesFragment);
                search.setVisibility(View.VISIBLE);
            }
            return true;
        });

        model.getInformation();
    }

    public void switchFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }


    @Override
    public void onGetUserData(HomePageActivityModel model) {
        if (model != null) {
            name.setText(model.getName());

            Glide.with(HomePageActivity.this)
                    .load(model.getImageUri())
                    .centerCrop()
                    .circleCrop()
                    .into(profilePic);

            email.setText(model.getEmail());
        }
    }
}