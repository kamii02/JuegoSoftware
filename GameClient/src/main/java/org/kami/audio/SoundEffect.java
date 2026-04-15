package org.kami.audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.io.InputStream;

/**
 * Implementación de {@link ISoundEffect} para efectos de sonido puntuales.
 *
 * <p>
 * Usa {@code javax.sound.sampled.Clip} (API nativa del JDK, sin dependencias
 * externas), igual que {@link MusicPlayer}. La diferencia clave es que este
 * reproductor NO hace bucle: cada llamada a {@link #play()} rebobina el clip
 * al inicio y lo reproduce una vez.
 * </p>
 *
 * <p>
 * El audio se carga en memoria en el constructor (desde el classpath,
 * igual que {@code PropertiesManager} carga {@code application.properties}),
 * para que las reproducciones posteriores sean instantáneas sin I/O.
 * </p>
 *
 * @author  [tu nombre]
 * @version 1.0
 * @since   1.0
 */
public class SoundEffect implements ISoundEffect {

    /**
     * Clip con los bytes del audio ya cargados en memoria.
     * Se reutiliza en cada llamada a play() mediante setFramePosition(0).
     */
    private final Clip clip;

    /**
     * Construye el efecto de sonido cargando el archivo .wav desde el classpath.
     *
     * @param soundPath ruta relativa a resources/ del archivo .wav.
     *                  Ejemplo: {@code "sounds/coin_sound.wav"}
     * @throws RuntimeException si el archivo no existe o no puede leerse.
     */
    public SoundEffect(String soundPath) {
        try (InputStream raw = getClass().getClassLoader()
                .getResourceAsStream(soundPath)) {

            if (raw == null) {
                throw new RuntimeException(
                        "SoundEffect: no se encontró el archivo en resources/" + soundPath
                                + ". Verifica la clave correspondiente en application.properties.");
            }

            // BufferedInputStream es requerido por AudioSystem.getAudioInputStream()
            // ya que necesita que el stream soporte mark/reset.
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(
                    new BufferedInputStream(raw));

            clip = AudioSystem.getClip();
            clip.open(audioIn); // Carga todos los bytes en memoria una sola vez

        } catch (Exception e) {
            throw new RuntimeException(
                    "SoundEffect: error al cargar '" + soundPath + "': " + e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     *
     * Rebobina el clip al frame 0 y lo reproduce sin bucle (una sola vez).
     * Si el clip estaba reproduciéndose (pulsación rápida de teclas),
     * se detiene y reinicia desde el principio.
     */
    @Override
    public void play() {
        if (clip != null) {
            clip.stop();              // Detiene si ya estaba sonando (evita solapamientos)
            clip.setFramePosition(0); // Rebobina al inicio
            clip.start();            // Reproduce una vez sin bucle (a diferencia de loop())
        }
    }
}