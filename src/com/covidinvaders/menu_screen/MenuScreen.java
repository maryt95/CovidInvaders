package com.covidinvaders.menu_screen;

import com.covidinvaders.display.Display;
import com.covidinvaders.levels.Level1;
import com.covidinvaders.sound.Sound;
import com.covidinvaders.state.StateMachine;
import com.covidinvaders.state.SuperStateMachine;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.net.URL;

public class MenuScreen extends SuperStateMachine implements KeyListener {

    private Font titolo;
    private Font sottoTitolo;

    private String stringaTitolo = "COVID INVADERS";
    private String stringaStart = "PREMI INVIO PER COMINCIARE";

    private Sound openingTheme;

    public MenuScreen(StateMachine stateMachine) {
        super(stateMachine);
        openingTheme = new Sound("/com/covidinvaders/sounds/openingTheme.wav");

        //if (!openingTheme)
        this.start();
    }

    public void start(){
        openingTheme.play();
    }

    public void stop(){
        openingTheme.stop();
    }

    @Override
    public void update(double delta) {
    }

    @Override
    public void draw(Graphics2D g) {

        try {
            titolo = getFont("ARCADECLASSIC.ttf", "titolo");
            sottoTitolo = getFont("Arcadepix Plus.ttf", "sottotitolo");
        } catch (Exception e) {
            e.printStackTrace();
        }

        g.setFont(titolo);
        int titleWidth = g.getFontMetrics().stringWidth(stringaTitolo);
        g.setColor(Color.yellow);
        g.drawString(stringaTitolo, ((Display.WIDTH / 2) - (titleWidth / 2)) - 2, (Display.HEIGHT / 2) - 123);
        g.setColor(Color.green);
        g.drawString(stringaTitolo, (Display.WIDTH / 2) - (titleWidth / 2), (Display.HEIGHT / 2) - 125);

        g.setFont(sottoTitolo);
        g.setColor(Color.white);
        int startWidth = g.getFontMetrics().stringWidth(stringaStart);
        g.drawString(stringaStart, (Display.WIDTH / 2) - (startWidth / 2), (Display.HEIGHT / 2) + 75);
    }

    @Override
    public void init(Canvas canvas) {
        canvas.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            getStateMachine().setState((byte) 1);
            openingTheme.stop();
        }
    }

    public Font getFont(String fileName, String tipoTitolo) throws Exception {
        //System.out.println("fileName: " + fileName);
        String path = "/com/covidinvaders/fonts/" + fileName;
        //System.out.println("path: " + path);
        URL url = getClass().getResource(path);
        Font font = Font.createFont(Font.TRUETYPE_FONT, new File(url.toURI()));

        if (tipoTitolo == "titolo")
            return font.deriveFont(Font.PLAIN, 80);
        else
            return font.deriveFont(Font.PLAIN, 20);

    }
}
