package org.kami.config.element;

public class ConfigPlayer {
    /** Tamaño del jugador en píxeles */
    public static final int TAMANIO_JUGADOR = 70;
    public static final String RUTA_IMAGEN = "images.png";
    /**
     * Constructor privado para evitar que la clase sea instanciada.
     */
    private ConfigPlayer() {
        throw new UnsupportedOperationException("Clase de utilidad, no instanciar.");
    }
}
