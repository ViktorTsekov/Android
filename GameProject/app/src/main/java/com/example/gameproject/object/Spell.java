package com.example.gameproject.object;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.gameproject.GameLoop;
import com.example.gameproject.R;
import com.example.gameproject.object.Circle;
import com.example.gameproject.object.Player;

public class Spell extends Circle {
    private static final double SPEED_PIXELS_PER_SECOND = 600.0;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;

    private final Player spellCaster;

    public Spell(Context context, Player spellCaster) {
        //Call constructor of parent
        super(context, ContextCompat.getColor(context, R.color.spell), spellCaster.getPositionX(), spellCaster.getPositionY(), 25);

        //Initialize fields
        this.spellCaster = spellCaster;
        velocityX = spellCaster.getDirectionX() * MAX_SPEED;
        velocityY = spellCaster.getDirectionY() * MAX_SPEED;
    }

    @Override
    public void update() {
        positionX += velocityX;
        positionY += velocityY;
    }

}
