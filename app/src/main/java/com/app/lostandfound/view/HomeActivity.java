package com.app.lostandfound.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.app.lostandfound.R;
import com.app.lostandfound.base.BaseActivity;
import com.app.lostandfound.view.ui.ExploreFragment;
import com.app.lostandfound.utility.Constant;
import com.app.lostandfound.view.ui.ChatFragment;
import com.app.lostandfound.view.ui.LostFoundItemFragment;
import com.app.lostandfound.view.ui.MyAdsFragment;
import com.app.lostandfound.view.ui.ProfileFragment;
import com.app.lostandfound.view.ui.UploadFragment;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

public class HomeActivity extends BaseActivity {
    BottomNavigationView bottomNavigationView;

    //native advance
    //unit id
//ca-app-pub-9444676719023189/2515573502

    //app id
    //ca-app-pub-9444676719023189~7604248654

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lose_find);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setUpNavigation();
    }

    public void setUpNavigation(){
        bottomNavigationView =findViewById(R.id.bottomNavigationView);
        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView,
                navHostFragment.getNavController());
    }


    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {


            case Constant.SUB_CATEGORY_CODE:

                break;


            default:
                break;
        }
    }

}
