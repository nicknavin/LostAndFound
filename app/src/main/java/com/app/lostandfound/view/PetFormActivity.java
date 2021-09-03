package com.app.lostandfound.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.app.lostandfound.R;
import com.app.lostandfound.base.BaseActivity;
import com.app.lostandfound.costomview.CustomTextView;

public class PetFormActivity extends BaseActivity {

    CustomTextView btnNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_form);
        initView();
    }

    private void initView()
    {
        ((CustomTextView)findViewById(R.id.header_toolbar)).setText(context.getResources().getString(R.string.add_detail));

        btnNext=(CustomTextView)findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,LastLocationActivity.class);
                startActivity(intent);

            }
        });

        ((ImageView)findViewById(R.id.imgBack)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }




}
