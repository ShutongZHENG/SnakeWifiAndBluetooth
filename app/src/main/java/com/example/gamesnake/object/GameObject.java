package com.example.gamesnake.object;

import android.graphics.Canvas;

import com.example.gamesnake.Game;

public abstract class GameObject {
    protected double positionX;
    protected double positionY;

    public GameObject(double positionX, double positionY){
        this.positionX = positionX;
        this.positionY = positionY;

    }


    public abstract void draw(Canvas canvas);
    public abstract void update();
    public double getPositionX(){return positionX;}
    public double getPositionY(){return positionY;}
    public static boolean isColliding(GameObject obj1, GameObject obj2){
        if (obj1.positionX == obj2.positionX && obj1.positionY == obj2.positionY)
            return true;
        else
            return false;
    }


}
