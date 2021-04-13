package com.example.gameproject;

import com.example.gameproject.object.Enemy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
    Level manages all of the enemies spawned on the screen based on the current level of the player
 */
public class Level {
    private static final int TIME_BETWEEN_LEVELS = 500;

    private int currentLevel;
    private int enemiesToSpawn;
    private int enemiesToKill;
    private int timeUntilNextSpawn;
    private static int timeBetweenSpawning;
    private List<String> randomEnemies = new ArrayList<String>();

    public Level() {
        currentLevel = 1;
        enemiesToSpawn = 10;
        enemiesToKill = enemiesToSpawn;
        timeBetweenSpawning = 150;
        timeUntilNextSpawn = TIME_BETWEEN_LEVELS;
        randomEnemies.add("A");
    }

    //pickRandomEnemy returns a random enemy type based on the current level
    public String pickRandomEnemy() {
        Random rand = new Random();
        int randomIndex = rand.nextInt(randomEnemies.size());

        return randomEnemies.get(randomIndex);
    }

    //readyToSpawn checks if a new Enemy should spawn based on a decided spawning time
    public boolean readyToSpawn() {
        //Check if we are in endless mode
        if(currentLevel != -1) {

            if (timeUntilNextSpawn <= 0 && enemiesToSpawn > 0) {
                timeUntilNextSpawn += timeBetweenSpawning;
                enemiesToSpawn--;
                return true;
            } else {
                timeUntilNextSpawn--;
                return false;
            }

        } else {

            if (timeUntilNextSpawn <= 0) {
                timeUntilNextSpawn += timeBetweenSpawning;
                return true;
            } else {
                timeUntilNextSpawn--;
                return false;
            }

        }

    }

    //killEnemy registers a killed enemy and checks if we have reached the next level
    public void killEnemy() {
        //Check if we are in endless mode
        if(currentLevel != -1) {
            enemiesToKill--;

            if(enemiesToKill <= 0) {
                currentLevel++;

                if(currentLevel == 1) {
                    enemiesToSpawn = 10;
                    enemiesToKill = enemiesToSpawn;
                    timeUntilNextSpawn = TIME_BETWEEN_LEVELS;
                    timeBetweenSpawning -= 10;
                    randomEnemies.add("A");
                } else if(currentLevel == 2) {
                    enemiesToSpawn = 20;
                    enemiesToKill = enemiesToSpawn;
                    timeUntilNextSpawn = TIME_BETWEEN_LEVELS;
                    timeBetweenSpawning -= 10;
                    randomEnemies.add("B");
                } else if(currentLevel == 3) {
                    enemiesToSpawn = 30;
                    enemiesToKill = enemiesToSpawn;
                    timeUntilNextSpawn = TIME_BETWEEN_LEVELS;
                    timeBetweenSpawning -= 10;
                    randomEnemies.add("C");
                } else {
                    //Go to endless mode
                    currentLevel = -1;
                    enemiesToSpawn = 0;
                    enemiesToKill = enemiesToSpawn;
                    timeUntilNextSpawn = TIME_BETWEEN_LEVELS;
                }

            }

        }

    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getEnemiesToKill() {
        return enemiesToKill;
    }

}
