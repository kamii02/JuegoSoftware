package org.kami.network;


import org.kami.config.IUDPConfig;
import org.kami.model.GameState;
import org.kami.shared.IGameRenderer;

import javax.swing.*;
import java.util.Map;
import java.util.function.Consumer;

/**
 * S: única responsabilidad — ser la fachada de red para el Main.
 * Tu Main solo conoce esta clase. No sabe nada de UDP, sockets ni config.
 *
 * D: recibe GameRenderer por constructor — cuando tengas motor gráfico
 *    solo cambias qué renderer pasas, sin tocar esta clase.
 */
public class NetworkManager {

    private static final String READY_MESSAGE = "READY";
    private static final String WIN           = "WIN";
    private Runnable onReady;
    private Consumer<String> onWin;
    private final UdpConnection connection;
    private final IGameRenderer renderer;
    private final String        playerId;

    public NetworkManager(IUDPConfig config, IGameRenderer renderer) {
        this.playerId   = config.getPlayerId();
        this.renderer   = renderer;
        this.connection = new UdpConnection(config.getIp(), config.getPort());
    }

    public void setOnReady(Runnable onReady) {this.onReady = onReady;}

    public void setOnWin(Consumer<String> onWin) {this.onWin = onWin;}

    /** Conecta y arranca el hilo receptor. Llama esto al iniciar el juego. */
    public void connect() throws Exception {
        connection.connect();
        startListening();
        connection.send("MOVE " + playerId + " 0 0 1 0");
        System.out.println("[NetworkManager] Conectado como " + playerId);
    }

    /**
     * Envía la posición del jugador al servidor.
     * Llama esto cada vez que tu personaje se mueva.
     */
    public void sendPosition(int x, int y, int level, int score) {
        connection.send("MOVE " + playerId + " " + x + " " + y + " " + level + " " + score);
    }

    /** Cierra la conexión. Llama esto al salir del juego. */
    public void disconnect() {
        connection.disconnect();
    }

    public void sendWin(){
        connection.send("WIN " + playerId);
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
        if (raw.startsWith(WIN)){
            notifyWin(raw);
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

    private void notifyWin(String raw){
        if(onWin != null){
            SwingUtilities.invokeLater(() -> onWin.accept(raw));
        }
    }
}
