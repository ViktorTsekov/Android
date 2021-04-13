package com.example.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Google
        Button google = findViewById(R.id.google);
        google.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Uri google = Uri.parse("https://google.com");
                Intent gotoGoogle = new Intent(Intent.ACTION_VIEW, google);
                startActivity(gotoGoogle);
            }
        });

        //Second activity
        Button secondActivity = findViewById(R.id.activity);
        secondActivity.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        });

    }
}