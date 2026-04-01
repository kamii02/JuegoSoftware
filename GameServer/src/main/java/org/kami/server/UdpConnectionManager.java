package org.kami.server;

import org.kami.shared.GameState;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.List;

public class UdpConnectionManager {
    private static final int BUFFER_SIZE = 512;

    private final int port;
    private final GameState state;
    private final List<IMessageHandler> handlers;
    private UdpBroadcaster broadcaster;

    public UdpConnectionManager(int port, GameState state, List<IMessageHandler> handlers) {
        this.port     = port;
        this.state    = state;
        this.handlers = handlers;
    }

    public void start() throws Exception {
        DatagramSocket socket = new DatagramSocket(port);
        broadcaster = new UdpBroadcaster(socket);
        System.out.println("[Server] Escuchando UDP en puerto " + port);

        byte[] buffer = new byte[BUFFER_SIZE];

        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet); // Bloqueante — espera el próximo paquete

            String message = new String(packet.getData(), 0, packet.getLength()).trim();
            InetSocketAddress sender = new InetSocketAddress(
                    packet.getAddress(), packet.getPort()
            );

            broadcaster.addClient(sender); // Registra automáticamente al cliente
            dispatch(message);
        }
    }

    /** O: despacha sin if/else — cada handler decide si puede procesar el mensaje */
    private void dispatch(String message) {
        String type = message.split(" ")[0];
        handlers.stream()
                .filter(h -> h.canHandle(type))
                .findFirst()
                .ifPresentOrElse(
                        h  -> h.handle(message, state, broadcaster),
                        () -> System.out.println("[Server] Mensaje desconocido: " + message)
                );
    }
}
