package com.example.gamesnake;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.security.spec.ECField;

public class GameLoop extends Thread {
    private static final int gameLevel = 10;
    private boolean isStart = false;
    private SurfaceHolder surfaceHolder;
    private Game game;
    private Canvas canvas = null;

    public GameLoop(Game game, SurfaceHolder surfaceHolder) {
        this.game = game;
        this.surfaceHolder = surfaceHolder;
    }

    public void startLoop() {
        isStart = true;
        start();
    }
    @Override
    public void run(){
        super.run();
        int MAJnb = 0;
        long beginTime ;
        long duree;
        long stopTime;
        beginTime = System.currentTimeMillis();
        while (isStart){
            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    if (MAJnb%gameLevel == 0){
                    game.update();
                    }
                    game.draw(canvas);
                    MAJnb++;

                }
            }catch (IllegalArgumentException | JsonProcessingException e ){
                e.printStackTrace();
            }finally {
                if ( canvas != null){
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }


            duree = System.currentTimeMillis() - beginTime;
            stopTime = (int)(MAJnb*(1000/60.0) - duree);
            if (stopTime >0 ){
                try {
                    sleep(stopTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            while(stopTime < 0 && MAJnb < 59){
                try {

                    if (MAJnb%gameLevel == 0){
                        game.update();

                    }

                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                game.draw(canvas);
                MAJnb++;
                duree = System.currentTimeMillis() - beginTime;
                stopTime = (int)(MAJnb*(1000/60.0) - duree);
            }

            duree = System.currentTimeMillis() - beginTime;
            if (duree >= 1000){
                MAJnb =0;
                beginTime = System.currentTimeMillis();
            }




        }




    }
}
