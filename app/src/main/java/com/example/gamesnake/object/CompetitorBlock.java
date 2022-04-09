package com.example.gamesnake.object;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class CompetitorBlock extends GameObject{
    // public boolean gameStatus;
    public boolean isSeed;
    private Paint paintSeed;
    private Paint paintSnake;
    private static final int colorSeed = Color.BLUE;
    private static final int colorSnake = Color.RED;
    private static final int width =10;
    private static final int length =10;



    public CompetitorBlock(double positionX, double positionY , boolean isSeed) {
        super(positionX, positionY);
        this.isSeed = isSeed;
        paintSeed = new Paint();
        paintSnake = new Paint();
        paintSnake.setStyle(Paint.Style.FILL);
        paintSeed.setStyle(Paint.Style.FILL);
        paintSeed.setColor(colorSeed);
        paintSnake.setColor(colorSnake);

    }

    @Override
    public void draw(Canvas canvas) {

        if (isSeed){
            Rect rectSeed = new Rect(30+(int)positionX*width, 712+(int)positionY*length,
                    30+(int)(positionX+1)*width, 712+(int)(positionY+1)*length );
            canvas.drawRect(rectSeed,paintSeed);
        }else{
            Rect rectSnake = new Rect(30+(int)positionX*width, 712+(int)positionY*length,
                    30+(int)(positionX+1)*width, 712+(int)(positionY+1)*length );
            canvas.drawRect(rectSnake,paintSnake);
        }





    }

    @Override
    public void update() {

    }


}
