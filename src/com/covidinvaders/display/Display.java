package com.covidinvaders.display;

import com.covidinvaders.state.StateMachine;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

/*
* Classe che serve a strutturare la finestra di gioco,
* contiene il main e che rende il gioco eseguibile.
*/

public class Display extends Canvas implements Runnable {

    public static void main(String [] args) {
        Display display = new Display();
        JFrame frame = new JFrame();
        frame.add(display);
        frame.pack();
        frame.setTitle("Space Invaders");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        display.start();
    }

    private boolean running = false;
    private Thread thread;

    public synchronized void start(){
        if (running)
            return;

        running = true;
        thread = new Thread(this); // il target sarebbe la classe che implementa runnable, quindi passo this
        thread.start();
    }

    public synchronized void stop(){
        if (!running)
            return;

        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static int WIDTH = 800;
    public static int HEIGHT = 600;
    public int FPS;

    public static StateMachine state;

    public Display(){
        this.setSize(WIDTH, HEIGHT);
        this.setFocusable(true);

        state = new StateMachine(this);
        state.setState((byte) 0);
    }

    @Override
    /*
    * Override di Runnable. In questo metodo stabilisco quel che succede quando il thread
    * viene fatto partire. Ho necessità di far partire un secondo thread che mi gestisca
    * la parte del render grafico perché sul mainThread sto eseguendo il resto del codice.
    * Stabilisco quindi un numero di FPS e i frame da renderizzare durante l'esecuzione
    * e metto il thread a dormire ogni volta che questo processo viene ultimato.
    * Più si alza il numero dei TARGET_FPS e più posso renderizzare in una frazione di secondo.
    *  */
    public void run(){

        long timer = System.currentTimeMillis();
        long lastLoopTime = System.nanoTime();
        final int TARGET_FPS = 60;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
        int frame = 0;

        this.createBufferStrategy(3);
        BufferStrategy bs = this.getBufferStrategy();
        while(running){
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double delta = updateLength / ((double) OPTIMAL_TIME);

            frame++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                FPS = frame;
                frame = 0;
                System.out.println(FPS);
            }

            draw(bs);
            update(delta);

            try {
                /* Mando a dormire il thread ogni volta che finisco di disegnare qualcosa */
                Thread.sleep(((lastLoopTime - System.nanoTime()) + OPTIMAL_TIME) / 1000000);
            } catch(Exception e) {
                //System.out.println("Exception: " + e);
            }

            //System.out.println("Sta girando!");
        }
    }

    /*
    * Mi serve per disegnare il rettangolo di gioco. Gli passo
    * le dimensioni della finestra di gioco con un margine di 50px.
    * BufferStrategy è una classe che estende JFrame e serve per riempire
    * il rettangolo di gioco. */
    public void draw(BufferStrategy bs) {
        do {
            do {
                Graphics2D g = (Graphics2D) bs.getDrawGraphics();
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, WIDTH + 50, HEIGHT + 50);

                state.draw(g);

                g.dispose();
            } while (bs.contentsRestored());
            bs.show();
        } while (bs.contentsLost());
    }

    public void update(double delta){
        state.update(delta);
    }

}
