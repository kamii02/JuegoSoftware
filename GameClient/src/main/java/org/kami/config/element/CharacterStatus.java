package org.kami.config.element;

public final class CharacterStatus {
    /** Posición horizontal actual de la bola */
    private final int posX;

    /** Posición vertical actual de la bola */
    private final int posY;

    /** Tamaño (diámetro) de la bola en píxeles */
    private final int tamanio;

    /**
     * Crea un nuevo objeto que representa el estado actual de la bola.
     *
     * @param posX posición horizontal de la bola
     * @param posY posición vertical de la bola
     * @param tamanio tamaño de la bola en píxeles
     */
    public CharacterStatus(int posX, int posY, int tamanio) {
        this.posX    = posX;
        this.posY    = posY;
        this.tamanio = tamanio;
    }

    /**
     * Obtiene la posición horizontal de la bola.
     *
     * @return posición en el eje X
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Obtiene la posición vertical de la bola.
     *
     * @return posición en el eje Y
     */
    public int getPosY() {
        return posY;
    }

    /**
     * Obtiene el tamaño de la bola.
     *
     * @return tamaño (ancho y alto) en píxeles
     */
    public int getTamanio() {
        return tamanio;
    }
}
