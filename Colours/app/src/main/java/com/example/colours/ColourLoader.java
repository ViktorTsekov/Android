package com.example.colours;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ColourLoader extends AppCompatActivity {
    TextView colour1;
    TextView colour2;
    TextView colour3;

    void setup() {
        SharedPreferences sp = getSharedPreferences(getResources().getString(R.string.shared_preferences).toString(), MODE_PRIVATE);

        colour1 = (TextView) findViewById(R.id.Col1);
        colour2 = (TextView) findViewById(R.id.Col2);
        colour3 = (TextView) findViewById(R.id.Col3);

        int col1 = Color.rgb(sp.getInt("CR1", 100), sp.getInt("CG1", 100), sp.getInt("CB1", 100));
        int col2 = Color.rgb(sp.getInt("CR2", 100), sp.getInt("CG2", 100), sp.getInt("CB2", 100));
        int col3 = Color.rgb(sp.getInt("CR3", 100), sp.getInt("CG3", 100), sp.getInt("CB3", 100));

        colour1.setBackgroundColor(col1);
        colour2.setBackgroundColor(col2);
        colour3.setBackgroundColor(col3);
    }

    void loadColour(int n) {
        SharedPreferences sp = getSharedPreferences(getResources().getString(R.string.shared_preferences).toString(), MODE_PRIVATE);
        SharedPreferences.Editor spEdit = sp.edit();

        if(n == 1) {
            spEdit.putInt("CurrentRedColour", sp.getInt("CR1", 100));
            spEdit.putInt("CurrentGreenColour", sp.getInt("CG1", 100));
            spEdit.putInt("CurrentBlueColour", sp.getInt("CB1", 100));
        } else if(n == 2) {
            spEdit.putInt("CurrentRedColour", sp.getInt("CR2", 100));
            spEdit.putInt("CurrentGreenColour", sp.getInt("CG2", 100));
            spEdit.putInt("CurrentBlueColour", sp.getInt("CB2", 100));
        } else if(n == 3) {
            spEdit.putInt("CurrentRedColour", sp.getInt("CR3", 100));
            spEdit.putInt("CurrentGreenColour", sp.getInt("CG3", 100));
            spEdit.putInt("CurrentBlueColour", sp.getInt("CB3", 100));
        }

        spEdit.apply();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colour_loader);

        setup();

        Button col1btn = findViewById(R.id.Col1btn);
        col1btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                loadColour(1);
                startActivity(new Intent(ColourLoader.this, MainActivity.class));
            }
        });

        Button col2btn = findViewById(R.id.Col2btn);
        col2btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                loadColour(2);
                startActivity(new Intent(ColourLoader.this, MainActivity.class));
            }
        });

        Button col3btn = findViewById(R.id.Col3btn);
        col3btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                loadColour(3);
                startActivity(new Intent(ColourLoader.this, MainActivity.class));
            }
        });
    }
}