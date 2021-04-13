package com.example.gameproject;

import com.example.gameproject.object.Circle;
import com.example.gameproject.object.Enemy;
import com.example.gameproject.object.Player;

import java.util.ArrayList;
import java.util.List;

/*
    UpdateEnemies handles the enemies AI on a separate thread due to the high amount of work on the main thread
 */
public class UpdateEnemies implements Runnable {
    private List<Enemy> enemyList = new ArrayList<Enemy>();
    private Player player;

    public UpdateEnemies(List<Enemy> enemyList, Player player) {
        this.enemyList = enemyList;
        this.player = player;
    }

    @Override
    public void run() {
        //In order to use the run method of the Runnable interface we need to call the sleep method
        try {
            Thread.sleep(1);
        } catch(Exception e) {
            e.printStackTrace();
        }

        for(int i = 0; i < enemyList.size(); i++) {
            Enemy curEnemy = enemyList.get(i);

            //Draw a raycast from the enemy to the player and move if there are no obstacles in the way
            if(!Circle.thereAreEnemiesAhead(curEnemy, enemyList, player, i)) {
                curEnemy.update();
            }

        }

    }

}
