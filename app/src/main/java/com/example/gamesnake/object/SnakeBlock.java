package com.example.gamesnake.object;

import com.example.gamesnake.Util.Direction;

public class SnakeBlock extends Square{
    private Direction direction;
    public SnakeBlock(int color, double positionX, double positionY, Direction direction) {
        super(color, positionX, positionY);
        this.direction = direction;
    }

    @Override
    public void update() {

    }
    public Direction getDirection(){
        return this.direction;
    }
    public void setDirection(Direction direction){
        this.direction = direction;
    }
}
