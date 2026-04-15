package org.kami.network;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kami.model.GameState;
import org.kami.shared.IGameRenderer;
import org.kami.view.Layout;

import javax.swing.*;

/**
 * Implementación de IGameRenderer que utiliza Swing para actualizar la interfaz gráfica.
 * Se encarga de renderizar el estado del juego en la vista mediante el uso del layout.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class SwingRenderer implements IGameRenderer {

    /**
     * Componente de la interfaz encargado de representar visualmente el juego.
     */
    private final Layout layout;

    /**
     * Identificador del jugador local.
     */
    private final String myId;

    /**
     * Renderiza el estado actual del juego en la interfaz gráfica.
     * La actualización se ejecuta en el hilo de eventos de Swing.
     * @param state estado actual del juego
     */
    @Override
    public void render(GameState state) {
        SwingUtilities.invokeLater(() ->
                layout.updRemotePlayers(state.getPositions(), myId)
        );
    }
}