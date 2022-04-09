package com.example.gamesnake.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.core.content.ContextCompat;

import com.example.gamesnake.Game;
import com.example.gamesnake.R;

public class Map{
    private static final int MAP_WIDTH = 2200;
    private static final int MAP_HEIGHT = 1050;
    private static final int MAP_RIGHT_WIDTH = 1500;
    private static final int MAP_RIGHT_HEIGHT = 1000;
    private Bitmap background;

    public Map(Context context){
        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.map);





    }
    public void draw(Canvas canvas){

        Rect dst = new Rect();
        dst.left = 0;
        dst.top = 0;
        dst.right = MAP_WIDTH;
        dst.bottom = MAP_HEIGHT;
        canvas.drawBitmap(background,null,dst,null);

    }
}
