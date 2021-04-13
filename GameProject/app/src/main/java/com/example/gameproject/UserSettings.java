package com.example.gameproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserSettings extends AppCompatActivity {
    EditText volume;
    EditText ups;
    CheckBox analytics;

    private void setup() {
        SharedPreferences preferences = getSharedPreferences( getPackageName() + "_preferences", MODE_PRIVATE);

        volume = (EditText) findViewById(R.id.VolumeVal);
        volume.setHint("" + preferences.getInt("volume", 100));

        ups = (EditText) findViewById(R.id.upsVal);
        ups.setHint("" + preferences.getInt("ups", 60));

        analytics = (CheckBox) findViewById(R.id.analyticsVal);

        if(preferences.getString("analytics", "true").equals("true")) {
            analytics.setSelected(true);
            analytics.setChecked(true);
        } else {
            analytics.setSelected(false);
            analytics.setChecked(false);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setup();

        Button save = findViewById(R.id.SaveBtn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences( getPackageName() + "_preferences", MODE_PRIVATE);
                SharedPreferences.Editor spEdit = preferences.edit();

                if(!volume.getText().toString().matches("") && !ups.getText().toString().matches("")) {
                    int volumeVal = Integer.parseInt(volume.getText().toString());
                    int upsVal = Integer.parseInt(ups.getText().toString());

                    if(volumeVal < 0) volumeVal = 0;
                    if(volumeVal > 100) volumeVal = 100;

                    if(upsVal < 30) upsVal = 30;
                    if(upsVal > 60) upsVal = 60;

                    spEdit.putInt("volume", volumeVal);
                    spEdit.putInt("ups", upsVal);

                    if(analytics.isChecked()) {
                        spEdit.putString("analytics", "true");
                    } else {
                        spEdit.putString("analytics", "false");
                    }

                    spEdit.apply();
                    Toast.makeText(getApplicationContext(), "Settings Saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Some of the fields are empty", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}