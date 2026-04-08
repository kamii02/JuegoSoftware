package org.kami.client;


import org.kami.config.IUDPConfig;
import org.kami.config.UDPConfig;
import org.kami.shared.GameState;
import org.kami.shared.IGameRenderer;

/**
 * S: única responsabilidad — ser la fachada de red para el Main.
 * Tu Main solo conoce esta clase. No sabe nada de UDP, sockets ni config.
 *
 * D: recibe GameRenderer por constructor — cuando tengas motor gráfico
 *    solo cambias qué renderer pasas, sin tocar esta clase.
 */
public class NetworkManager {

    private final UdpConnection connection;
    private final IGameRenderer renderer;
    private final String        playerId;

    public NetworkManager(IUDPConfig config, IGameRenderer renderer) {
        this.playerId   = config.getPlayerId();
        this.renderer   = renderer;
        this.connection = new UdpConnection(config.getIp(), config.getPort());
    }

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
        Thread listener = new Thread(() -> {
            try {
                while (true) {
                    String raw         = connection.receive();
                    GameState updated  = GameState.deserialize(raw);
                    renderer.render(updated);
                }
            } catch (Exception e) {
                System.out.println("[NetworkManager] Hilo receptor terminado: " + e.getMessage());
            }
        }, "udp-listener");

        listener.setDaemon(true); // Se cierra solo cuando cierra el juego
        listener.start();
    }
}
