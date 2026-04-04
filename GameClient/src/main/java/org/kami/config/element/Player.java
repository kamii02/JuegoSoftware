package org.kami.config.element;

import lombok.Data;

import java.awt.*;

@Data
public class Player {
    private int posX;
    private int posY;
    private final int tamanio;
    //private final Image imagen;


    public Player(int posX, int posY, int tamanio) {
        this.posX = posX;
        this.posY = posY;
        this.tamanio = tamanio;

    }


}
