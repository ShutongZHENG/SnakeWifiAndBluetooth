package com.example.gamesnake.object;

import android.graphics.Canvas;

import com.example.gamesnake.Util.Direction;

import java.util.ArrayList;

public class Snake {
    public ArrayList<SnakeBlock> squares;
    public boolean snakeColliding;
    private double initPositionX;
    private double initPositionY;
    private int initColor;

    public Snake(double positionX, double positionY, int color) {
        squares = new ArrayList<>();
        squares.add(new SnakeBlock(color, positionX, positionY, Direction.Right));
        squares.add(new SnakeBlock(color, positionX - 1, positionY, Direction.Right));
        snakeColliding = false;
        initColor = color;
        initPositionY =positionY;
        initPositionX = positionX;
    }

    public void update() {
        double beforePx = squares.get(0).positionX;
        double beforePy = squares.get(0).positionY;
        for (int i = 0; i < squares.size(); i++) {
            if (i == 0) {
                switch (squares.get(i).getDirection()) {
                    case Up:
                        squares.get(i).positionY -= 1;
                        if (squares.get(i).positionY < 0)
                            squares.get(i).positionY = 28;
                        break;
                    case Right:
                        squares.get(i).positionX += 1;
                        if (squares.get(i).positionX > 43)
                            squares.get(i).positionX = 0;
                        break;
                    case Left:
                        squares.get(i).positionX -= 1;
                        if (squares.get(i).positionX < 0)
                            squares.get(i).positionX = 42;
                        break;
                    case Down:
                        squares.get(i).positionY += 1;
                        if (squares.get(i).positionY > 29)
                            squares.get(i).positionY = 0;
                        break;
                }

            }
            if (i > 0) {

                double tmpPx = squares.get(i).positionX;
                double tmpPy = squares.get(i).positionY;
                squares.get(i).positionX = beforePx;
                squares.get(i).positionY = beforePy;
                beforePx = tmpPx;
                beforePy = tmpPy;
            }


        }

        snakeColliding = isColliding();


    }

    public void draw(Canvas canvas) {
        for (SnakeBlock sb : squares) {

            sb.draw(canvas);
        }
    }

    public boolean isColliding() {
        for (int i = 1; i < squares.size(); i++) {
            if (squares.get(0).positionX == squares.get(i).positionX
                    && squares.get(0).positionY == squares.get(i).positionY)
                return true;
        }
        return false;


    }

    public void reSet(){
        squares.clear();
        snakeColliding =false;
        squares.add(new SnakeBlock(initColor, initPositionX, initPositionY, Direction.Right));
        squares.add(new SnakeBlock(initColor, initPositionX - 1, initPositionY, Direction.Right));
    }

}
