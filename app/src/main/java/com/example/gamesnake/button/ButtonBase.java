package com.example.gamesnake.button;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.gamesnake.R;

public abstract class ButtonBase {
    protected double positionX;
    protected double positionY;
    protected String btContext;
    private static final int width = 100;
    private static final int length = 300;
    public ButtonBase(double positionX, double positionY, String btContext){
        this.btContext = btContext;
        this.positionX= positionX;
        this.positionY = positionY;
    }
    public void draw(Canvas canvas){
        Rect rect = new Rect((int)positionX,(int)positionY,(int)positionX+length, (int)positionY+width);
        Paint paint = new Paint();
        Paint paintWords = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.GRAY);
        paintWords.setColor(Color.BLACK);
        paintWords.setTextAlign(Paint.Align.CENTER);
        paintWords.setTextSize(50);
        canvas.drawRect(rect,paint);
        canvas.drawText(btContext,(float) positionX+150,(float)positionY+70,paintWords);
    }
    public abstract void update();
    public abstract void onClick();


}
