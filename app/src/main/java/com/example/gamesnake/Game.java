package com.example.gamesnake;



import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.gamesnake.Util.Direction;
import com.example.gamesnake.button.ButtonReady;
import com.example.gamesnake.map.Map;
import com.example.gamesnake.object.Competitor;
import com.example.gamesnake.object.Seed;
import com.example.gamesnake.object.Snake;
import com.fasterxml.jackson.core.JsonProcessingException;


public class Game extends SurfaceView implements SurfaceHolder.Callback {
    public boolean isReady;
    private Context context;
    private Map map;
    private GameLoop gameLoop;
    public Snake snake;
    public Seed seed;
    private double x1, x2, y1, y2;
    private static double MINIDISTANCE = 150.0;
    private ButtonReady btReady;
    public boolean isGameOver;
    private GameOver gameOver;


    public Competitor competitor;

    public Game(Context context ) {
        super(context);
        isReady = false;
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        this.context = context;
        gameLoop = new GameLoop(this, surfaceHolder);
        map = new Map(this.context);

        snake = new Snake(21, 15, Color.RED);
        seed = new Seed(10, 10, snake);
        btReady = new ButtonReady(125,300,this);
        competitor = new Competitor();
        isGameOver = false;
        gameOver = new GameOver(snake);


        setFocusable(true);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                return true;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();
                double deltaX = x2 - x1;
                double deltaY = y2 - y1;
                if (Math.abs(deltaX) > MINIDISTANCE && isReady) {
                    if (x2 > x1) {
                        //Right
                        if (snake.squares.get(0).getDirection() != Direction.Left)
                            snake.squares.get(0).setDirection(Direction.Right);
                    } else {

                        if (snake.squares.get(0).getDirection() != Direction.Right)
                            snake.squares.get(0).setDirection(Direction.Left);

                    }
                } else if (Math.abs(deltaY) > MINIDISTANCE && isReady) {
                    if (y2 > y1) {
                        if (snake.squares.get(0).getDirection() != Direction.Up)
                            snake.squares.get(0).setDirection(Direction.Down);
                    } else {
                        if (snake.squares.get(0).getDirection() != Direction.Down)
                            snake.squares.get(0).setDirection(Direction.Up);
                    }
                }else{
                    if (x1<425 && x1>125 && x2<425 && x2>125 && y1>300 && y1<400 && y2>300 && y2<400 && isReady == false){
                        btReady.onClick();
                        seed.reSet();
                        snake.reSet();
                    }


                }
                return true;
        }

        return super.onTouchEvent(event);
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        map.draw(canvas);

        if (!isReady && isGameOver == false) {
            btReady.draw(canvas);
            seed.draw(canvas);
            snake.draw(canvas);

        }
        else if(isGameOver  && !isReady){
            btReady.draw(canvas);
                gameOver.draw(canvas);
        }
        else if (isGameOver == false && isReady){
            seed.draw(canvas);
            snake.draw(canvas);
        }
        competitor.draw(canvas);


    }

    public void update() throws JsonProcessingException {

        if (isReady && isGameOver == false){
            if(!snake.snakeColliding){
                seed.update();
                snake.update();
            }else{
                this.isReady= false;
                isGameOver = true;
            }

        }

    }

    public Context getContextGame(){
        return this.context;
    }

}
