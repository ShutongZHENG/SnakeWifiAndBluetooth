package com.example.gamesnake.object;

import android.graphics.Canvas;

import com.example.gamesnake.Util.PositionCompetitor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Competitor {
    public ArrayList<CompetitorBlock> competitorBlocks;
    public Competitor(){
        competitorBlocks = new ArrayList<>();
    }

    public void draw(Canvas canvas){
        for (CompetitorBlock cb: competitorBlocks) {
            cb.draw(canvas);
        }
    }

    public void update(String JsonDataReceive){
        if (JsonDataReceive != null) {
            List<PositionCompetitor> pcs = new ArrayList<PositionCompetitor>();
            try {
                ObjectMapper mapper = new ObjectMapper();
                pcs = mapper.readValue(JsonDataReceive, new TypeReference<List<PositionCompetitor>>(){});
            } catch (JsonProcessingException e) {
                System.out.println("Error: this phone can convert jsonData to Arraylist");
            }


            competitorBlocks.clear();
            CompetitorBlock competitorBlock;
            for (int i = 0; i < pcs.size(); i++) {
                if (i == 0) {
                    competitorBlock = new CompetitorBlock(pcs.get(i).positionX, pcs.get(i).positionY, true);
                } else {
                    competitorBlock = new CompetitorBlock(pcs.get(i).positionX, pcs.get(i).positionY, false);
                }
                competitorBlocks.add(competitorBlock);
            }
        }

    }



}
