package org.kami.controller;

/**
 * Interfaz que define los eventos relacionados con colisiones dentro del juego.
 *
 * <p>Permite implementar el patrón Observer (escuchadores),
 * donde distintas clases pueden reaccionar a eventos de colisión
 * sin acoplarse directamente a la lógica del {@link CollisionManager}.</p>
 *
 * <p>Las implementaciones de esta interfaz pueden utilizarse para:</p>
 * <ul>
 *     <li>Actualizar la interfaz gráfica</li>
 *     <li>Reproducir sonidos</li>
 *     <li>Actualizar puntajes</li>
 *     <li>Controlar el flujo del juego</li>
 * </ul>
 */
public interface ICollisionListener {

    /**
     * Se ejecuta cuando el jugador colisiona con un muro.
     *
     * Puede utilizarse para:
     * - Reproducir un sonido
     * - Mostrar animación de impacto
     * - Bloquear movimiento
     */
    void onWallHit();

    /**
     * Se ejecuta cuando el jugador recoge una moneda.
     *
     * Puede utilizarse para:
     * - Actualizar el puntaje en pantalla
     * - Reproducir sonido de recolección
     * - Mostrar efectos visuales
     */
    void onCoinCollected();

    /**
     * Se ejecuta cuando el jugador entra en una puerta
     * y cambia de nivel.
     *
     * @param newLevel nuevo nivel al que se accede
     */
    void onDoorEntered(int newLevel);

    /**
     * Se ejecuta cuando el jugador completa el último nivel
     * y gana el juego.
     *
     * Puede utilizarse para:
     * - Mostrar pantalla de victoria
     * - Detener el juego
     * - Enviar resultados a la red
     */
    void onGameWon();
}