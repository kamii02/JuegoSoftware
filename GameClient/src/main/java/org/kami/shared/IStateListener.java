package org.kami.shared;

import org.kami.model.GameState;

/**
 * Define el contrato para los componentes que desean ser notificados
 * cuando el estado del juego cambia.
 * Permite implementar el patrón observador.
 */
public interface IStateListener {

    /**
     * Método invocado cuando el estado del juego ha cambiado.
     * @param state estado actualizado del juego
     */
    void onStateChanged(GameState state);
}