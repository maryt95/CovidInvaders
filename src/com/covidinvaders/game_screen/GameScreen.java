package com.covidinvaders.game_screen;

import com.covidinvaders.display.Display;
import com.covidinvaders.levels.Level1;
import com.covidinvaders.menu_screen.MenuScreen;
import com.covidinvaders.state.StateMachine;
import com.covidinvaders.state.SuperStateMachine;
import com.covidinvaders.timer.TickTimer;
import com.covidinvaders.handler.EnemyBulletHandler;

import java.awt.*;
import java.awt.event.KeyEvent;

/* Serve per definire la schermata di gioco con le righe dei punteggi e della vita.
* Inserisco nel GameScreen tutti gli elementi definiti nel costruttore,
* ovvero le barriere, i proiettili, il giocatore, il settaggio del livello di gioco
* e la schermata iniziale.*/
public class GameScreen extends SuperStateMachine {

    private Player player;
    private BasicBlocks blocks;
    private Level1 level;
    private EnemyBulletHandler bulletHandler;
    private MenuScreen menuScreen;

    public static int SCORE = 0;

    private Font gameScreen = new Font("Arial", Font.PLAIN, 48);
    private TickTimer gameOverTimer = new TickTimer(180);
    private TickTimer completeTimer = new TickTimer(180);


    public GameScreen(StateMachine stateMachine){
        super(stateMachine);
        blocks = new BasicBlocks();
        bulletHandler = new EnemyBulletHandler();
        player = new Player(375, 500, 50, 50, blocks);
        level  = new Level1(player, bulletHandler);
        menuScreen = new MenuScreen(stateMachine);
    }

    @Override
    /* Aggiorno il player e il livello aggiungendo le barriere.
    * A questo punto faccio partire il primo livello e interrompo
    * la schermata del menu.
    * Se sono andato in gameover faccio un reset del livello, mostro nuovamente
    * il menù principale e faccio ripartire la musica iniziale.
    * Se il livello invece è completo ricomincio semplicemente il livello da capo.*/
    public void update(double delta) {
        player.update(delta);
        level.update(delta, blocks);

        level.start();
        menuScreen.stop();

        if (level.isGameOver()){
            gameOverTimer.tick(delta);
            if (gameOverTimer.isEventReady()){
                level.stop();
                level.reset();
                blocks.reset();
                getStateMachine().setState((byte) 0);
                SCORE = 0;
                menuScreen.start();
            }
        }

        if (level.isComplete()) {
            completeTimer.tick(delta);
            if (completeTimer.isEventReady()){
                level.reset();
            }
        }
    }

    @Override
    /* Mi serve per disegnare la stringa della vita e del punteggio.
    * Disegno i blocchi, il player e poi il livello.
    * In questo metodo disegno la scritta del game over o del livello completo
    * al centro del display. */
    public void draw(Graphics2D g) {
        g.setColor(Color.white);
        g.drawString("Score: " + SCORE , 5, 15);

        g.setColor(Color.red);
        g.drawString("Health: " + player.getHealth() , 5, 35);

        blocks.draw(g);
        player.draw(g);
        level.draw(g);

        if (level.isGameOver()){
            g.setColor(Color.red);
            g.setFont(gameScreen);
            String gameOver = "GAME OVER!";
            int gameOverWidth = g.getFontMetrics().stringWidth(gameOver);
            g.drawString(gameOver, (Display.WIDTH/2)-(gameOverWidth/2), Display.HEIGHT/2);
        }

        if (level.isComplete()){
            g.setColor(Color.green);
            g.setFont(gameScreen);
            String complete = "YOU WIN!";
            int completeWidth = g.getFontMetrics().stringWidth(complete);
            g.drawString(complete, (Display.WIDTH/2)-(completeWidth/2), Display.HEIGHT/2);
        }
    }

    @Override
    /* Il keylistener serve per far funzionare il gioco alla pressione dei tasti della tastiera. */
    public void init(Canvas canvas) {
        canvas.addKeyListener(player);
    }
}
