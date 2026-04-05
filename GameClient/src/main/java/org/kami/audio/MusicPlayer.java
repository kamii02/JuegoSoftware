package org.kami.audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.io.InputStream;

/**
 * Implementación de {@link IMusicPlayer} que reproduce un archivo .wav
 * en bucle continuo usando {@code javax.sound.sampled.Clip}.
 *
 * <p>
 * La ruta del archivo se obtiene del classpath (resources/), de la misma
 * forma en que {@link org.kami.config.PropertiesManager} carga el
 * {@code application.properties}: mediante
 * {@code getClass().getClassLoader().getResourceAsStream(...)}.
 * </p>
 *
 * <p>
 * El constructor recibe la ruta relativa al directorio {@code resources/}.
 * Ejemplo: si el archivo está en {@code resources/sounds/background.wav},
 * la ruta es {@code "sounds/background.wav"}.
 * </p>
 *
 * @author  [tu nombre]
 * @version 1.0
 * @since   1.0
 */
public class MusicPlayer implements IMusicPlayer {

    /**
     * Clip de audio de Java Sound. Es la abstracción de más alto nivel
     * para reproducir sonidos cortos/medianos en bucle.
     * Se declara como campo para poder detenerlo desde stop().
     */
    private Clip clip;

    /**
     * Ruta relativa al classpath (resources/) del archivo .wav.
     * Se lee desde application.properties con la clave "music.path".
     */
    private final String musicPath;

    /**
     * Construye el reproductor cargando el archivo de audio desde el classpath.
     *
     * @param musicPath ruta relativa a resources/ del archivo .wav.
     *                  Ejemplo: {@code "sounds/background.wav"}
     * @throws RuntimeException si el archivo no existe o no puede leerse.
     */
    public MusicPlayer(String musicPath) {
        this.musicPath = musicPath;

        // Cargamos el stream del .wav desde resources/ del classpath,
        // igual que PropertiesManager carga application.properties.
        try (InputStream raw = getClass().getClassLoader()
                .getResourceAsStream(musicPath)) {

            if (raw == null) {
                throw new RuntimeException(
                        "MusicPlayer: no se encontró el archivo de audio en resources/"
                                + musicPath + ". Verifica la ruta en application.properties.");
            }

            // BufferedInputStream es necesario porque AudioSystem.getAudioInputStream()
            // requiere que el stream soporte mark/reset, y el stream crudo no lo hace.
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(
                    new BufferedInputStream(raw));

            // Clip es el objeto de reproducción. Se obtiene del sistema de audio.
            clip = AudioSystem.getClip();

            // Carga todos los bytes del audio en memoria.
            clip.open(audioIn);

        } catch (Exception e) {
            throw new RuntimeException(
                    "MusicPlayer: error al cargar el audio '" + musicPath + "': "
                            + e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     *
     * Inicia la reproducción desde el principio en bucle infinito.
     * {@code Clip.LOOP_CONTINUOUSLY} es una constante de Java Sound
     * que equivale a Integer.MAX_VALUE repeticiones.
     */
    @Override
    public void play() {
        if (clip != null) {
            clip.setFramePosition(0);            // Rebobina al inicio
            clip.loop(Clip.LOOP_CONTINUOUSLY);   // Reproduce en bucle infinito
        }
    }

    /**
     * {@inheritDoc}
     *
     * Detiene la reproducción y libera el recurso de audio del sistema.
     * Importante llamarlo al cerrar la ventana para evitar memory leaks.
     */
    @Override
    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();  // Libera los recursos de audio del sistema operativo
        }
    }
}