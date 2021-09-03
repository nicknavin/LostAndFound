package com.app.lostandfound.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.app.lostandfound.R;
import com.app.lostandfound.adapter.LostFoundProductAdapter;
import com.app.lostandfound.base.BaseActivity;

public class SearchActivity extends BaseActivity {

    RecyclerView recyclerView;
    LostFoundProductAdapter lostFoundProductAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
    }


    private void initView()
    {
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
recyclerView.setLayoutManager(new LinearLayoutManager(context));
        lostFoundProductAdapter=new LostFoundProductAdapter(context);
        recyclerView.setAdapter(lostFoundProductAdapter);
    }

}
