package org.kami.server;

import org.kami.shared.GameState;

public class MoveHandler implements IMessageHandler {
    @Override
    public boolean canHandle(String messageType) {
        return "MOVE".equals(messageType);
    }

    @Override
    public void handle(String message, GameState state, UdpBroadcaster broadcaster) {
        // Formato esperado: "MOVE PLAYER_1 120 340"
        try {
            String[] parts  = message.split(" ");
            String playerId = parts[1];
            int x           = Integer.parseInt(parts[2]);
            int y           = Integer.parseInt(parts[3]);

            state.updatePosition(playerId, x, y);
            broadcaster.broadcast(state.serialize());

        } catch (Exception e) {
            System.out.println("[MoveHandler] Mensaje malformado: " + message);
        }
    }
}
