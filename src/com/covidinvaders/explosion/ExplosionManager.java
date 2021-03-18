package com.covidinvaders.explosion;

import java.awt.*;
import java.util.ArrayList;

public class ExplosionManager {

    private static ArrayList<ExplosionType> explosions = new ArrayList<ExplosionType>();

    public ExplosionManager(){

    }

    /* Serve per disegnare l'array delle esplosioni */
    public void draw (Graphics2D g){
        for (int i = 0; i < explosions.size(); i++){
            explosions.get(i).draw(g);
        }
    }

    /* Serve per aggiornare l'array delle esplosioni */
    public void update(double delta){
        for (int i = 0; i < explosions.size(); i++){
            explosions.get(i).update(delta);
            if (explosions.get(i).destroy()){
                explosions.remove(i);
            }
        }
    }

    /* Serve per creare l'esplosione dei pixel */
    public static void createPixelExplosion(double xPos, double yPos){
        ExplosionType et = new PixelExplosion(xPos, yPos);
        explosions.add(et);
    }



}
