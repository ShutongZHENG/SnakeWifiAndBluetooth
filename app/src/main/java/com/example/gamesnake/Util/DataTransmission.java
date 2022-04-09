package com.example.gamesnake.Util;

import android.service.autofill.Sanitizer;

import com.example.gamesnake.object.Seed;
import com.example.gamesnake.object.Snake;
import com.example.gamesnake.object.SnakeBlock;

import java.util.ArrayList;

public class DataTransmission {
    public ArrayList<PositionCompetitor> pcs;

    public DataTransmission(){
        pcs = new ArrayList<>();
    }

    public ArrayList<PositionCompetitor> collectPositions(Snake snake, Seed seed){
        ArrayList<PositionCompetitor> res = new ArrayList<>();
        res.add(new PositionCompetitor(seed.getPositionX(), seed.getPositionY()));
        for (int i = 0 ; i< snake.squares.size(); i++){
            res.add(new PositionCompetitor(snake.squares.get(i).getPositionX(),
                    snake.squares.get(i).getPositionY()));
        }
        pcs = res;
        return res;
    }



}
