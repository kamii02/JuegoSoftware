package org.kami.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kami.shared.GameState;
import org.kami.shared.IGameRenderer;
import org.kami.view.Layout;

import javax.swing.*;
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class SwingRenderer implements IGameRenderer {
    private final Layout layout;
    private final String myId;


    @Override
    public void render(GameState state) {
        SwingUtilities.invokeLater(() ->
            layout.updRemotePlayers(state.getPositions(), myId)
        );
    }
}
