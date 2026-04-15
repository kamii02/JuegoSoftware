package org.kami.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class UdpBroadcaster {
    private static final int MIN_PLAYERS_TO_START = 2;
    private static final String READY_MESSAGE     = "READY";
    private final DatagramSocket socket;
    private final List<InetSocketAddress> clients = new ArrayList<>();

    public UdpBroadcaster(DatagramSocket socket) {
        this.socket = socket;
    }

    public synchronized void addClient(InetSocketAddress address) {
        if (!clients.contains(address)) {
            clients.add(address);
            System.out.println("[Broadcaster] Cliente registrado: " + address);
        }
        if(isReadyToStart()){
            broadcast(READY_MESSAGE);
        }
    }

    public synchronized void removeClient(InetSocketAddress address) {
        clients.remove(address);
        System.out.println("[Broadcaster] Cliente eliminado: " + address);
    }

    public synchronized void broadcast(String message) {
        byte[] data = message.getBytes();
        clients.forEach(address -> {
            try {
                DatagramPacket packet = new DatagramPacket(
                        data, data.length,
                        address.getAddress(),
                        address.getPort()
                );
                socket.send(packet);
            } catch (Exception e) {
                System.out.println("[Broadcaster] Error enviando a " + address + ": " + e.getMessage());
            }
        });
    }

    private boolean isReadyToStart() {
        return clients.size() >= MIN_PLAYERS_TO_START;
    }
}
