package org.kami.network;


import org.kami.shared.INetworkConnection;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Implementación de una conexión de red basada en UDP.
 * Permite enviar y recibir mensajes a través de datagramas,
 * encapsulando la lógica de sockets para comunicación con el servidor.
 */
public class UdpConnection implements INetworkConnection {

    /**
     * Tamaño del buffer utilizado para recibir datos.
     */
    private static final int BUFFER_SIZE = 512;

    /**
     * Dirección del servidor.
     */
    private final String host;

    /**
     * Puerto del servidor.
     */
    private final int    port;

    /**
     * Socket UDP utilizado para la comunicación.
     */
    private DatagramSocket socket;

    /**
     * Dirección IP del servidor.
     */
    private InetAddress serverAddress;

    /**
     * Constructor que inicializa la dirección y puerto del servidor.
     * @param host dirección del servidor
     * @param port puerto del servidor
     */
    public UdpConnection(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Establece la conexión creando el socket y resolviendo la dirección del servidor.
     * @throws Exception si ocurre un error al inicializar la conexión
     */
    @Override
    public void connect() throws Exception {
        socket        = new DatagramSocket();
        serverAddress = InetAddress.getByName(host);
        System.out.println("[UdpConnection] Listo para enviar a " + host + ":" + port);
    }

    /**
     * Envía un mensaje al servidor mediante un paquete UDP.
     * @param message mensaje a enviar
     */
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

    /**
     * Recibe un mensaje del servidor.
     * Este método es bloqueante y espera hasta que llegue un paquete.
     * @return mensaje recibido
     * @throws Exception si ocurre un error durante la recepción
     */
    public String receive() throws Exception {
        byte[]         buffer = new byte[BUFFER_SIZE];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        return new String(packet.getData(), 0, packet.getLength()).trim();
    }

    /**
     * Cierra la conexión UDP si está activa.
     */
    @Override
    public void disconnect() {
        if (socket != null && !socket.isClosed()) {
            socket.close();
            System.out.println("[UdpConnection] Desconectado.");
        }
    }
}