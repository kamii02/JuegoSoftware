package org.kami.client;

import org.kami.shared.GameState;
import org.kami.shared.IGameRenderer;

public class ConsoleRenderer implements IGameRenderer {
    @Override
    public void render(GameState state) {
        System.out.println("--- Estado del juego ---");
        state.getPositions().forEach((id, pos) ->
                System.out.printf("  %s → x=%-4d y=%d%n", id, pos[0], pos[1])
        );
        System.out.println("------------------------");
    }
}
