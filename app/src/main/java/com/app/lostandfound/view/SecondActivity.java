package com.app.lostandfound.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.lostandfound.R;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Step SecondActivity onCreate");
        setContentView(R.layout.activity_second);
        Button button=(Button)findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("Stepsss SecondActivity onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("Stepsss SecondActivity onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("Stepsss SecondActivity onResume"); }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("Stepsss SecondActivity onStop"); }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("Stepsss SecondActivity onPause");}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("Stepsss SecondActivity onDestroy"); }
}
