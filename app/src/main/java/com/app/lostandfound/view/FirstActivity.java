package com.app.lostandfound.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.lostandfound.R;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Step onCreate");
        setContentView(R.layout.activity_first);
        Button button=(Button)findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SecondActivity.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("Step onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("Step onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("Step onResume"); }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("Step onStop"); }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("Step onPause");}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("Step onDestroy"); }
}
