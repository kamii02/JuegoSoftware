package org.kami.shared;

import java.io.IOException;

/**
 * I + D: contrato mínimo de red.
 * GameClient depende de esta abstracción, no de UDP ni TCP directamente.
 */
public interface INetworkConnection {
    void connect() throws Exception;
    void send(String message);
    void disconnect();
}