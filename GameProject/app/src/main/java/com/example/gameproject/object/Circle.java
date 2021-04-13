package com.example.gameproject.object;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.List;

/*
    Circle is an abstract class which implements a draw method from GameObject for drawing the com.example.gameproject.object as a circle.
 */
public abstract class Circle extends GameObject {
    protected double radius;
    protected Paint paint;

    public Circle(Context context, int color, double positionX, double positionY, double radius) {
        //Call constructor of parent
        super(positionX, positionY);

        //Initialize fields
        paint = new Paint();
        paint.setColor(color);
        this.radius = radius;
    }

    /**
     * thereAreEnemiesAhead draws a vector from the passed object to a target object and checks if there any enemies in the way
     * @param originPoint
     * @param listOfObjects
     * @param endPoint
     * @param currentIndex
     * @return
     */
    public static boolean thereAreEnemiesAhead(Circle originPoint, List<Enemy> listOfObjects, Circle endPoint, int currentIndex) {
        //The position of the origin point
        double originX = originPoint.getPositionX() + originPoint.getRadius() / 2;
        double originY = originPoint.getPositionY() + originPoint.getRadius() / 2;

        //The position of the current pointer on the screen
        double currentX = originX;
        double currentY = originY;

        //The position of the end point
        double endX = endPoint.getPositionX() + endPoint.getRadius() / 2;
        double endY = endPoint.getPositionY() + endPoint.getRadius() / 2;

        //The direction in which the vector is pointing
        double directionX = originPoint.getDirectionX();
        double directionY = originPoint.getDirectionY();

        //Draw vector until we reach the coordinates of the end point
        while((currentX >= endX - endPoint.getRadius() && currentX <= endX + endPoint.getRadius()) || (currentY >= endY - endPoint.getRadius() && currentY <= endY + endPoint.getRadius())) {
            currentX += directionX;
            currentY += directionY;

            for(int i = 0; i < listOfObjects.size(); i++) {

                //Check indexes to avoid self comparison
                if(i != currentIndex) {
                    Circle curObj = listOfObjects.get(i);
                    double curObjX = curObj.getPositionX() + curObj.getRadius() / 2;
                    double curObjY = curObj.getPositionY() + curObj.getRadius() / 2;

                    //Return true if we encounter an object during the drawing of the vector
                    if ((currentX >= curObjX - curObj.getRadius() && currentX <= curObjX + curObj.getRadius()) || (currentY >= curObjY - curObj.getRadius() && currentY <= curObjY + curObj.getRadius())) {
                        return true;
                    }

                }

            }

        }

        return false;
    }

    /**
     * isColliding checks if two circle objects are colliding, based on their position and radii
     * @param obj1
     * @param obj2
     * @return
     */
    public static boolean isColliding(Circle obj1, Circle obj2) {
        double distance = getDistanceBetweenObjects(obj1, obj2);
        double distanceToCollision = obj1.getRadius() + obj2.getRadius();

        if(distance < distanceToCollision) {
            return true;
        } else {
            return false;
        }

    }

    public void draw(Canvas canvas) {
        canvas.drawCircle((float) positionX, (float) positionY, (float) radius, paint);
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setPaint(int color) {
        paint = new Paint();
        paint.setColor(color);
    }

}
