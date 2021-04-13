package com.example.gameproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

import java.text.DecimalFormat;

public class GameLoop extends Thread {
    public static double MAX_UPS;
    private static double UPS_PERIOD;

    private SurfaceHolder surfaceHolder;
    private Game game;

    private boolean isRunning;
    private double averageUPS;
    private double averageFPS;

    public GameLoop(Game game, SurfaceHolder surfaceHolder, Context context) {
        SharedPreferences preferences = context.getSharedPreferences( context.getPackageName() + "_preferences", context.MODE_PRIVATE);

        this.isRunning = false;
        this.surfaceHolder = surfaceHolder;
        this.game = game;
        this.MAX_UPS = preferences.getInt("ups", 60);
        UPS_PERIOD = 1E3 / MAX_UPS;
    }

    public double getAverageUPS() {
        return averageUPS;
    }

    public double getAverageFPS() {
        return averageFPS;
    }

    public void startLoop() {
        isRunning = true;
        start();
    }

    public void stopLoop() {
        isRunning = false;
    }

    @Override
    public void run() {
        super.run();

        //Declare count and cycle variables
        int updateCount = 0;
        int frameCount = 0;

        long startTime = 0;
        long elapsedTime = 0;
        long sleepTime = 0;

        //Game loop
        Canvas canvas = null;
        startTime = System.currentTimeMillis();
        while(isRunning) {
            //Update and render the game
            try {
                canvas = surfaceHolder.lockCanvas();

                synchronized (surfaceHolder) {
                    game.update();
                    updateCount++;
                    game.draw(canvas);
                }

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } finally {
                if(canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                        frameCount++;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            //Pause game loop to not exceed target UPS
            elapsedTime = System.currentTimeMillis() - startTime;
            sleepTime = (long) (updateCount * UPS_PERIOD - elapsedTime);
            if(sleepTime > 0) {
                try {
                    sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //Skip frames to keep up with target UPS
            while (sleepTime < 0 && updateCount < MAX_UPS - 1) {
                game.update();
                updateCount++;
                elapsedTime = System.currentTimeMillis() - startTime;
                sleepTime = (long) (updateCount * UPS_PERIOD - elapsedTime);
            }

            //Calculate average UPS and FPS
            elapsedTime = System.currentTimeMillis() - startTime;
            if (elapsedTime >= 1000) {
                averageUPS = updateCount / (1E-3 * elapsedTime);
                averageFPS = frameCount / (1E-3 * elapsedTime);
                updateCount = 0;
                frameCount = 0;
                startTime = System.currentTimeMillis();
            }

        }

    }

}
