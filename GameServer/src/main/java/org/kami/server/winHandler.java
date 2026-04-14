package org.kami.server;

import org.kami.shared.GameState;

public class winHandler implements IMessageHandler{

    private static final String WIN = "WIN";

    @Override
    public boolean canHandle(String messageType) {
        return WIN.equals(messageType);
    }

    @Override
    public void handle(String message, GameState state, UdpBroadcaster broadcaster) {
        String winnerId = message.split(" ")[1];
        broadcaster.broadcast("WIN " +  winnerId + " " + state.serialize());
    }
}
