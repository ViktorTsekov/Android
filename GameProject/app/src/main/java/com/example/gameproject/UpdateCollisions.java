package com.example.gameproject;

import com.example.gameproject.object.Circle;
import com.example.gameproject.object.Enemy;
import com.example.gameproject.object.Player;
import com.example.gameproject.object.Spell;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
    UpdateCollisions handles the collisions in the game on a separate thread due to the high amount of work on the main thread
 */
public class UpdateCollisions implements Runnable {
    private Player player;
    private Level level;
    private List<Enemy> enemyList = new ArrayList<Enemy>();
    private List<Spell> spellList = new ArrayList<Spell>();

    public UpdateCollisions(Player player, Level level, List<Enemy> enemyList, List<Spell> spellList) {
        this.player = player;
        this.level = level;
        this.enemyList = enemyList;
        this.spellList = spellList;
    }

    @Override
    public void run() {
        //In order to use the run method of the Runnable interface we need to call the sleep method
        try {
            Thread.sleep(1);
        } catch(Exception e) {
            e.printStackTrace();
        }

        Iterator<Enemy> iteratorEnemy = enemyList.iterator();
        Iterator<Spell> iteratorSpell = spellList.iterator();

        //Iterate trough the list of enemies
        while (iteratorEnemy.hasNext()) {
            Enemy enemy = iteratorEnemy.next();

            //Remove the enemy and urt the player if it collides with the player
            if(Circle.isColliding(enemy, player)) {
                iteratorEnemy.remove();
                player.setHealthPoints(player.getHealthPoints() - 10);
                level.killEnemy();
                continue;
            }

            //Iterate trough the list of spells and check if there are collisions with the enemy
            while(iteratorSpell.hasNext()) {
                Circle spell = iteratorSpell.next();

                //Remove the spell and hurt the enemy if they collide with each other
                if(Circle.isColliding(spell, enemy)) {
                    iteratorSpell.remove();
                    enemy.setHealth(enemy.getHealth() - 50);

                    if(enemy.getHealth() <= 0) {
                        String enemyType = enemy.getEnemyType();

                        if(enemyType.equals("A")) {
                            player.setScore(player.getScore() + 100);
                        } else if(enemyType.equals("B")) {
                            player.setScore(player.getScore() + 200);
                        } else if(enemyType.equals("C")) {
                            player.setScore(player.getScore() + 300);
                        }

                        iteratorEnemy.remove();
                        level.killEnemy();
                    }

                    break;
                }

            }

        }

    }

}


