package org.kami;

import org.kami.config.IConfigReader;
import org.kami.config.PropertiesManager;
import org.kami.server.IMessageHandler;
import org.kami.server.MoveHandler;
import org.kami.server.UdpConnectionManager;
import org.kami.shared.GameState;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        IConfigReader reader = new PropertiesManager("application.properties");
        int port = reader.getPort();

        GameState state =  new GameState();

        List<IMessageHandler> handlers = List.of(
          new MoveHandler()
        );

        UdpConnectionManager manager = new UdpConnectionManager(port, state, handlers);
        manager.start();
    }
}
