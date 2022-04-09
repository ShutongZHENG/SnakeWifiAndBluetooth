package com.example.gamesnake.button;

import com.example.gamesnake.Game;
import com.example.gamesnake.object.Snake;

public class ButtonReady extends ButtonBase {
    private Game game;
    public ButtonReady(double positionX, double positionY , Game game) {
        super(positionX, positionY, "Ready");
        this.game =game;
    }

    @Override
    public void update() {

    }

    @Override
    public void onClick() {
        game.isReady = true;
        game.isGameOver = false;
    }
}
