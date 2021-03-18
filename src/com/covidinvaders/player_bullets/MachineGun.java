package com.covidinvaders.player_bullets;

import com.covidinvaders.display.Display;
import com.covidinvaders.game_screen.BasicBlocks;

import java.awt.*;

public class MachineGun extends PlayerWeaponType{

    private  Rectangle bullet;
    private final double speed = 2.5d;

    public MachineGun(double xPos, double yPos, int width, int height){
        this.setxPos(xPos);
        this.setyPos(yPos);
        this.setHeight(height);
        this.setWidth(width);

        this.bullet = new Rectangle((int) getxPos(), (int) getyPos(), getWidth(), getHeight());
    }


    @Override
    public void draw(Graphics2D g) {
        if (bullet == null) return;

        g.setColor(Color.GREEN);
        g.fill(bullet);
    }

    @Override
    public void update(double delta, BasicBlocks blocks) {
        if (bullet == null) return;

        this.setyPos(getyPos() - (delta*speed));
        bullet.y = (int) this.getyPos();
        wallCollide(blocks);
        isOutofBounds();
    }

    @Override
    public boolean collisionRect(Rectangle rect) {
        if (this.bullet == null) return false;
        if (this.bullet.intersects(rect)) {
            this.bullet = null;
            return true;
        }

        return false;
    }

    @Override
    public boolean collisionPoly(Polygon poly) {
        return false;
    }

    @Override
    public boolean destroy() {
        if (bullet == null) return true;

        return false;
    }

    @Override
    protected void wallCollide(BasicBlocks blocks) {
        for (int i = 0; i < blocks.barriere.size(); i++){
            if (bullet.intersects(blocks.barriere.get(i))){
                blocks.barriere.remove(i);
                bullet = null;
                return;
            }
        }

    }

    @Override
    protected void isOutofBounds() {
        if (this.bullet == null) return;

        if (bullet.y < 0 || bullet.y > Display.HEIGHT || bullet.x < 0 || bullet.x > Display.WIDTH) bullet = null;
    }
}
