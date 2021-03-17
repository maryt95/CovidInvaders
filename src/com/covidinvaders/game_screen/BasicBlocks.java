package com.covidinvaders.game_screen;

import java.awt.*;
import java.util.ArrayList;

public class BasicBlocks {

    public ArrayList<Rectangle> barriera = new ArrayList<Rectangle>();

    public BasicBlocks(){
        basicBlocks(75, 450);
        basicBlocks(275, 450);
        basicBlocks(475, 450);
        basicBlocks(675, 450);
    }

    public void draw(Graphics2D g){
        g.setColor(Color.GREEN);
        for (int i = 0; i< barriera.size(); i++){
            g.fill(barriera.get(i));
        }
    }

    public void basicBlocks(int xPos, int yPos) {
        int larghezzaMuro = 3;
        int x = 0;
        int y = 0;

        for (int i = 0; i < 13; i++) {
            if ((14 + (i * 2) + larghezzaMuro < 22 + larghezzaMuro)) {
                row(14 + (i*2) + larghezzaMuro, xPos - (i*3), yPos + (i*3));
                x = (i*3) + 3;
            } else {
                row(22 + larghezzaMuro, xPos-x, yPos + (i*3));
                y = (i*3);
            }
        }

        // lato sinistro
        for (int i=0; i<5; i++){
            row(8 + larghezzaMuro - i, xPos - x, (yPos+y)+(i*3));
        }

        // lato destro
        for (int i=0; i<5; i++){
            row(8 + larghezzaMuro - i, (xPos - x + (14*3)) + (i*3), (yPos+y)+(i*3));
        }

    }

    public void row(int rows, int xPos, int yPos){
        for (int i = 0; i < rows; i++){
            Rectangle mattoncino = new Rectangle(xPos + (i*3), yPos, 3, 3);
            this.barriera.add(mattoncino);
        }
    }

    public void reset(){
        barriera.clear();

        basicBlocks(75, 450);
        basicBlocks(275, 450);
        basicBlocks(475, 450);
        basicBlocks(675, 450);
    }

}


