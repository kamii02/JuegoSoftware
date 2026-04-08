package org.kami.shared;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameState {

    private final Map<String, int[]> positions = new ConcurrentHashMap<>();
    private final List<IStateListener> listeners  = new ArrayList<>();

    public void updatePosition(String playerId, int x, int y, int level) {
        positions.put(playerId, new int[]{x, y, level});
        notifyListeners();
    }

    public Map<String, int[]> getPositions() {
        return Collections.unmodifiableMap(positions);
    }

    public void addListener(IStateListener listener) {
        listeners.add(listener);
    }

    private void notifyListeners() {
        listeners.forEach(l -> l.onStateChanged(this));
    }

    /** Serializa: "PLAYER_1:100,200,1|PLAYER_2:150,320,4|" */
    public String serialize() {
        StringBuilder sb = new StringBuilder();
        positions.forEach((id, pos) ->
                sb.append(id).append(":")
                        .append(pos[0]).append(",")
                        .append(pos[1]).append(",")
                        .append(pos[2])
                        .append("|")
        );
        return sb.toString();
    }

    /** Deserializa el string que llega del servidor */
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
                    Integer.parseInt(coords[2])
            );
        }
        return state;
    }
}
