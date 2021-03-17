package com.covidinvaders.enemy_types;

import com.covidinvaders.game_screen.BasicBlocks;
import com.covidinvaders.game_screen.Player;
import com.covidinvaders.handler.EnemyBulletHandler;

import java.awt.*;
import java.util.ArrayList;

public abstract class EnemyType  {

    private EnemyBulletHandler bulletHandler;

    public EnemyType(EnemyBulletHandler bulletHandler){
        this.bulletHandler = bulletHandler;
    }

    public abstract void draw(Graphics2D g);
    public abstract void update(double delta, Player player, BasicBlocks blocks);
    public abstract void changeDirection(double delta);

    public abstract boolean deathScene();
    public abstract boolean collide(int i, Player player, BasicBlocks blocks, ArrayList<EnemyType> enemys);
    public abstract boolean isOutOfBounds();

    public EnemyBulletHandler getBulletHandler(){
        return bulletHandler;
    }

}
