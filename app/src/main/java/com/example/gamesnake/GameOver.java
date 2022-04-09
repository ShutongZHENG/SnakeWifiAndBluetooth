package com.example.gamesnake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.gamesnake.object.Snake;

public class GameOver {
    private double positionX;
    private double positionY;
    private static final int width = 400;
    private static final int length = 1000;
    private Snake snake;

    public GameOver(Snake snake) {
        positionX = 800;
        positionY = 200;
        this.snake = snake;
    }

    public void draw(Canvas canvas) {


        Paint paintRect = new Paint();
        Paint paintWords = new Paint();
        paintRect.setStyle(Paint.Style.FILL);
        paintRect.setColor(Color.WHITE);
        paintWords.setTextAlign(Paint.Align.CENTER);
        paintWords.setColor(Color.BLACK);
        paintWords.setTextSize(75);
        Rect rect = new Rect((int) positionX, (int) positionY, (int) positionX + length, (int) positionY + width);
        canvas.drawRect(rect, paintRect);
        String str = "Score: " + (snake.squares.size()-2);
        canvas.drawText(str, (float) positionX + 500, (float) positionY + 200, paintWords);

    }

}
