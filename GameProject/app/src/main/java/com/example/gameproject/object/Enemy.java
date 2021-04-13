package com.example.gameproject.object;

import android.content.Context;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.example.gameproject.GameLoop;
import com.example.gameproject.R;

/*
    Enemy is a character which always moves in the direction of the player.
    The Enemy class is an extension of a Circle, which is an extension of a GameObject.
 */
public class Enemy extends Circle {
    private static double MAX_SPEED;

    private final Player player;
    private int health = 0;
    private int color = 0;
    private int speed = 0;
    private double directionX = 0;
    private double directionY = 0;
    private double distanceToPlayer = 0;
    private String enemyType;

    public Enemy(Context context, Player player, double positionX, double positionY, double radius) {
        //Call constructor of parent
        super(context, ContextCompat.getColor(context, R.color.enemyDefault), positionX, positionY, radius);

        //Initialize fields
        this.player = player;
    }

    public Enemy(Context context, Player player, String enemyType, int boundaryX, int boundaryY) {
        //Call parent constructor
        super(
                context,
                ContextCompat.getColor(context, R.color.enemyDefault),
                Math.random() * (boundaryX - 60),
                Math.random() * (boundaryY - 60),
                30
        );

        //Initialize fields
        this.player = player;
        this.enemyType = enemyType;
        calculateDirectionToPlayer();
        determineEnemyStats(enemyType, context);
    }

    public double getDirectionX() {
        return directionX;
    }

    public double getDirectionY() {
        return directionY;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String getEnemyType() {
        return enemyType;
    }

    @Override
    public void update() {
        calculateDirectionToPlayer();

        //Set velocity in direction to the player
        if(distanceToPlayer > 0) {
            velocityX = directionX * MAX_SPEED;
            velocityY = directionY * MAX_SPEED;
        } else {
            velocityX = 0;
            velocityY = 0;
        }

        //Update the position of the enemy
        positionX += velocityX;
        positionY += velocityY;
    }

    //determineEnemyStats determines the stats of the enemy based on the provided type
    private void determineEnemyStats(String enemyType, Context context) {

        if(enemyType.equals("A")) {
            this.health = 50;
            this.speed = 220;
            MAX_SPEED = speed / GameLoop.MAX_UPS;
            this.color = ContextCompat.getColor(context, R.color.enemyTypeA);
            this.setPaint(color);
            this.setRadius(30);
        } else if(enemyType.equals("B")) {
            this.health = 100;
            this.speed = 240;
            MAX_SPEED = speed / GameLoop.MAX_UPS;
            this.color = ContextCompat.getColor(context, R.color.enemyTypeB);
            this.setPaint(color);
            this.setRadius(35);
        } else if(enemyType.equals("C")) {
            this.health = 150;
            this.speed = 200;
            MAX_SPEED = speed / GameLoop.MAX_UPS;
            this.color = ContextCompat.getColor(context, R.color.enemyTypeC);
            this.setPaint(color);
            this.setRadius(40);
        }

    }

    //calculateDirectionToPlayer calculates the vector pointing to the player
    private void calculateDirectionToPlayer() {
        //Calculate vector from enemy to player in x and y coordinates
        double distanceToPlayerX = player.getPositionX() - positionX;
        double distanceToPlayerY = player.getPositionY() - positionY;

        //Calculate absolute distance between enemy and player
        distanceToPlayer = GameObject.getDistanceBetweenObjects(this, player);

        //Calculate direction from enemy to player
        directionX = distanceToPlayerX / distanceToPlayer;
        directionY = distanceToPlayerY / distanceToPlayer;
    }

}
