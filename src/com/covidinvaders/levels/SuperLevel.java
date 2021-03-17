package com.covidinvaders.levels;

import com.covidinvaders.game_screen.BasicBlocks;

import java.awt.*;

public interface SuperLevel {

    void draw(Graphics2D g);
    void update(double delta, BasicBlocks blocks);
    void hasDirectionChange(double delta);
    void changeDirectionAllEnemys(double delta);

    boolean isGameOver();
    boolean isComplete();

    void destroy();
    void reset();


}
