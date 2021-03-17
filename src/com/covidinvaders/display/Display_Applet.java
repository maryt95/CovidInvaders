package com.covidinvaders.display;

import javax.swing.*;
import java.awt.*;

public class Display_Applet extends JApplet {

        private Display display = new Display();

        public void init(){
            setLayout(new BorderLayout());
            add(display);
        }

        public void start(){
            display.start();
        }

        public void stop(){
            display.stop();
        }

}
