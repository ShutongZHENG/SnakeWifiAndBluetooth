package com.example.gamesnake.object;

import android.graphics.Color;

import java.util.Random;

public class Seed extends Square{
    private Snake snake;
    private static final int color = Color.BLUE;
    private double initPositionX;
    private double initPositionY;
    public Seed(double positionX, double positionY, Snake snake) {
        super(color, positionX, positionY);
        this.snake = snake;
        initPositionX = positionX;
        initPositionY =positionY;
    }



    @Override
    public void update() {
            if ( isColliding(this, snake.squares.get(0))){
                Random random = new Random();
                int randomPositionX = 0;
                int randomPositionY = 0;

                boolean inSnake = true;
                while (inSnake){

                    randomPositionX = random.nextInt(42);
                    randomPositionY = random.nextInt(28);
                    boolean res = false;
                    for (SnakeBlock sb: snake.squares) {
                        if (randomPositionX == sb.positionX && randomPositionY == sb.positionY)
                            res = true;
                    }
                    inSnake = res;

                }
                
                
                this.positionX = randomPositionX;
                this.positionY = randomPositionY;
                SnakeBlock lastSb =  this.snake.squares.get( this.snake.squares.size()-1);
                double lastSbPositionX;
                double lastSbPositionY;
                switch (lastSb.getDirection()){
                    case Up:
                        lastSbPositionX = lastSb.positionX;
                        lastSbPositionY = lastSb.positionY-1;
                        break;
                    case Down:
                        lastSbPositionX = lastSb.positionX;
                        lastSbPositionY = lastSb.positionY+1;
                        break;
                    case Left:
                        lastSbPositionX = lastSb.positionX-1;
                        lastSbPositionY = lastSb.positionY;
                        break;
                    case Right:
                        lastSbPositionX = lastSb.positionX+1;
                        lastSbPositionY = lastSb.positionY;
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + lastSb.getDirection());
                }
                SnakeBlock sb = new SnakeBlock(lastSb.getColor(),lastSbPositionX,lastSbPositionY,lastSb.getDirection());
                this.snake.squares.add(sb);

            }
    }
    public void reSet(){
        positionY = initPositionY;
        positionX = initPositionX;
    }
}
