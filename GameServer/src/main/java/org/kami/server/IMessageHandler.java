package org.kami.server;

import org.kami.shared.GameState;

public interface IMessageHandler {
    boolean canHandle(String messageType);
    void handle(String message, GameState state, UdpBroadcaster broadcaster);
}
