package org.kami.network;


import org.kami.config.IUDPConfig;
import org.kami.model.GameState;
import org.kami.shared.IGameRenderer;

import javax.swing.*;

/**
 * S: única responsabilidad — ser la fachada de red para el Main.
 * Tu Main solo conoce esta clase. No sabe nada de UDP, sockets ni config.
 *
 * D: recibe GameRenderer por constructor — cuando tengas motor gráfico
 *    solo cambias qué renderer pasas, sin tocar esta clase.
 */
public class NetworkManager {

    private static final String READY_MESSAGE = "READY";
    private Runnable onReady;
    private final UdpConnection connection;
    private final IGameRenderer renderer;
    private final String        playerId;

    public NetworkManager(IUDPConfig config, IGameRenderer renderer) {
        this.playerId   = config.getPlayerId();
        this.renderer   = renderer;
        this.connection = new UdpConnection(config.getIp(), config.getPort());
    }

    public void setOnReady(Runnable onReady) {this.onReady = onReady;}

    /** Conecta y arranca el hilo receptor. Llama esto al iniciar el juego. */
    public void connect() throws Exception {
        connection.connect();
        startListening();
        connection.send("MOVE " + playerId + " 0 0 1");
        System.out.println("[NetworkManager] Conectado como " + playerId);
    }

    /**
     * Envía la posición del jugador al servidor.
     * Llama esto cada vez que tu personaje se mueva.
     */
    public void sendPosition(int x, int y, int level) {
        connection.send("MOVE " + playerId + " " + x + " " + y + " " + level);
    }

    /** Cierra la conexión. Llama esto al salir del juego. */
    public void disconnect() {
        connection.disconnect();
    }

    /** Hilo que escucha actualizaciones del servidor y renderiza */
    private void startListening() {
        Thread listener = new Thread(this::listenLoop, "udp-listener");
        listener.setDaemon(true);
        listener.start();
    }

    private void listenLoop() {
        try {
            while (true) {
                String raw = connection.receive();
                handleMessage(raw);
            }
        } catch (Exception e) {
            System.out.println("[NetworkManager] Hilo receptor terminado: " + e.getMessage());
        }
    }

    private void handleMessage(String raw) {
        if (READY_MESSAGE.equals(raw)) {
            notifyReady();
            return;
        }
        GameState updated = GameState.deserialize(raw);
        renderer.render(updated);
    }

    private void notifyReady() {
        if (onReady != null) {
            SwingUtilities.invokeLater(onReady);
        }
    }
}
