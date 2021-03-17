package com.covidinvaders.levels;

import com.covidinvaders.enemy_types.EnemyType;
import com.covidinvaders.enemy_types.EnemyTypeBasic;
import com.covidinvaders.game_screen.BasicBlocks;
import com.covidinvaders.game_screen.Player;
import com.covidinvaders.handler.EnemyBulletHandler;
import com.covidinvaders.sound.Sound;

import java.awt.*;
import java.util.ArrayList;

/*
* Questa classe mi serve per definire i metodi del primo livello.
* Ho un'override di metodi implementati dall'interfaccia
* SuperLevel, che stabilisce la struttura di nuovi eventuali livelli.
* Runnable è l'interfaccia di cui ho bisogno per far partire il thread che
* si occuperà di far riprodurre la musica del livello.
*/

public class Level1 implements SuperLevel, Runnable {

    private Player player;
    private ArrayList<EnemyType> enemies = new ArrayList<EnemyType>();
    private EnemyBulletHandler bulletHandler;

    private Sound beep, boop, finalRound;
    private boolean beepboop;

    // variabili per il Thread
    private Thread threadMusica;
    private boolean running = false;

    /*
    * Costruttore della classe.
    * All'interno del costruttore oltre al player, ai nemici
    * e le loro armi, definisco anche i tre tipi di suono
    * che riprodurrò durante l'esecuzione del livello.
    */
    public Level1(Player player, EnemyBulletHandler bulletHandler) {
        this.player = player;
        this.bulletHandler = bulletHandler;
        addEnemies();

        finalRound = new Sound("/com/covidinvaders/sounds/finalRound.wav");
        beep = new Sound("/com/covidinvaders/sounds/beep.wav");
        boop = new Sound("/com/covidinvaders/sounds/boop.wav");
    }

    @Override
    /*
    * Metodo che serve a disegnare i nemici in base alla grandezza
    * dell'array enemies. Se l'array non esiste non disegno nemici.
    * Richiamo anche la classe draw di bulletHandler
    * che ha lo stesso funzionamento.
    */
    public void draw(Graphics2D g) {
        if (enemies == null) return;
        for (int i = 0; i < enemies.size(); i++) enemies.get(i).draw(g);

        bulletHandler.draw(g);
    }

    @Override
    /*
    * Metodo che serve per fare l'update di quel che ho sullo schermo
    * come ad esempio il giocatore, le barriere e la lista dei nemici
    * al momento della collisione coi proiettili.
    * Faccio partire il thread che si occuperà di gestire la musica
    * del livello.
    */
    public void update(double delta, BasicBlocks blocks) {
        if (enemies == null) return;

        for (int i = 0; i < enemies.size(); i++) enemies.get(i).update(delta, player, blocks);
        for (int i = 0; i < enemies.size(); i++) enemies.get(i).collide(i, player, blocks, enemies);

        hasDirectionChange(delta);
        bulletHandler.update(delta, blocks, player);
        this.start();
    }

    @Override
    /*
    * Metodo richiamato nell'update. Se raggiungo il lato della finestra
    * di gioco allora cambio la posizione a tutti i nemici di una
    * posizione delta.
    */
    public void hasDirectionChange(double delta) {
        if (enemies == null) return;
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).isOutOfBounds()) {
                changeDirectionAllEnemys(delta);
            }
        }
    }

    @Override
    /*
     * Metodo che serve per a cambiare la posizione a tutti i nemici
     * di una posizione delta. In base alla collisione
     * riproduco il suono.
     */
    public void changeDirectionAllEnemys(double delta) {
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).changeDirection(delta);
        }

        if (beepboop){
            beepboop = false;
            boop.play();
        } else {
            beepboop = true;
            beep.play();
        }
    }

    @Override
    public boolean isGameOver() {
        return player.getHealth() <= 0;
    }

    @Override
    /* Svuoto la lista dei nemici al livello completo */
    public boolean isComplete() {
        return enemies.isEmpty();
    }

    @Override
    public void destroy() {}

    @Override
    /* Resetto tutti i dati di gioco */
    public void reset() {
        player.reset();
        enemies.clear();
        finalRound.stop();
        addEnemies();

        bulletHandler.reset();
    }

    /*
    * Aggiungo elementi all'array dei nemici del tipo EnemyTypeBasic
    * */
    public void addEnemies(){
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 10; x++) {
                EnemyType e = new EnemyTypeBasic(150 + (x * 40), 25 + (y * 40), 1, 3, bulletHandler);
                enemies.add(e);
            }
        }
    }

    public synchronized void start(){
        if (running)
            return;

        running = true;
        threadMusica = new Thread(this); // il target sarebbe la classe che implementa runnable, quindi passo this
        threadMusica.start();
    }

    public synchronized void stop(){
        if (!running) {
            finalRound.stop();
            return;
        }

        running = false;
        try {
            threadMusica.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /* Metodo in override dalla classe Runnable,
    * mi serve per far girare il thread che si occuperà
    * di far girare la musica fino a fine livello. */
    @Override
    public void run() {
        while(running){
            finalRound.play();
        }
    }
}
