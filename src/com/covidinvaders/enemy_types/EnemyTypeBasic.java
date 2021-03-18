package com.covidinvaders.enemy_types;

import com.covidinvaders.display.Display;
import com.covidinvaders.game_screen.BasicBlocks;
import com.covidinvaders.game_screen.GameScreen;
import com.covidinvaders.game_screen.Player;
import com.covidinvaders.sound.Sound;
import com.covidinvaders.sprite.SpriteAnimation;
import com.covidinvaders.timer.Timer;
import com.covidinvaders.enemy_bullet.EnemyBasicBullet;
import com.covidinvaders.handler.EnemyBulletHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/* Estende la classe astratta EnemyType e l'override dei suoi metodi.
* Serve per definire la figura del nemico che incontrerò nel livello. */

public class EnemyTypeBasic extends EnemyType {

    private double speed = 1.0d;

    private Rectangle rect;
    private SpriteAnimation enemySprite;

    private int shootTime;
    private Timer shootTimer;

    private Sound explosionSound;

    /* Costruttore della classe. Definisco l'immagine che utilizzerò per disegnare il nemico
    * insieme al rettangolo in cui sarà disegnato, al tempo di sparo e al suono che farà al momento
    * della sua esplosione. */
    public EnemyTypeBasic(double xPos, double yPos, int rows, int columns, EnemyBulletHandler bulletHandler) {
        super(bulletHandler);

        enemySprite = new SpriteAnimation(xPos, yPos, rows, columns, 300, "/com/covidinvaders/images/Invaders.png");
        enemySprite.setWidth(25);
        enemySprite.setHeight(25);
        enemySprite.setLimit(2);

        this.setRect(new Rectangle((int) enemySprite.getxPos(), (int) enemySprite.getyPos(), enemySprite.getWidth(), enemySprite.getHeight()));
        enemySprite.setLoop(true);

        shootTimer = new Timer();
        shootTime = new Random().nextInt(12000);

        explosionSound = new Sound("/com/covidinvaders/sounds/explosion.wav");
    }

    @Override
    public void draw(Graphics2D g) {
        enemySprite.draw(g);
    }

    @Override
    /* Aggiorno la posizione del singolo nemico e dello sparo. */
    public void update(double delta, Player player, BasicBlocks blocks) {
        enemySprite.update(delta);

        enemySprite.setxPos(enemySprite.getxPos() - (delta * speed));
        this.getRect().x = (int) enemySprite.getxPos();

        if (shootTimer.timerEvent(shootTime)){
            getBulletHandler().addBullet(new EnemyBasicBullet(getRect().x, getRect().y));
            shootTime = new Random().nextInt(12000);
        }
    }

    @Override
    /* In questo metodo definisco il cambio di direzione dell'array
    * dei nemici insieme alla velocità con cui avviene il cambio di direzione. */
    public void changeDirection(double delta) {
        speed *= -1.05d;
        enemySprite.setxPos(enemySprite.getxPos() - (delta * speed));
        this.getRect().x = (int) enemySprite.getxPos();

        enemySprite.setyPos(enemySprite.getyPos() + (delta * 15));
        this.getRect().y = (int) enemySprite.getyPos();
    }

    @Override
    /* Quando un nemico viene colpito faccio partire il suono dell'esplosione del nemico. */
    public boolean deathScene() {
        if (!enemySprite.isPlay())
            return false;

        if (enemySprite.isSpriteAnimDestroyed()){
            if (!explosionSound.isPlaying()) {
                explosionSound.play();
            }
            return true;
        }

        return false;
    }

    @Override
    /* Quando c'è la collisione rimuovo il nemico dalla lista dei nemici con
    * l'animazione relativa all'esplosione.
    * Se la lista dei nemici non è nulla e il mio proiettile colpisce il rettangolo del nemico
    * allora faccio partire l'esplosione e aggiungo otto punti al mio punteggio. */
    public boolean collide(int i, Player player, BasicBlocks blocks, ArrayList<EnemyType> enemys) {
        if (enemySprite.isPlay()){
            if (enemys.get(i).deathScene()){
                enemys.remove(i);
            }
            return false;
        }

        for (int w = 0; w < player.playerWeapons.weapons.size(); w++) {
            if(enemys != null && player.playerWeapons.weapons.get(w).collisionRect(((EnemyTypeBasic) enemys.get(i)).getRect())) {
                enemySprite.resetLimit();
                enemySprite.setAnimationSpeed(120);
                enemySprite.setPlay(true, true);
                GameScreen.SCORE += 8;
                return true;
            }
        }

        return false;
    }

    @Override
    /* Serve per sapere se sono oltre la larghezza del display */
    public boolean isOutOfBounds() {
        return rect.x <= 0 || rect.x >= Display.WIDTH - rect.width;
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }


}
