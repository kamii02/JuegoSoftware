package org.kami.shared;

import org.kami.model.GameState;

/**
 * Define el contrato para los componentes encargados de renderizar
 * el estado del juego.
 * Permite desacoplar la lógica de renderizado de la lógica del juego.
 */
public interface IGameRenderer {

    /**
     * Renderiza el estado actual del juego.
     * @param state estado del juego a representar
     */
    void render(GameState state);
}