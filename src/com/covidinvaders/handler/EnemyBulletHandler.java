package com.covidinvaders.handler;

import com.covidinvaders.game_screen.BasicBlocks;
import com.covidinvaders.game_screen.Player;
import com.covidinvaders.enemy_bullet.EnemyWeaponType;
import com.covidinvaders.explosion.ExplosionManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/* Serve per gestire i proiettili dei nemici. Creo la lista delle armi,
* al momento della collisione creo l'esplosione, rimuovo il proiettile e
* decremento la vita del giocatore. */
public class EnemyBulletHandler {

    private List<EnemyWeaponType> weaponTypes =  new ArrayList<>();

    public void addBullet(EnemyWeaponType weaponType){
        this.weaponTypes.add(weaponType);
    }

    public void draw(Graphics2D g){
        for (EnemyWeaponType enemyWeaponType : weaponTypes){
            enemyWeaponType.draw(g);
        }
    }

    public void update(double delta, BasicBlocks blocks, Player player){
        for (int i=0; i<weaponTypes.size(); i++){
            weaponTypes.get(i).update(delta, blocks, player);
            if (weaponTypes.get(i).collision(player.getRect())){
                ExplosionManager.createPixelExplosion(weaponTypes.get(i).getxPos(), weaponTypes.get(i).getyPos());
                weaponTypes.remove(i);
                player.hit();
            }
        }
    }

    public void reset(){
        weaponTypes.clear();
    }

}
