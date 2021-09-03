package com.app.lostandfound.view;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.app.lostandfound.R;
import com.app.lostandfound.adapter.SubCategoryListAdapter;
import com.app.lostandfound.base.BaseActivity;
import com.app.lostandfound.costomview.CustomTextView;
import com.app.lostandfound.lostfoundinterface.ApiCallback;
import com.app.lostandfound.network.ApiClient;
import com.app.lostandfound.network.ApiInterface;
import com.app.lostandfound.pojo.SubCategoryData;
import com.app.lostandfound.pojo.SubCategoryResponse;

import java.util.ArrayList;

public class SubCategoryActivity extends BaseActivity {

    RecyclerView recyclerView;
    Context context;
    String id="",postType="",name="";
    ArrayList<SubCategoryData> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        context=this;
        if(getIntent().getStringExtra("ID")!=null)
        {
            id=getIntent().getStringExtra("ID");
        }
        if(getIntent().getStringExtra("NAME")!=null)
        {
            name=getIntent().getStringExtra("NAME");
        }
        if(getIntent().getStringExtra("POST_TYPE")!=null)
        {
            postType=getIntent().getStringExtra("POST_TYPE");
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
        ((CustomTextView)findViewById(R.id.header_toolbar)).setText(name);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        getSubcategory(id);
    }





    public void getSubcategory(String id)
    {
        if (isConnectingToInternet(context))
        {
            showLoading();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<SubCategoryResponse> call = apiInterface.getSubCategory(id);
            call.enqueue(new Callback<SubCategoryResponse>()
            {
                @Override
                public void onResponse(Call<SubCategoryResponse> call, Response<SubCategoryResponse> response)
                {
                    dismiss_loading();
                    SubCategoryResponse subCategoryResponse=response.body();
                    if(subCategoryResponse.getStatus()==1)
                    {
                        list =subCategoryResponse.getSubCategoryDataList();
                        recyclerView.setAdapter(new SubCategoryListAdapter(context, list,new ApiCallback() {
                            @Override
                            public void result(int position)
                            {
                                SubCategoryData subCategoryData=list.get(position);
                                Intent intent;
                                if (!postType.equals("3"))
                                {
                                    intent = new Intent(context, PeopleFormActivity.class);
                                    intent.putExtra("CAT_ID", subCategoryData.getCategory_id());
                                    intent.putExtra("SUB_ID", subCategoryData.getSubcategory_id());
                                    intent.putExtra("POST_TYPE", postType);
                                    startActivity(intent);

                                } else {
                                    intent = new Intent(context, LostFoundByCategoryActivity.class);
                                    intent.putExtra("CAT_ID", subCategoryData.getCategory_id());
                                    intent.putExtra("SUB_ID", subCategoryData.getSubcategory_id());
                                    intent.putExtra("POST_TYPE", postType);
                                    startActivity(intent);
                                    finish();
                                }

                            }
                        }));


                        for(SubCategoryData subCategoryData : list)
                        {
                            System.out.println("title "+subCategoryData.getSubcategory_title());
                        }
                    }

                }

                @Override
                public void onFailure(Call<SubCategoryResponse> call, Throwable t) {

                }
            });
        } else
            {
                showInternetConnectionToast();
        }

    }


}
