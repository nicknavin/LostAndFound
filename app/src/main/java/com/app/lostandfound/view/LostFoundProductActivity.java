package com.app.lostandfound.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.app.lostandfound.R;
import com.app.lostandfound.adapter.LostFoundProductAdapter;
import com.app.lostandfound.adapter.SubCategoryListAdapter;
import com.app.lostandfound.costomview.CustomTextView;
import com.app.lostandfound.lostfoundinterface.ApiCallback;
import com.app.lostandfound.utility.Constant;

public class LostFoundProductActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_found_product);
        initView();
    }

    private void initView()
    {
        ((ImageView)findViewById(R.id.imgback)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ((CustomTextView)findViewById(R.id.header_toolbar)).setText("Lost Item Name");
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        LostFoundProductAdapter lostFoundProductAdapter=new LostFoundProductAdapter(context);
       recyclerView.setAdapter(lostFoundProductAdapter);
    }



}
