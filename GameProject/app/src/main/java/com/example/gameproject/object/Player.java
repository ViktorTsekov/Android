package com.example.gameproject.object;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.DisplayMetrics;

import androidx.core.content.ContextCompat;

import com.example.gameproject.GameLoop;
import com.example.gameproject.Joystick;
import com.example.gameproject.R;
import com.example.gameproject.Utils;

/*
    Player is the main character of the game which the user can control using a joystick.
    The player class is an extension of a Circle, which is an extension of a GameObject.
 */
public class Player extends Circle {
    public static final int MAX_HEALTH = 100;
    private static final double SPEED_PIXELS_PER_SECOND = 400.0;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;

    private Joystick joystick;
    private int screenHeight;
    private int screenWidth;
    private int healthPoints;
    private int score;
    private String name;

    public Player(Context context, Joystick joystick, double positionX, double positionY, double radius, String username) {
        //Call constructor of parent
        super(context, ContextCompat.getColor(context, R.color.player), positionX, positionY, radius);

        //Initialize fields
        this.joystick = joystick;
        this.healthPoints = MAX_HEALTH;
        this.score = 0;
        this.name = username;

        //Get screen size
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;
    }

    public void update() {
        //Update velocity based on the actuator of the joystick
        velocityX = joystick.getActuatorX() * MAX_SPEED;
        velocityY = joystick.getActuatorY() * MAX_SPEED;

        //Update player position
        positionX += velocityX;
        positionY += velocityY;

        //Update direction
        if(velocityX != 0 || velocityY != 0) {
            //Normalize velocity to get a direction
            double distance = Utils.getDistanceBetweenPoints(0, 0, velocityX, velocityY);

            directionX = velocityX / distance;
            directionY = velocityY / distance;
        }

        //Check if the player goes out of the screen
        if(positionX < radius) {
            positionX = radius;
        } else if(positionX > screenWidth - radius) {
            positionX = screenWidth - radius;
        }

        if(positionY < radius) {
            positionY = radius;
        } else if(positionY > screenHeight - radius) {
            positionY = screenHeight - radius;
        }

    }

    public void setPosition(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    public void setHealthPoints(int health) {
        this.healthPoints = health;

        if(healthPoints < 0) {
            healthPoints = 0;
        }

    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

}
