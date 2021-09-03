package com.app.lostandfound.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.app.lostandfound.R;
import com.app.lostandfound.adapter.CategoryGridAdapter;
import com.app.lostandfound.base.BaseActivity;
import com.app.lostandfound.costomview.CustomTextView;
import com.app.lostandfound.costomview.GridSpacingItemDecoration;
import com.app.lostandfound.lostfoundinterface.ApiCallback;
import com.app.lostandfound.network.ApiClient;
import com.app.lostandfound.network.ApiInterface;
import com.app.lostandfound.pojo.CategoryData;
import com.app.lostandfound.pojo.CategoryResponse;

import java.util.ArrayList;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends BaseActivity implements View.OnClickListener {
RecyclerView recyclerView;
    ArrayList<CategoryData> list =new ArrayList<>();
    String postType="";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        postType=getIntent().getStringExtra("POST_TYPE");
        initView();

    }
    private void initView() {

        ((ImageView)findViewById(R.id.imgBack)).setOnClickListener(this);
        if(postType.equals("1")) {
            ((CustomTextView) findViewById(R.id.header_toolbar)).setText(context.getResources().getString(R.string.what_lost));
        }
        else   if(postType.equals("2"))
        {
            ((CustomTextView) findViewById(R.id.header_toolbar)).setText(context.getResources().getString(R.string.what_found));
        }
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        //GridLayoutManager gridLayoutManager = new GridLayoutManager()
        int spanCount = 2; // 3 columns
        int spacing = 40; // 50px
        boolean includeEdge = false;
        recyclerView.setLayoutManager(new GridLayoutManager(this,spanCount));

        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

//        recyclerView.addItemDecoration(new SpacesItemDecoration(10));
//        recyclerView.setLayoutManager(gridLayoutManager);
        getCategory();



    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.imgBack:
                finish();
                break;
        }
    }








    public void getCategory()
    {
        if (isConnectingToInternet(context)) {
            showLoading();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<CategoryResponse> call = apiInterface.getCategory();
            call.enqueue(new Callback<CategoryResponse>() {
                @Override
                public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response)
                {
                    dismiss_loading();
                    CategoryResponse categoryResponse=response.body();
                    if(categoryResponse.getStatus()==1)
                    {
                         list =categoryResponse.getCategoryDataList();
                        int[] colors = context.getResources().getIntArray(R.array.rainbow);
                        CategoryGridAdapter categoryGridAdapter =new CategoryGridAdapter(context, colors, list,new ApiCallback() {
                            @Override
                            public void result(int position)
                            {
                                CategoryData categoryData=list.get(position);
                                Intent intent=new Intent(context, SubCategoryActivity.class);
                                intent.putExtra("ID",categoryData.getCategory_id());
                                intent.putExtra("NAME",categoryData.getCategory_title());
                                intent.putExtra("POST_TYPE",postType);
                                startActivity(intent);


                            }
                        });
                        recyclerView.setAdapter(categoryGridAdapter);
                    }

                }

                @Override
                public void onFailure(Call<CategoryResponse> call, Throwable t) {

                }
            });
        }
        else
        {
            showInternetConnectionToast();
        }

    }




}
