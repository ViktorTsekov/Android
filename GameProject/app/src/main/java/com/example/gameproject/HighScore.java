package com.example.gameproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class HighScore extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;
    TextView highScoreView;

    private void setup() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Scores");
        highScoreView = (TextView) findViewById(R.id.HighScoreView);
    }


    private boolean internetIsAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void readFromDatabase() {

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<User> list = new ArrayList<>();

                //Read the entries from the database
                for(DataSnapshot ds : snapshot.getChildren()) {
                    String name = "";
                    String score = "";
                    int i = 0;

                    for(DataSnapshot dsChildren : ds.getChildren()) {

                        if(i == 0) {
                            name = dsChildren.getValue().toString();
                        } else if(i == 1) {
                            score = dsChildren.getValue().toString();
                        }

                        i++;
                    }

                    User user = new User(name, Integer.parseInt(score));
                    list.add(user);
                }

                int n = list.size();

                //Sort the entries
                for(int i = 0; i < n; i++) {

                    for(int j = 1; j < n - i; j++) {

                        if(list.get(j - 1).getScore() <= list.get(j).getScore()) {
                            Collections.swap(list, j - 1, j);
                        }

                    }

                }

                String txtPrint = "";
                int count = 1;

                //Print the highest ten results
                for(int i = 0; i < list.size(); i++) {
                    User user = list.get(i);
                    txtPrint += user.getName() + " " + user.getScore() + "\n";

                    if(count == 10) break;
                    count++;
                }

                highScoreView.setText(txtPrint);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("", "Failed to read value.", error.toException());
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        setup();

        //Check if we are connected to the internet
        if(internetIsAvailable()) {
            readFromDatabase();
        } else {
            highScoreView.setText("No Connection");
        }

    }

}