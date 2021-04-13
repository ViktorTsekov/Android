package com.example.gameproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.gameproject.object.Player;

/*
    PanelDrawer handles all of the text panels that are drawn on the screen
 */
public class GamePanel {
    private Context context;
    private Level level;
    private GameLoop gameLoop;
    private Player player;
    private int screenWidth;
    private int screenHeight;

    public GamePanel(Context context, Level level, GameLoop gameLoop, Player player, int screenWidth, int screenHeight) {
        this.context = context;
        this.level = level;
        this.gameLoop = gameLoop;
        this.player = player;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void drawPlayerName(Canvas canvas) {
        String name = player.getName();
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.white);

        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText(name, 100, 100, paint);
    }

    public void drawUPS(Canvas canvas) {
        String averageUPS = String.format("%.0f", gameLoop.getAverageUPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.white);

        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("UPS: " + averageUPS, 100, 200, paint);
    }

    public void drawFPS(Canvas canvas) {
        String averageFPS = String.format("%.0f", gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.white);

        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("FPS: " + averageFPS, 100, 300, paint);
    }

    public void drawHealth(Canvas canvas) {
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.white);

        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("Health: " + player.getHealthPoints(), screenWidth - 370, 100, paint);
    }

    public void drawScore(Canvas canvas) {
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.white);

        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("Score: " + player.getScore(), screenWidth - 370, 200, paint);
    }

    public void drawLevel(Canvas canvas) {
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.white);

        paint.setColor(color);
        paint.setTextSize(50);

        //Check if we are in endless mode
        if(level.getCurrentLevel() != -1) {
            canvas.drawText("Level: " + level.getCurrentLevel(), screenWidth - 370, 400, paint);
        } else {
            canvas.drawText("Endless mode", screenWidth - 370, 400, paint);
        }

    }

    public void drawEnemiesLeft(Canvas canvas) {
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.white);

        paint.setColor(color);
        paint.setTextSize(50);

        //Check if we are in endless mode
        if(level.getCurrentLevel() != -1) {
            canvas.drawText("Enemies left: " + level.getEnemiesToKill(), screenWidth - 370, 300, paint);
        } else {
            canvas.drawText("Enemies left: ∞", screenWidth - 370, 300, paint);
        }

    }

}
