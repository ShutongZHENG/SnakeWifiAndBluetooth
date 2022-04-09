package com.example.gamesnake.object;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public abstract class Square extends GameObject {
    protected static final int width = 35;
    protected static final int length = 35;
    protected Paint paint;

    public Square(int color, double positionX, double positionY){
        super(positionX,positionY);
        paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
    }

    public void draw(Canvas canvas){
        Rect r = new Rect(564+(int)positionX*width, (int)positionY*length, 564+(int)(positionX+1)*width, (int)(positionY+1)*length);
        canvas.drawRect(r,paint);


    }
    public void setPosition(double positionX, double positionY){
        this.positionX = positionX;
        this.positionY = positionY;
    }
    public int getColor(){
        return paint.getColor();
    }



}
