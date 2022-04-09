package com.example.gamesnake.Util;


public class PositionCompetitor {
    public double positionX;
    public double positionY;
    public PositionCompetitor(){
        super();
    }
    public PositionCompetitor(double positionX, double positionY){
        this.positionX = positionX;
        this.positionY = positionY;
    }
    public double getPositionX(){
        return positionX;
    }
    public double getPositionY(){
        return positionY;
    }

}
