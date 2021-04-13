package com.example.gameproject;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class Utils {

    /**
     * getDistanceBetweenPoints returns the distance between the two points p1 and p2
     * @param p1x
     * @param p1y
     * @param p2x
     * @param p2y
     * @return
     */
    public static double getDistanceBetweenPoints(int p1x, int p1y, double p2x, double p2y) {
        return Math.sqrt(
                Math.pow(p1x - p2x, 2) +
                Math.pow(p1y - p2y, 2)
        );
    }

}
