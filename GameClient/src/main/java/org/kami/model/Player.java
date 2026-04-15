package org.kami.model;

import lombok.Data;

/**
 * Representa un jugador dentro del juego.
 * Contiene información sobre su posición, tamaño y puntaje.
 */
@Data
public class Player {

    /**
     * Posición horizontal del jugador.
     */
    private int posX;

    /**
     * Posición vertical del jugador.
     */
    private int posY;

    /**
     * Tamaño del jugador.
     */
    private final int tamanio;

    /**
     * Puntaje actual del jugador.
     */
    private int score;

    //private final Image imagen;

    /**
     * Constructor que inicializa la posición y el tamaño del jugador.
     * El puntaje se inicializa en cero.
     * @param posX posición horizontal inicial
     * @param posY posición vertical inicial
     * @param tamanio tamaño del jugador
     */
    public Player(int posX, int posY, int tamanio) {
        this.posX = posX;
        this.posY = posY;
        this.tamanio = tamanio;
        this.score = 0;

    }

}