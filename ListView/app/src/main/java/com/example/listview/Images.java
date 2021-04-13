package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

public class Images extends AppCompatActivity {
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        image = (ImageView) findViewById(R.id.image);

        if(getIntent().hasExtra("ImageID")) {
            int id = getIntent().getExtras().getInt("ImageID");
            Bitmap bm = null;

            if(id == 0) {
                bm = BitmapFactory.decodeResource(getResources(), R.drawable.peach);
            } else if(id == 1) {
                bm = BitmapFactory.decodeResource(getResources(), R.drawable.squash);
            } else if(id == 2) {
                bm = BitmapFactory.decodeResource(getResources(), R.drawable.tomato);
            }

            Bitmap bmScaled = Bitmap.createScaledBitmap(bm, 150, 150, true);
            image.setImageBitmap(bmScaled);
        }

    }
}