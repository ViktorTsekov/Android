package com.example.database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    EditText id;
    EditText name;
    EditText score;

    FirebaseDatabase database;
    DatabaseReference myRef;

    void setup() {
        textView = (TextView) findViewById(R.id.TextView);
        id = (EditText) findViewById(R.id.UserID);
        name = (EditText) findViewById(R.id.Name);
        score = (EditText) findViewById(R.id.Score);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Android Score");
    }

    void storeToDatabase(String id, String name, String score) {
        DataObject obj = new DataObject(name, score);

        myRef.child(id).setValue(obj);

        Toast.makeText(MainActivity.this, "Data Stored", Toast.LENGTH_LONG).show();
    }

    void readFromDatabase() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList list = new ArrayList();

                for(DataSnapshot ds : snapshot.getChildren()) {
                    String data = ds.getKey() + ": ";

                    for(DataSnapshot dsChildren : ds.getChildren()) {
                        data = data + " " + dsChildren.getValue();
                    }

                    list.add(data);
                }

                int i = 0;
                Iterator iter = list.iterator();
                String txtPrint = "";

                while(iter.hasNext() && i < 5) {
                    txtPrint = txtPrint + iter.next() + "\n";
                    i++;
                }

                textView.setText("Data Stored:\n\n" + txtPrint.toString());
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
        setContentView(R.layout.activity_main);

        setup();

        //Write
        Button storeData = (Button) findViewById(R.id.StoreData);
        storeData.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                storeToDatabase(id.getText().toString(), name.getText().toString(), score.getText().toString());
            }
        });

        //Read
        Button readData = (Button) findViewById(R.id.ReadData);
        readData.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                readFromDatabase();
            }
        });

    }
}