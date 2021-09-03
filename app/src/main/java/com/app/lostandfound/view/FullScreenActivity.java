package com.app.lostandfound.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.app.lostandfound.R;
import com.app.lostandfound.adapter.MyCustomPagerAdapter;
import com.app.lostandfound.base.BaseActivity;
import com.app.lostandfound.lostfoundinterface.ApiCallback;
import com.app.lostandfound.pojo.Images;
import com.app.lostandfound.pojo.LostAllPost;
import com.google.android.material.tabs.TabLayout;


import java.util.ArrayList;

public class FullScreenActivity extends BaseActivity {
    ViewPager viewPager;

    MyCustomPagerAdapter myCustomPagerAdapter;
    LostAllPost lostAllPost;
    ArrayList<Images> urlList=new ArrayList<>();
    int position =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        if(getIntent().getParcelableExtra("DATA")!=null)
        {
            lostAllPost=getIntent().getParcelableExtra("DATA");
            urlList=lostAllPost.getImagesArrayList();
            position=getIntent().getIntExtra("POSITION",0);
        }
        initView();
    }

    private void initView()
    {
        ((ImageView)findViewById(R.id.imgBack)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        viewPager=(ViewPager)findViewById(R.id.viewpager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager, true);
        myCustomPagerAdapter = new MyCustomPagerAdapter(context, urlList, new ApiCallback() {
            @Override
            public void result(int position) {

            }
        });
        viewPager.setAdapter(myCustomPagerAdapter);
        viewPager.setCurrentItem(position);
    }

}
