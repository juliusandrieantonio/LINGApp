package com.example.lingapp.ui.HomePage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lingapp.R;
import com.example.lingapp.ui.AboutUs.AboutUsActivity;
import com.example.lingapp.ui.ContactUs.ContactUsActivity;
import com.example.lingapp.ui.HomePage.Fragments.DashBoard.DashBoardFragment;
import com.example.lingapp.ui.HomePage.Fragments.History.HistoryFragment;
import com.example.lingapp.ui.HomePage.Fragments.EducationalResources.EducationalResourcesFragment;
import com.example.lingapp.ui.HomePage.Fragments.Home.HomeFragment;
import com.example.lingapp.ui.HomePage.Fragments.MealPlan.MealPlanFragment;
import com.example.lingapp.ui.LoginPage.LoginPageActivity;
import com.example.lingapp.ui.UpdateUser.UpdateUserActivity;
import com.example.lingapp.utils.HomePageActivityModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class HomePageActivity extends AppCompatActivity implements IHomePageActivity {

    private static final int MENU = R.id.menu;
    private static final int HOME = R.id.home;
    private static final int DASHBOARD = R.id.dashboard;
    private static final int RESTAURANT = R.id.restaurant;
    private static final int EDIT = R.id.edit;
    private static final int EDUCATIONAL_RESOURCES = R.id.educationalResources;
    private static final int ABOUT_US = R.id.aboutUs;
    private HomeFragment homeFragment;
    private MealPlanFragment mealPlanFragment;
    private HistoryFragment editFragment;
    private DashBoardFragment dashBoardFragment;
    private EducationalResourcesFragment educationalResourcesFragment;
    private ImageView menu_two, manageUser;
    private HomePageActivityModel model;
    private TextView name, email;
    private ImageView profilePic;
    private String displayName;
    private Uri imageUri;
    private String classification = "";
    private BottomNavigationView bottomNavigationView;
    private boolean isOnPause = false;
    private final int CONTACT_US = R.id.contactUs;
    private Toolbar toolbar;
    private TextView toolbar_title;
    private Animation animation;
    private ImageView customProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        bottomNavigationView = findViewById(R.id.bottomNavBar);
        model = new HomePageActivityModel(this);
        toolbar_title = findViewById(R.id.toolbar_title);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        customProgressBar = findViewById(R.id.customProgressBar);

        DrawerLayout mDrawer =  findViewById(R.id.drawerLayout);
        NavigationView navView = findViewById(R.id.navView);
        toolbar = findViewById(R.id.toolbar);
        TextView logout = findViewById(R.id.logout);

        View header = navView.getHeaderView(0);
        name = header.findViewById(R.id.nameTV);
        email = header.findViewById(R.id.emailTV);
        profilePic = header.findViewById(R.id.profilePic);
        customProgressBar.startAnimation(animation);
        model.getInformation();
        model.getData();

        navView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == CONTACT_US) {
                startActivity(new Intent(getApplicationContext(), ContactUsActivity.class));
            }
            if (item.getItemId() == ABOUT_US) {
                startActivity(new Intent(getApplicationContext(), AboutUsActivity.class));
            }
            return true;
        });

        logout.setOnClickListener(view -> {
            model.logout();
            startActivity(new Intent(getApplicationContext(), LoginPageActivity.class));
            finish();
        });

        menu_two = findViewById(R.id.menu_two);
        menu_two.setOnClickListener(v -> mDrawer.openDrawer(GravityCompat.START));
        manageUser = findViewById(R.id.manageAcc);

        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == MENU){
                mDrawer.openDrawer(GravityCompat.START);
            }
            if (item.getItemId() == R.id.search) {
                Toast.makeText(this, "hey", Toast.LENGTH_SHORT).show();
            }
            return true;
        });
        
        manageUser.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), UpdateUserActivity.class));
            finish();
        });

    }

    public void switchFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }


    @Override
    public void onGetUserData(HomePageActivityModel model) {
        if (model != null) {
            name.setText(model.getName());
            imageUri = model.getImageUri();
            displayName = model.getName();
            Glide.with(HomePageActivity.this)
                    .load(model.getImageUri())
                    .centerCrop()
                    .circleCrop()
                    .into(profilePic);

            email.setText(model.getEmail());
        }
    }

    @Override
    public void onGetData(String classification, String result) {
        customProgressBar.clearAnimation();
        this.classification = classification;
        homeFragment = new HomeFragment();
        editFragment = new HistoryFragment();
        mealPlanFragment = new MealPlanFragment(classification);
        dashBoardFragment = new DashBoardFragment(imageUri, displayName, classification, result);
        educationalResourcesFragment = new EducationalResourcesFragment(toolbar, toolbar_title);

        // switch fragment to default home
        if (getApplicationContext() != null && !isOnPause) {
            switchFragment(homeFragment);
            bottomNavigationView.setOnItemSelectedListener(item -> {
                menu_two.setVisibility(View.GONE);
                toolbar.getMenu().clear();
                toolbar.inflateMenu(R.menu.hamburger_menu);
                toolbar.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                manageUser.setVisibility(View.GONE);
                if (item.getItemId() == HOME) {
                    switchFragment(homeFragment);
                    toolbar_title.setText(getString(R.string.home));
                }
                if (item.getItemId() == DASHBOARD) {
                    switchFragment(dashBoardFragment);
                    manageUser.setVisibility(View.VISIBLE);
                    toolbar_title.setText(getString(R.string.dashboard));
                }
                if (item.getItemId() == EDIT) {
                    switchFragment(editFragment);
                    toolbar_title.setText(getString(R.string.edit));
                }
                if (item.getItemId() == RESTAURANT) {
                    switchFragment(mealPlanFragment);
                    toolbar_title.setText(getString(R.string.restaurant));
                }
                if (item.getItemId() == EDUCATIONAL_RESOURCES) {
                    toolbar.getMenu().clear();
                    toolbar.inflateMenu(R.menu.search);
                    toolbar.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                    switchFragment(educationalResourcesFragment);
                    menu_two.setVisibility(View.VISIBLE);
                    toolbar_title.setText(getString(R.string.educational_resources));

                }
                return true;
            });
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        isOnPause = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
         isOnPause = false;
    }
}