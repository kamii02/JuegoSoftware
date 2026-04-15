package org.kami.model;

import org.kami.shared.IStateListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Representa el estado global del juego.
 * Mantiene las posiciones de los jugadores junto con su nivel y puntaje,
 * y permite notificar a los observadores cuando el estado cambia.
 */
public class GameState {

    /**
     * Mapa que almacena las posiciones de los jugadores.
     * La clave es el identificador del jugador y el valor es un arreglo
     * con la información de posición, nivel y puntaje.
     */
    private final Map<String, int[]> positions = new ConcurrentHashMap<>();

    /**
     * Lista de observadores que escuchan cambios en el estado del juego.
     */
    private final List<IStateListener> listeners  = new ArrayList<>();

    /**
     * Actualiza la posición, nivel y puntaje de un jugador
     * y notifica a los observadores del cambio.
     * @param playerId identificador del jugador
     * @param x coordenada horizontal
     * @param y coordenada vertical
     * @param level nivel del jugador
     * @param score puntaje del jugador
     */
    public void updatePosition(String playerId, int x, int y, int level, int score) {
        positions.put(playerId, new int[]{x, y, level, score});
        notifyListeners();
    }

    /**
     * Obtiene un mapa inmodificable con las posiciones actuales de los jugadores.
     * @return mapa de posiciones
     */
    public Map<String, int[]> getPositions() {
        return Collections.unmodifiableMap(positions);
    }

    /**
     * Agrega un observador que será notificado cuando el estado cambie.
     * @param listener observador a registrar
     */
    public void addListener(IStateListener listener) {
        listeners.add(listener);
    }

    /**
     * Notifica a todos los observadores registrados sobre un cambio en el estado.
     */
    private void notifyListeners() {
        listeners.forEach(l -> l.onStateChanged(this));
    }

    /**
     * Serializa el estado del juego en un formato de texto.
     * El formato incluye el identificador del jugador seguido de sus datos.
     * @return representación en texto del estado del juego
     */
    public String serialize() {
        StringBuilder sb = new StringBuilder();
        positions.forEach((id, pos) ->
                sb.append(id).append(":")
                        .append(pos[0]).append(",")
                        .append(pos[1]).append(",")
                        .append(pos[2]).append(",")
                        .append(pos[3])
                        .append("|")
        );
        return sb.toString();
    }

    /**
     * Deserializa una cadena de texto en un objeto GameState.
     * Reconstruye las posiciones de los jugadores a partir del formato recibido.
     * @param raw cadena con la información serializada
     * @return instancia de GameState con los datos cargados
     */
    public static GameState deserialize(String raw) {
        GameState state = new GameState();
        for (String entry : raw.split("\\|")) {
            if (entry.isEmpty()) continue;
            String[] parts  = entry.split(":");
            String[] coords = parts[1].split(",");
            state.updatePosition(
                    parts[0],
                    Integer.parseInt(coords[0]),
                    Integer.parseInt(coords[1]),
                    Integer.parseInt(coords[2]),
                    Integer.parseInt(coords[3])
            );
        }
        return state;
    }
}