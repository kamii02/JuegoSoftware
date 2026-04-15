package org.kami.network;


import org.kami.shared.INetworkConnection;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * D: implementación concreta de NetworkConnection.
 * GameClient nunca importa esta clase — depende solo de la interfaz.
 */
public class UdpConnection implements INetworkConnection {

    private static final int BUFFER_SIZE = 512;

    private final String host;
    private final int    port;
    private DatagramSocket socket;
    private InetAddress serverAddress;

    public UdpConnection(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void connect() throws Exception {
        socket        = new DatagramSocket(); // Puerto aleatorio para el cliente
        serverAddress = InetAddress.getByName(host);
        System.out.println("[UdpConnection] Listo para enviar a " + host + ":" + port);
    }

    @Override
    public void send(String message) {
        try {
            byte[]         data   = message.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, port);
            socket.send(packet);
        } catch (Exception e) {
            System.out.println("[UdpConnection] Error al enviar: " + e.getMessage());
        }
    }

    /** Bloqueante — espera el próximo paquete del servidor */
    public String receive() throws Exception {
        byte[]         buffer = new byte[BUFFER_SIZE];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        return new String(packet.getData(), 0, packet.getLength()).trim();
    }

    @Override
    public void disconnect() {
        if (socket != null && !socket.isClosed()) {
            socket.close();
            System.out.println("[UdpConnection] Desconectado.");
        }
    }
}

