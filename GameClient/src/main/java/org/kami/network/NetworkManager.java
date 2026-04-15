package org.kami.network;


import org.kami.config.IUDPConfig;
import org.kami.model.GameState;
import org.kami.shared.IGameRenderer;

import javax.swing.*;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Actúa como la fachada de red del juego.
 * Encapsula toda la lógica de comunicación UDP y expone métodos simples
 * para conectar, enviar datos y recibir actualizaciones del servidor.
 * Permite desacoplar la lógica de red del resto de la aplicación.
 */
public class NetworkManager {

    /**
     * Mensaje que indica que el servidor está listo.
     */
    private static final String READY_MESSAGE = "READY";

    /**
     * Prefijo de mensaje que indica que un jugador ha ganado.
     */
    private static final String WIN           = "WIN";

    /**
     * Acción a ejecutar cuando el servidor indica que está listo.
     */
    private Runnable onReady;

    /**
     * Acción a ejecutar cuando se recibe un mensaje de victoria.
     */
    private Consumer<String> onWin;

    /**
     * Conexión UDP utilizada para la comunicación con el servidor.
     */
    private final UdpConnection connection;

    /**
     * Componente encargado de renderizar el estado del juego.
     */
    private final IGameRenderer renderer;

    /**
     * Identificador único del jugador.
     */
    private final String playerId;

    /**
     * Constructor que inicializa la configuración de red y el renderer.
     * @param config configuración UDP del cliente
     * @param renderer componente de renderizado del juego
     */
    public NetworkManager(IUDPConfig config, IGameRenderer renderer) {
        this.playerId   = config.getPlayerId();
        this.renderer   = renderer;
        this.connection = new UdpConnection(config.getIp(), config.getPort());
    }

    /**
     * Establece la acción a ejecutar cuando el servidor esté listo.
     * @param onReady acción a ejecutar
     */
    public void setOnReady(Runnable onReady) {this.onReady = onReady;}

    /**
     * Establece la acción a ejecutar cuando se reciba un mensaje de victoria.
     * @param onWin acción a ejecutar
     */
    public void setOnWin(Consumer<String> onWin) {this.onWin = onWin;}

    /**
     * Establece la conexión con el servidor e inicia el hilo de escucha.
     * También envía una posición inicial del jugador.
     * @throws Exception si ocurre un error al conectar
     */
    public void connect() throws Exception {
        connection.connect();
        startListening();
        connection.send("MOVE " + playerId + " 0 0 1 0");
        System.out.println("[NetworkManager] Conectado como " + playerId);
    }

    /**
     * Envía la posición actual del jugador al servidor.
     * @param x coordenada horizontal
     * @param y coordenada vertical
     * @param level nivel del jugador
     * @param score puntaje del jugador
     */
    public void sendPosition(int x, int y, int level, int score) {
        connection.send("MOVE " + playerId + " " + x + " " + y + " " + level + " " + score);
    }

    /**
     * Cierra la conexión con el servidor.
     */
    public void disconnect() {
        connection.disconnect();
    }

    /**
     * Envía un mensaje indicando que el jugador ha ganado.
     */
    public void sendWin(){
        connection.send("WIN " + playerId);
    }

    /**
     * Inicia un hilo en segundo plano que escucha mensajes del servidor.
     */
    private void startListening() {
        Thread listener = new Thread(this::listenLoop, "udp-listener");
        listener.setDaemon(true);
        listener.start();
    }

    /**
     * Bucle que recibe mensajes continuamente desde el servidor.
     */
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

    /**
     * Procesa los mensajes recibidos desde el servidor.
     * @param raw mensaje recibido
     */
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

    /**
     * Notifica que el servidor está listo.
     */
    private void notifyReady() {
        if (onReady != null) {
            SwingUtilities.invokeLater(onReady);
        }
    }

    /**
     * Notifica que se ha recibido un mensaje de victoria.
     * @param raw mensaje recibido
     */
    private void notifyWin(String raw){
        if(onWin != null){
            SwingUtilities.invokeLater(() -> onWin.accept(raw));
        }
    }
}