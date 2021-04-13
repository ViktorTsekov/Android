package com.example.gameproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.UpdateLayout;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.gameproject.object.Player;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/*
    GameOver handles the logic for when the player is dead
 */
public class GameOver {
    private Context context;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    public GameOver(Context context) {
        this.context = context;
        this.database = FirebaseDatabase.getInstance();
        this.myRef = database.getReference("Scores");
    }

    public void drawGameOver(Canvas canvas, float screedWidth, float screenHeight) {
        int color = ContextCompat.getColor(context, R.color.gameOver);

        Paint paint = new Paint();
        paint.setColor(color);
        paint.setTextSize(150);

        canvas.drawText("GAME OVER", screedWidth / 2 - 300, screenHeight / 2, paint);
    }

    public void drawFinalScore(Canvas canvas, float screedWidth, float screenHeight, int score) {
        int color = ContextCompat.getColor(context, R.color.gameOver);

        Paint paint = new Paint();
        paint.setColor(color);
        paint.setTextSize(80);

        canvas.drawText("You managed to reach a score of " + score, screedWidth / 2 - 570, screenHeight / 2 + 100, paint);
    }

    //saveUserToDatabase saves the achieved result in the database
    public void saveUserToDatabase(String playerName, int playerScore) {

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int oldScore = 0;
                boolean userEncountered = false;

                //Read all entries from the database
                for(DataSnapshot ds : snapshot.getChildren()) {
                    String curName = "";
                    int curScore = 0;
                    int i = 0;

                    for(DataSnapshot dsChildren : ds.getChildren()) {

                        if(i == 0) {
                            curName = dsChildren.getValue().toString();

                            if(curName.equals(playerName)) {
                                userEncountered = true;
                            }

                        } else if(i == 1) {
                            curScore = Integer.parseInt(dsChildren.getValue().toString());

                            if(userEncountered) {
                                oldScore = curScore;
                                break;
                            }

                        }

                        i++;
                    }

                    if(userEncountered) {
                        break;
                    }

                }

                //Check if such user already exists in the database
                if(userEncountered) {

                    //If such user exists check if the new score is better than the old one and update it in the database
                    if(oldScore <= playerScore) {
                        User user = new User(playerName, playerScore);
                        myRef.child(playerName).setValue(user);
                    } else {
                        User user = new User(playerName, oldScore);
                        myRef.child(playerName).setValue(user);
                    }

                } else {
                    //If such user does not exist just create a new user
                    User user = new User(playerName, playerScore);
                    myRef.child(playerName).setValue(user);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("", "Failed to read value.", error.toException());
            }

        });

    }

}
