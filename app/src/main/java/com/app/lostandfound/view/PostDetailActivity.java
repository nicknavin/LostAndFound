package com.app.lostandfound.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.lostandfound.R;
import com.app.lostandfound.adapter.MyCustomPagerAdapter;
import com.app.lostandfound.base.BaseActivity;
import com.app.lostandfound.costomview.CustomTextView;
import com.app.lostandfound.lostfoundinterface.ApiCallback;
import com.app.lostandfound.network.ApiClient;
import com.app.lostandfound.network.ApiInterface;
import com.app.lostandfound.pojo.Images;
import com.app.lostandfound.pojo.LostAllPost;
import com.app.lostandfound.utility.Utility;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.tabs.TabLayout;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class PostDetailActivity extends BaseActivity implements View.OnClickListener
{
    ViewPager viewPager;

    MyCustomPagerAdapter myCustomPagerAdapter;
    LostAllPost lostAllPost;
    ArrayList<Images>urlList=new ArrayList<>();
    CustomTextView txtLostFoundName,txtLostFoundAddress,txtLostFoundDate,txtDescription;
    CustomTextView txtFirstName,txtLastName,txtAge,txtGender,txtBodyColor,txtHeight,txtBreed,txtBrand,txtModel,txtReward,txtTitle;

    LinearLayout layFName,layLName,layAge,layGender,layHeight,layBreed,layBrand,layModel,layReward,layBodyColor;
    CheckBox imgLike;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        if(getIntent().getParcelableExtra("DATA")!=null)
        {
            lostAllPost=getIntent().getParcelableExtra("DATA");
            urlList=lostAllPost.getImagesArrayList();
        }
        initView();
    }

    private void initView()
    {
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
        ((ImageView)findViewById(R.id.imgBack)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.share)).setOnClickListener(this);
        imgLike=(CheckBox)findViewById(R.id.imgLike);
        imgLike.setOnClickListener(this);
        txtTitle=(CustomTextView)findViewById(R.id.txtTitle);
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
            txtTitle.setText(lostAllPost.getTitle());
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
                txtReward.setText(lostAllPost.getReward()+context.getResources().getString(R.string.rupee));
            }
            else
            {
                layReward.setVisibility(View.GONE);
            }


                layBreed.setVisibility(View.GONE);













        }







    }

private void postShare()
{
    String url = ApiClient.BASE_URL_IMAGE+lostAllPost.getImagesArrayList().get(0).getPost_image();


    Glide.with(context)
            .asBitmap()
            .load(url)
            .into(new CustomTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition)
                {
                    Utility.sharePost(resource,lostAllPost,context);
//                                    sendData(resource,lostAllPost);
                }

                @Override
                public void onLoadCleared(@Nullable Drawable placeholder) {
                }
            });
}



    @Override
    public void onClick(View view) {
     switch (view.getId())
     {
         case R.id.share:
             postShare();
             break;
         case R.id.imgBack:
             finish();
             break;
         case R.id.imgLike:
             if (!userId.isEmpty()) {


                 if(imgLike.isChecked())
                 {
                     callLikePost(lostAllPost);
                 }
                 else
                 {
                     callUnLikePost(lostAllPost);
                 }
             }
             else
             {
                 Utility.showToast(context,"Please login");
             }
             break;
     }
    }


    public void callLikePost(final LostAllPost lostAllPost)
    {
        if (isInternetConnected()) {
            showLoading();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<ResponseBody> call = apiInterface.callLikePost(userId,lostAllPost.getPost_id());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
                {
                    dismiss_loading();
                    if(response.body()!=null)
                    {
                        ResponseBody responseBody= response.body();
                        try {
                            String data =responseBody.string();
                            JSONObject jsonObject=new JSONObject(data);
                            showToast(jsonObject.getString("message"));
                            if(jsonObject.getString("status").equals("1")){
//                                lostAllPost.setLike(true);


                            }
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }


                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    dismiss_loading();
                }
            });
        }
        else
        {
            showInternetConnectionToast();
        }

    }


    public void callUnLikePost(final LostAllPost lostAllPost)
    {
        if (isInternetConnected()) {
            showLoading();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<ResponseBody> call = apiInterface.callUnlikePost(userId,lostAllPost.getPost_id());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
                {dismiss_loading();
                    if(response.body()!=null) {
                        ResponseBody responseBody = response.body();
                        try {
                            String data = responseBody.string();
                            JSONObject jsonObject = new JSONObject(data);
                            showToast(jsonObject.getString("message"));
                            if (jsonObject.getString("status").equals("1"))
                            {
//                                lostAllPost.setLike(false);
//


                            }
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    dismiss_loading();
                }
            });
        }
        else
        {
            showInternetConnectionToast();
        }

    }


}
