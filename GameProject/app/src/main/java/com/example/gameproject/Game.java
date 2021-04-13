package com.example.gameproject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.gameproject.object.Circle;
import com.example.gameproject.object.Enemy;
import com.example.gameproject.object.Player;
import com.example.gameproject.object.Spell;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
    Game manages all of the objects inside the game
 */
public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private static final int MAX_ENEMIES_ON_SCREEN = 6;

    private final Player player;
    private final Joystick joystick;

    private GameLoop gameLoop;
    private Context context;
    private GameOver gameOver;
    private Level level;
    private Sound sound;
    private GamePanel panel;
    private List<Enemy> enemyList = new ArrayList<Enemy>();
    private List<Spell> spellList = new ArrayList<Spell>();

    private int joystickPointerId = 0;
    private int numberOfSpellsToCast = 0;
    private int screenWidth = 0;
    private int screenHeight = 0;

    public Game(Context context) {
        super(context);

        //Get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        this.context = context;

        //Get screen size
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        //Get the username from the previous activity
        SharedPreferences preferences = context.getSharedPreferences( context.getPackageName() + "_preferences", context.MODE_PRIVATE);
        String username = preferences.getString("username", "");

        //Initialize fields
        joystick = new Joystick(200, screenHeight - 200, 110, 80);
        gameLoop = new GameLoop(this, surfaceHolder, context);
        player = new Player(getContext(), joystick, 500, 500, 30, username);
        gameOver = new GameOver(context);
        sound = new Sound(context);
        level = new Level();
        panel = new GamePanel(context, level, gameLoop, player, screenWidth, screenHeight);

        sound.playSoundtrack();
        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                //Joystick was pressed
                if(joystick.getIsPressed()) {
                    //Cast spell
                    numberOfSpellsToCast++;
                }
                else if(joystick.isPressed((double) event.getX(), (double) event.getY())){
                    //Move
                    joystickPointerId = event.getPointerId(event.getActionIndex());
                    joystick.setIsPressed(true);
                } else {
                    //Cast spell
                    numberOfSpellsToCast++;
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                //Joystick is moving
                if(joystick.getIsPressed()) {
                    joystick.setActuator((double) event.getX(), (double) event.getY());
                }
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                //Joystick was let go
                if(joystickPointerId == event.getPointerId(event.getActionIndex())) {
                    joystick.setIsPressed(false);
                    joystick.resetActuator();
                }
                return true;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        SharedPreferences preferences = context.getSharedPreferences( context.getPackageName() + "_preferences", context.MODE_PRIVATE);

        //Check if analytics is enabled
        if(preferences.getString("analytics", "true").equals("true")) {
            panel.drawFPS(canvas);
            panel.drawUPS(canvas);
        }

        panel.drawPlayerName(canvas);
        panel.drawHealth(canvas);
        panel.drawScore(canvas);
        panel.drawLevel(canvas);
        panel.drawEnemiesLeft(canvas);

        player.draw(canvas);

        //Draw the enemies
        for(Enemy enemy : enemyList) {
            enemy.draw(canvas);
        }

        //Draw the spells
        for(Spell spell : spellList) {
            spell.draw(canvas);
        }

        //Joystick must be on top of everything, that is why it is drawn last
        joystick.draw(canvas);

        //Draw game over and stop the game
        if(player.getHealthPoints() <= 0) {
            gameOver.drawGameOver(canvas, screenWidth, screenHeight);
            gameOver.drawFinalScore(canvas, screenWidth, screenHeight, player.getScore());
            gameOver.saveUserToDatabase(player.getName(), player.getScore());
            gameLoop.stopLoop();
        }

    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        Log.d("Game.java", "surfaceCreated()");

        if(gameLoop.getState().equals(Thread.State.TERMINATED)) {
            gameLoop = new GameLoop(this, holder, context);
        }

        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        Log.d("Game.java", "surfaceChanged()");
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        Log.d("Game.java", "surfaceDestroyed()");
        sound.stopSoundtrack();

        if(gameLoop.getState().equals(Thread.State.TERMINATED)) {
            gameLoop = new GameLoop(this, holder, context);
        }

        gameLoop.stopLoop();
    }

    //Update game state
    public void update() {
        joystick.update();
        player.update();

        //Spawn new enemy if it is time to spawn and do not display more than the allowed number of enemies on screen
        if(enemyList.size() < MAX_ENEMIES_ON_SCREEN) {
            if(level.readyToSpawn()) {
                enemyList.add(new Enemy(getContext(), player, level.pickRandomEnemy(), screenWidth, screenHeight));
            }
        }

        //Cast the number of spells that is requested by the player
        while(numberOfSpellsToCast > 0) {
            spellList.add(new Spell(context, player));
            numberOfSpellsToCast--;
        }

        //Update the spells in the list
        for(Spell spell : spellList) {
            spell.update();
        }

        //Call the UpdateCollisions class on a separate thread
        UpdateCollisions updateCollisions = new UpdateCollisions(player, level, enemyList, spellList);
        Thread updateCollisionsThread = new Thread(updateCollisions);
        updateCollisionsThread.run();

        //Call the UpdateEnemies class on a separate thread
        UpdateEnemies updateEnemies = new UpdateEnemies(enemyList, player);
        Thread updateEnemiesThread = new Thread(updateEnemies);
        updateEnemies.run();
    }

}
