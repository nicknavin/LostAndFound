package com.app.lostandfound.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.lostandfound.R;
import com.app.lostandfound.adapter.MyCustomPagerAdapter;
import com.app.lostandfound.base.BaseActivity;
import com.app.lostandfound.costomview.CustomTextView;
import com.app.lostandfound.lostfoundinterface.ApiCallback;
import com.app.lostandfound.pojo.Images;
import com.app.lostandfound.pojo.LostAllPost;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import androidx.viewpager.widget.ViewPager;

public class PostEditDetailActivity extends BaseActivity implements View.OnClickListener
{
    ViewPager viewPager;
    MyCustomPagerAdapter myCustomPagerAdapter;
    LostAllPost lostAllPost;
    ArrayList<Images>urlList=new ArrayList<>();
    CustomTextView txtLostFoundName,txtLostFoundAddress,txtLostFoundDate,txtDescription;
    CustomTextView txtFirstName,txtLastName,txtAge,txtGender,txtBodyColor,txtHeight,txtBreed,txtBrand,txtModel,txtReward;
    LinearLayout layFName,layLName,layAge,layGender,layHeight,layBreed,layBrand,layModel,layReward,layBodyColor;
ImageView imgEditPostimages,imgEditLastLocation,imgEditDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_edit_detail);
        if(getIntent().getParcelableExtra("DATA")!=null)
        {
            lostAllPost=getIntent().getParcelableExtra("DATA");
            urlList=lostAllPost.getImagesArrayList();
        }
        initView();
    }

    private void initView()
    {
        imgEditPostimages=(ImageView)findViewById(R.id.imgEditPostimages);
        imgEditPostimages.setOnClickListener(this);
        imgEditLastLocation=(ImageView)findViewById(R.id.imgEditLastLocation);
        imgEditLastLocation.setOnClickListener(this);
        imgEditDetail=(ImageView)findViewById(R.id.imgEditDetail);
        imgEditDetail.setOnClickListener(this);


        viewPager=(ViewPager)findViewById(R.id.viewpager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager, true);
        myCustomPagerAdapter = new MyCustomPagerAdapter(context, urlList, new ApiCallback() {
            @Override
            public void result(int position) {
                Intent intent=new Intent(context,FullScreenActivity.class);
                intent.putExtra("DATA",lostAllPost);
                intent.putExtra("POSITION",position);
                startActivity(intent);
            }
        });
        viewPager.setAdapter(myCustomPagerAdapter);
        ((ImageView)findViewById(R.id.imgBack)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        txtLostFoundName=(CustomTextView)findViewById(R.id.txtLostFoundName);
        txtDescription=(CustomTextView)findViewById(R.id.txtDescription);
        txtLostFoundAddress=(CustomTextView)findViewById(R.id.txtLostFoundAddress);
        txtLostFoundDate=(CustomTextView)findViewById(R.id.txtLostFoundDate);
        txtFirstName=(CustomTextView)findViewById(R.id.txtFirstName);
        txtLastName=(CustomTextView)findViewById(R.id.txtLastName);
        txtAge=(CustomTextView)findViewById(R.id.txtAge);
        txtGender=(CustomTextView)findViewById(R.id.txtGender);
        txtBodyColor=(CustomTextView)findViewById(R.id.txtBodyColor);
        txtHeight=(CustomTextView)findViewById(R.id.txtHeight);
        txtBreed=(CustomTextView)findViewById(R.id.txtBreed);
        txtBrand=(CustomTextView)findViewById(R.id.txtBrand);
        txtModel=(CustomTextView)findViewById(R.id.txtModel);
        txtReward=(CustomTextView)findViewById(R.id.txtReward);
        layFName=(LinearLayout)findViewById(R.id.layFName);
        layLName=(LinearLayout)findViewById(R.id.layLName);
        layAge=(LinearLayout)findViewById(R.id.layAge);
        layGender=(LinearLayout)findViewById(R.id.layGender);
        layHeight=(LinearLayout)findViewById(R.id.layHeight);
        layBreed=(LinearLayout)findViewById(R.id.layBreed);
        layBrand=(LinearLayout)findViewById(R.id.layBrand);
        layModel=(LinearLayout)findViewById(R.id.layModel);
        layReward=(LinearLayout)findViewById(R.id.layReward);
        layBodyColor=(LinearLayout)findViewById(R.id.layBodyColor);

        if(lostAllPost!=null)
        {
            txtLostFoundName.setText(lostAllPost.getFirst_name()+" "+lostAllPost.getLast_name());
            txtLostFoundDate.setText(lostAllPost.getLast_date());
             txtLostFoundAddress.setText(lostAllPost.getLast_location());
            txtDescription.setText(lostAllPost.getPost_description());
            if(!lostAllPost.getFirst_name().isEmpty())
            {
                txtFirstName.setText(lostAllPost.getFirst_name());
            }
            else
            {
                layFName.setVisibility(View.GONE);
            }

            if(!lostAllPost.getLast_name().isEmpty())
            {
                txtLastName.setText(lostAllPost.getLast_name());
            }
            else
            {
                layLName.setVisibility(View.GONE);
            }
            if(!lostAllPost.getAge().isEmpty())
            {
                txtAge.setText(lostAllPost.getAge());
            }
            else
            {
                layAge.setVisibility(View.GONE);
            }
            if(!lostAllPost.getGender().isEmpty())
            {
                txtGender.setText(lostAllPost.getGender());
            }
            else
            {
                layGender.setVisibility(View.GONE);
            }



            if(!lostAllPost.getColor().isEmpty())
            {
                txtBodyColor.setText(lostAllPost.getColor());
            }
            else
            {
                layBodyColor.setVisibility(View.GONE);
            }
              if(!lostAllPost.getHeight().isEmpty())
            {
                txtHeight.setText(lostAllPost.getHeight());
            }
            else
            {
                layHeight.setVisibility(View.GONE);
            }
            if(!lostAllPost.getBrand().isEmpty())
            {
                txtBrand.setText(lostAllPost.getBrand());
            }
            else
            {
                layBrand.setVisibility(View.GONE);
            }

            if(!lostAllPost.getModel().isEmpty())
            {
                txtModel.setText(lostAllPost.getModel());
            }
            else
            {
                layModel.setVisibility(View.GONE);
            }


            if(!lostAllPost.getReward().isEmpty())
            {
                txtReward.setText(lostAllPost.getReward());
            }
            else
            {
                layReward.setVisibility(View.GONE);
            }


                layBreed.setVisibility(View.GONE);













        }







    }


    @Override
    public void onClick(View view)
    {
        Intent intent;
        switch (view.getId())
        {
            case R.id.imgEditDetail:
                 intent=new Intent(context,PostUpdateActivity.class);
                intent.putExtra("DATA",lostAllPost);
                startActivity(intent);
                break;
            case R.id.imgEditLastLocation:
                 intent=new Intent(context,LastLocationUpdateActivity.class);
                intent.putExtra("DATA",lostAllPost);
                startActivity(intent);
                break;
            case R.id.imgEditPostimages:
//                intent=new Intent(context,LastLocationActivity.class);
//                intent.putExtra("DATA",lostAllPost);
//                startActivity(intent);
                break;
        }
    }
}
