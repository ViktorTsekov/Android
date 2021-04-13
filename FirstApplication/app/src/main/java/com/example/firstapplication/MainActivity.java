package com.example.firstapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView output;

    int maxNum = 20;
    int randomNum;
    int guessVal;

    private void Setup() {
        Random random = new Random();
        String instr = String.format("Pick a number between 1 and %d", maxNum);
        TextView instructions = (TextView)findViewById(R.id.Instructions);

        output = (TextView)findViewById(R.id.Output);
        instructions.setText(instr);
        randomNum = random.nextInt(maxNum - 1) + 1;
    }

    private void guessNumber() {
        EditText edit = (EditText)findViewById(R.id.Edit);

        guessVal = Integer.parseInt(edit.getText().toString());

        if(guessVal >= 1 && guessVal <= maxNum) {

            if(guessVal == randomNum) {
                output.setText("Right Answer");
            } else {
                output.setText(String.format("Try again, the right number was %d", randomNum));
            }

        }

    }

    private void generateNewNumber() {
        Random random = new Random();
        randomNum = random.nextInt(maxNum - 1) + 1;
        output.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Setup();

        Button submitBtn = (Button)findViewById(R.id.SubmitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                guessNumber();
            }
        });

        Button newBtn = (Button)findViewById(R.id.generate);
        newBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                generateNewNumber();
            }
        });

    }
}