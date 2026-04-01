package org.kami.shared;

public interface INetworkConnection {
    void connect() throws Exception;
    void send(String message);
    void disconnect();
}
