/**
 * Proporciona las interfaces y clases relacionadas con la gestión de audio
 * dentro del juego.
 *
 * <p>
 * Este paquete define el contrato para la reproducción de música global,
 * siguiendo el principio de programación contra interfaces en lugar de
 * implementaciones concretas.
 * </p>
 *
 * <p>
 * Incluye la interfaz {@link org.kami.audio.IMusicPlayer}, que establece
 * las operaciones básicas para controlar la reproducción de música, y su
 * implementación {@link org.kami.audio.MusicPlayer}, basada en la API
 * estándar {@code javax.sound.sampled} del JDK.
 * </p>
 *
 * <p>
 * El sistema de audio está diseñado para:
 * <ul>
 *   <li>Reproducir música de fondo en bucle continuo.</li>
 *   <li>Gestionar correctamente los recursos de audio.</li>
 *   <li>Desacoplar el uso del audio de su implementación concreta.</li>
 * </ul>
 * </p>
 *
 * @since 1.0
 */
package org.kami.audio;