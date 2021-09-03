package com.app.lostandfound.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.app.lostandfound.R;
import com.app.lostandfound.adapter.CategoryAdapter;
import com.app.lostandfound.adapter.SubCategoryListAdapter;
import com.app.lostandfound.costomview.CustomTextView;
import com.app.lostandfound.lostfoundinterface.ApiCallback;
import com.app.lostandfound.utility.Constant;

public class FilterActivity extends AppCompatActivity {
    RecyclerView recyclerViewCategory,recyclerViewSubCategory;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        context = this;
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
    ((CustomTextView)findViewById(R.id.header_toolbar)).setText(context.getResources().getString(R.string.filter));
    recyclerViewCategory=(RecyclerView)findViewById(R.id.recyclerviewCategory);
    recyclerViewCategory.setLayoutManager(new LinearLayoutManager(context));
    recyclerViewSubCategory=(RecyclerView)findViewById(R.id.recyclerviewSubCategory);
    recyclerViewSubCategory.setLayoutManager(new LinearLayoutManager(context));
    int[] colors = context.getResources().getIntArray(R.array.rainbow);
////        TypedArray  = getContext().getResources().getIntArray(R.array.progress_colors_light);
    CategoryAdapter categoryAdapter = new CategoryAdapter(context, colors, new ApiCallback() {
        @Override
        public void result(int position) {


        }
    });
    recyclerViewCategory.setAdapter(categoryAdapter);

//    SubCategoryListAdapter subCategoryListAdapter=new SubCategoryListAdapter(context, new ApiCallback() {
//        @Override
//        public void result(int position)
//        {
//
//        }
//    });
//    recyclerViewSubCategory.setAdapter(subCategoryListAdapter);




}
    private void initViewold() {

//        ((ImageView)findViewById(R.id.imgback)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//        ((CustomTextView)findViewById(R.id.header_toolbar)).setText(context.getResources().getString(R.string.filter));
//        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
//
////        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false);
//        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(staggeredGridLayoutManager);
//        int[] colors = context.getResources().getIntArray(R.array.rainbow);
////        TypedArray  = getContext().getResources().getIntArray(R.array.progress_colors_light);
//        CategoryAdapter categoryAdapter = new CategoryAdapter(context, colors, new ApiCallback() {
//            @Override
//            public void result(int position) {
//                Intent intent = new Intent(context, SubCategoryActivity.class);
//                intent.putExtra("ID", "id");
////                getActivity().startActivity(intent);
//                startActivityForResult(intent, Constant.SUB_CATEGORY_CODE);
//
//            }
//        });
//        recyclerView.setAdapter(categoryAdapter);


    }
}
