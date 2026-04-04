package org.kami.config.element;

import java.awt.*;

public class Player {
    private int posX;
    private int posY;
    private final int tamanio;
    private final Image imagen;

    /** Ancho de la pantalla donde se mueve la bola */
    private final int anchoPantalla;

    /** Alto de la pantalla donde se mueve la bola */
    private final int altoPantalla;


    public Player(int posX, int posY, int tamanio, Image imagen, int anchoPantalla, int altoPantalla) {
        this.posX = posX;
        this.posY = posY;
        this.tamanio = tamanio;
        this.imagen = imagen;
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
    }

    public Image getImagen() {
        return imagen;
    }
    public CharacterStatus getStatus() {
        return new CharacterStatus(posX, posY, tamanio);
    }

}
