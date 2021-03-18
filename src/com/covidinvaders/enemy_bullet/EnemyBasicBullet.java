package com.covidinvaders.enemy_bullet;

import com.covidinvaders.display.Display;
import com.covidinvaders.game_screen.BasicBlocks;
import com.covidinvaders.game_screen.Player;

import java.awt.*;

/*
* Serve per disegnare i proiettili dei nemici, estende la classe astratta
* EnemyWeaponType che definisce i metodi base che i proiettili devono
* implementare
*/
public class EnemyBasicBullet extends EnemyWeaponType {

    private Rectangle bullet;
    private double speed = 2.5d;
    private int xPos;
    private int yPos;

    public EnemyBasicBullet(double xPos, double yPos) {
        bullet = new Rectangle((int) xPos, (int) yPos, 5, 5);
        setxPos((int) xPos);
        setyPos((int) yPos);
    }

    @Override
    /* Mi serve per disegnare i proiettili. Se non esistono
    * proiettili esco dal metodo. */
    public void draw(Graphics2D g) {
        if (bullet == null) {
            return;
        }

        g.setColor(Color.RED);
        g.fill(bullet);
    }

    @Override
    /* Serve per fare l'update della posizione delta dei proiettili e
    * delle barriere quando vengono colpite. Aggiorno la posizione y
    * aggiungendo alla posizione iniziale lo spostamento delta per la velocità. */
    public void update(double delta, BasicBlocks blocks, Player player) {
        if (bullet == null) {
            return;
        }

        setyPos((int) (getyPos() + (delta * speed)));
        bullet.y = getyPos();
        isOutofBounds();
        wallCollide(blocks);
    }

    @Override
    /* Se il proiettile esiste e se si interseca
    * con la barriera allora la collisione è true. */
    public boolean collision(Rectangle rect) {

        if (bullet != null && bullet.intersects(rect)){
            return true;
        }

        return false;
    }

    @Override
    public boolean destroy() {
        return false;
    }

    @Override
    /* Se il proiettile nemico interseca la barriera allora rimuovo
    * il 'mattoncino' w dalla barriera ed elimino il proiettile. */
    protected void wallCollide(BasicBlocks barriere) {
        if (bullet == null)
            return;

        for (int w = 0; w < barriere.barriera.size(); w++){
            if (bullet.intersects(barriere.barriera.get(w))){
                barriere.barriera.remove(w);
                bullet = null;
                break;
            }
        }
    }

    @Override
    /* Se il proiettile va oltre le dimensioni del display
    * allora setto a null il proiettile */
    protected void isOutofBounds() {
        if (bullet != null && bullet.y < 0 || bullet.y > Display.HEIGHT || bullet.x < 0 || bullet.x > Display.WIDTH) bullet = null;
    }

    public Rectangle getBullet() {
        return bullet;
    }

    public void setBullet(Rectangle bullet) {
        this.bullet = bullet;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }
}
