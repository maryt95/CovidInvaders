package com.covidinvaders.enemy_bullet;

import com.covidinvaders.game_screen.BasicBlocks;
import com.covidinvaders.game_screen.Player;

import java.awt.*;

/* Serve per definire i metodi base che dovrà avere ogni tipo di proiettile. */
public abstract class EnemyWeaponType {

    public abstract void draw(Graphics2D g);
    public abstract void update(double delta, BasicBlocks blocks, Player player);

    public abstract boolean collision(Rectangle rect);
    public abstract boolean destroy();

    protected abstract void wallCollide(BasicBlocks blocks);
    protected abstract void isOutofBounds();

    public abstract int getxPos();
    public abstract int getyPos();


}
