package org.kami.audio;

/**
 * Contrato para el reproductor de música global del juego.
 *
 * <p>
 * Sigue el mismo patrón de interfaces del proyecto (IConfigReader,
 * INetworkConnection, ILayoutConfig…): se programa contra la interfaz,
 * no contra la implementación concreta.
 * </p>
 *
 * <p>
 * La implementación concreta es {@link MusicPlayer}, que usa
 * {@code javax.sound.sampled.Clip} — API incluida en el JDK,
 * sin dependencias externas.
 * </p>
 *
 * @author  [tu nombre]
 * @version 1.0
 * @since   1.0
 */
public interface IMusicPlayer {

    /**
     * Inicia la reproducción de la música en bucle infinito.
     * Debe llamarse una sola vez al arrancar el juego.
     */
    void play();

    /**
     * Detiene la reproducción y libera los recursos de audio.
     * Debe llamarse cuando el juego cierre (WindowListener o shutdown hook).
     */
    void stop();
}