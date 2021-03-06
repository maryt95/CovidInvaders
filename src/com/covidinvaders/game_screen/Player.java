package com.covidinvaders.game_screen;

import com.covidinvaders.display.Display;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/* Classe che mi serve per definire il mio player in base alla
 * pressione dei tasti (che il mio codice recepisce tramite il keylistener) */

public class Player implements KeyListener {

    private final double speed = 5.0d;
    private int health;

    private BufferedImage pSprite;
    private Rectangle rect;
    private double xPos, yPos, startXPos, startYPos;
    private int width;
    private int height;
    private BasicBlocks blocks;

    private boolean left = false;
    private boolean right = false;
    private boolean shoot = false;

    public PlayerWeapons playerWeapons;

    /* Definisco il rettangolo in cui disegnerò il mio player e inserisco l'immagine che assocerò
     * al mio avatar. Definisco quindi anche le armi che utilizzerò. */
    public Player(double xPos, double yPos, int width, int height, BasicBlocks blocks) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.startXPos = xPos;
        this.startYPos = yPos;
        this.width = width;
        this.height = height;
        this.health = 3;

        rect = new Rectangle((int) xPos, (int) yPos + 25, width, height + 25);

        try {
            URL url = this.getClass().getResource("/com/covidinvaders/images/Player.png");
            pSprite = ImageIO.read(url);
        } catch (IOException e) {}

        this.blocks = blocks;
        playerWeapons = new PlayerWeapons();

    }

    /* Si occupa di disegnare il player */
    public void draw(Graphics2D g) {
        g.drawImage(pSprite, (int) xPos, (int) yPos, width, height, null);
        playerWeapons.draw(g);
    }

    /* Si occupa dello spostamento del player, modifica la posizione
    * di un valore delta controllando non esca dal display. Fa l'update anche
    * dell'arma del player e spara nel caso in cui avviene lo sparo. */
    public void update(double delta) {
        if (right && !left && xPos < Display.WIDTH - width) {
            xPos += speed * delta;
            rect.x = (int) xPos;
        } else if (!right && left && xPos > 10) {
            xPos -= speed * delta;
            rect.x = (int) xPos;
        }

        playerWeapons.update(delta, blocks);
        if (shoot) {
            playerWeapons.shootBullet(xPos, yPos, 5, 5);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode(); // serve per prendere il tasto che ho premuto
        if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) { // se freccia destra o D allora vado a destra
            right = true;
        } else if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
            left = true;
        }

        if (key == KeyEvent.VK_SPACE) {
            shoot = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
            right = false;
        } else if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
            left = false;
        }
        if (key == KeyEvent.VK_SPACE) {
            shoot = false;
        }
    }

    /* Se la vita è maggiore di zero la decremento di 1 a ogni colpo del nemico */
    public void hit() {
        if (getHealth() > 0)
            setHealth(getHealth() - 1);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Rectangle getRect() {
        return rect;
    }

    public void reset() {
        health = 3;
        left = false;
        right = false;
        shoot = false;

        xPos = startXPos;
        yPos = startYPos;
        rect.x = (int) xPos;
        rect.y = (int) yPos + 25;
        playerWeapons.reset();
    }
}
