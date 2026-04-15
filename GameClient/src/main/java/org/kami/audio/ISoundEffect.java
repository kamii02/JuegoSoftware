package org.kami.audio;

/**
 * Contrato para efectos de sonido puntuales del juego.
 *
 * <p>
 * A diferencia de {@link IMusicPlayer} (que reproduce en bucle continuo),
 * esta interfaz modela sonidos de un solo disparo: se reproducen una vez
 * de principio a fin cada vez que ocurre un evento (moneda, colisión, etc.).
 * </p>
 *
 * <p>
 * Sigue el mismo patrón de interfaces del proyecto: se programa contra
 * la abstracción, nunca contra la implementación concreta.
 * </p>
 *
 * @author  [tu nombre]
 * @version 1.0
 * @since   1.0
 */
public interface ISoundEffect {

    /**
     * Reproduce el efecto de sonido una sola vez desde el inicio.
     * Si el sonido ya estaba reproduciéndose, lo reinicia desde el principio.
     * Debe llamarse cada vez que ocurra el evento correspondiente.
     */
    void play();
}