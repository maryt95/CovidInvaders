package com.covidinvaders.state;

import com.covidinvaders.game_screen.GameScreen;
import com.covidinvaders.menu_screen.MenuScreen;

import java.awt.*;
import java.util.ArrayList;

/* Classe che si occupa di settare gli stati del gioco,
* ovvero di fare lo switch tra una schermata e l'altra
* (come quella del menù e del gioco). In base allo stato selezionato
* si occupa di prendere il metodo draw e update dello 'stato'
* corrispondente.*/
public class StateMachine {

    private ArrayList<SuperStateMachine> states = new ArrayList<SuperStateMachine>();
    private Canvas canvas;
    private byte selectState = 0;

    public StateMachine(Canvas canvas){
        SuperStateMachine game = new GameScreen(this);
        SuperStateMachine menu = new MenuScreen(this);
        states.add(menu);
        states.add(game);

        this.canvas = canvas;
    }

    public void update(double delta) {
        states.get(selectState).update(delta);
    }

    public void draw(Graphics2D g) {
        states.get(selectState).draw(g);
    }

    public void setState(byte i) {
        for (int r = 0; r < canvas.getKeyListeners().length; r++){
            canvas.removeKeyListener(canvas.getKeyListeners()[r]);
        }
        selectState = i;
        states.get(selectState).init(canvas);
    }

    public byte getStates(){
        return selectState;
    }
}
