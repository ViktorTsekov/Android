package com.example.colours;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout CL;
    EditText redET;
    EditText greenET;
    EditText blueET;

    void setup() {
        CL = (ConstraintLayout) findViewById(R.id.root);
        redET = (EditText) findViewById(R.id.red);
        greenET = (EditText) findViewById(R.id.green);
        blueET = (EditText) findViewById(R.id.blue);

        SharedPreferences sp = getSharedPreferences(getResources().getString(R.string.shared_preferences).toString(), MODE_PRIVATE);
        setBackground(sp.getInt("CurrentRedColour", 100), sp.getInt("CurrentGreenColour", 100), sp.getInt("CurrentBlueColour", 100));
    }

    void setBackground(int r, int g, int b) {
        int col = Color.rgb(r, g, b);
        CL.setBackgroundColor(col);
    }

    void saveData(int n, int r, int g, int b) {
        SharedPreferences sp = getSharedPreferences(getResources().getString(R.string.shared_preferences).toString(), MODE_PRIVATE);
        SharedPreferences.Editor spEdit = sp.edit();

        if (n == 1) {
            spEdit.putInt("CR1", r);
            spEdit.putInt("CG1", g);
            spEdit.putInt("CB1", b);
        } else if (n == 2) {
            spEdit.putInt("CR2", r);
            spEdit.putInt("CG2", g);
            spEdit.putInt("CB2", b);
        } else if (n == 3) {
            spEdit.putInt("CR3", r);
            spEdit.putInt("CG3", g);
            spEdit.putInt("CB3", b);
        }

        spEdit.apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setup();

        //Load
        Button load = findViewById(R.id.Load);
        load.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ColourLoader.class));
            }
        });

        //Set Colour
        Button setColour = findViewById(R.id.SetColour);
        setColour.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int red = Integer.parseInt(redET.getText().toString());
                int green = Integer.parseInt(greenET.getText().toString());
                int blue = Integer.parseInt(blueET.getText().toString());
                setBackground(red, green, blue);
            }
        });

        //Save
        Button save = findViewById(R.id.Save);
        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText colourNumber = (EditText) findViewById(R.id.ColourNumber);

                int n = Integer.parseInt(colourNumber.getText().toString());
                int red = Integer.parseInt(redET.getText().toString());
                int green = Integer.parseInt(greenET.getText().toString());
                int blue = Integer.parseInt(blueET.getText().toString());

                if(n > 3) n = 3;
                if(n < 1) n = 1;

                saveData(n, red, green, blue);
            }
        });

    }
    
}