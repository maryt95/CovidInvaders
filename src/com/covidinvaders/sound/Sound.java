package com.covidinvaders.sound;

import javax.sound.sampled.*;
import java.net.URL;

/* Classe che mi serve per gestire le musiche e i suoni presenti all'interno del gioco.
* Si occupa di importare il suono, farlo partire, mandarlo in loop o eventualmente
* farlo fermare. */
public class Sound implements LineListener {

    private Clip soundClip;

    public Sound(String path){

        try {

            URL url = getClass().getResource(path);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            AudioFormat format = audioInputStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            soundClip = (Clip) AudioSystem.getLine(info);
            soundClip.open(audioInputStream);
            soundClip.addLineListener(this);


        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void update(LineEvent event) {
        if (event.getType().equals(LineEvent.Type.STOP)){
            soundClip.setFramePosition(1);
        }
    }

    public void play(){
        soundClip.start();
    }

    public void loop(){
        soundClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(){
        soundClip.stop();
        soundClip.setFramePosition(1);
    }

    public boolean isPlaying(){
        return soundClip.isRunning();
    }

}
