package com.example.gameproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SetUsername extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_username);

        Button proceed = findViewById(R.id.ProceedBtn);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences( getPackageName() + "_preferences", MODE_PRIVATE);
                SharedPreferences.Editor spEdit = preferences.edit();
                EditText username = (EditText) findViewById(R.id.UsernameInp);
                Intent intent = new Intent(getApplicationContext(), GameView.class);

                if(!username.getText().toString().matches("")) {
                    spEdit.putString("username", username.getText().toString());
                    spEdit.apply();
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Some of the fields are empty", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}