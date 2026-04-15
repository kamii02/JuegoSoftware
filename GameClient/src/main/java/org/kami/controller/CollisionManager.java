package org.kami.controller;

import org.kami.audio.ISoundEffect;
import org.kami.model.Player;
import org.kami.maps.IMapsHandler;
import org.kami.model.Coin;
import org.kami.model.GameMap;
import org.kami.model.Wall;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Clase encargada de gestionar todas las colisiones del juego.
 *
 * Controla:
 * - Colisiones con muros
 * - Recolección de monedas
 * - Interacción con puertas (cambio de nivel)
 * - Eventos asociados a colisiones (listeners)
 *
 * También maneja sonidos y posiciones de aparición del jugador.
 */
public class CollisionManager {

    /** Último nivel del juego */
    private static final int LAST_LEVEL = 4;

    /** Manejador de mapas */
    private final IMapsHandler mapsHandler;

    /** Referencia al jugador */
    private final Player player;

    /** Lista de oyentes para eventos de colisión */
    private final List<ICollisionListener> listener = new ArrayList<>();

    /** Sonido al recoger moneda */
    private ISoundEffect coinSound;

    /** Sonido al colisionar con muro */
    private ISoundEffect wallCollisionSound;

    /** Sonido al colisionar con jugador */
    private ISoundEffect playerCollisionSound;

    /** Indica si se está realizando un cambio de nivel */
    private boolean changeLevel = false;

    /**
     * Posiciones de spawn del jugador por nivel
     * Key: nivel
     * Value: [posX, posY]
     */
    private static final Map<Integer, int[]> SPAWN_POSITIONS = Map.of(
            1, new int[]{100,700},
            2, new int[]{600,740},
            3, new int[]{220,740},
            4, new int[]{50,740}
    );

    /**
     * Constructor del CollisionManager
     *
     * @param mapsHandler manejador de mapas
     * @param player referencia al jugador
     */
    public CollisionManager(IMapsHandler mapsHandler, Player player) {
        this.mapsHandler = mapsHandler;
        this.player = player;
    }

    /**
     * Agrega un listener de colisiones
     *
     * @param listener objeto que escucha eventos de colisión
     */
    public void addListener(ICollisionListener listener) {
        this.listener.add(listener);
    }

    /**
     * Elimina un listener de colisiones
     *
     * @param listener listener a eliminar
     */
    public void removeListener(ICollisionListener listener) {
        this.listener.remove(listener);
    }

    /**
     * Define el sonido al recoger monedas
     */
    public void setCoinSound(ISoundEffect coinSound) {
        this.coinSound = coinSound;
    }

    /**
     * Define el sonido al colisionar con muros
     */
    public void setWallCollissionSound(ISoundEffect wallCollisionSound) {
        this.wallCollisionSound = wallCollisionSound;
    }

    /**
     * Define el sonido al colisionar con jugador
     */
    public void setPlayerCollissionSound(ISoundEffect playerCollisionSound) {
        this.playerCollisionSound = playerCollisionSound;
    }

    /**
     * Indica si el jugador está cambiando de nivel
     *
     * @return true si está en transición de nivel
     */
    public boolean isChangeLevel() {
        return changeLevel;
    }

    /**
     * Verifica si el jugador colisiona con algún muro
     *
     * @param newX nueva posición X del jugador
     * @param newY nueva posición Y del jugador
     * @param level nivel actual
     * @return true si hay colisión con un muro
     */
    public boolean collidesWithWall(int newX, int newY, int level) {
        GameMap gameMap = mapsHandler.readMaps().get(level - 1);
        int ps = player.getTamanio();

        for (Wall wall : gameMap.getWalls()) {
            boolean chocaX = newX < wall.getX() + wall.getWidth()  && newX + ps > wall.getX();
            boolean chocaY = newY < wall.getY() + wall.getHeight() && newY + ps > wall.getY();
            if (chocaX && chocaY) return true;
        }
        return false;
    }

    /**
     * Verifica colisión con monedas y las recolecta si aplica
     *
     * @param level nivel actual
     */
    public void checkCoinCollision(int level) {
        GameMap gameMap = mapsHandler.readMaps().get(level - 1);
        int px = player.getPosX();
        int py = player.getPosY();
        int ps = player.getTamanio();

        for (int i = 0; i < gameMap.getCoins().size(); i++) {
            Coin coin = gameMap.getCoins().get(i);

            boolean solapaX = px < coin.getX() + coin.getWidth()  && px + ps > coin.getX();
            boolean solapaY = py < coin.getY() + coin.getHeight() && py + ps > coin.getY();

            if (solapaX && solapaY) {
                if (coinSound != null) coinSound.play();

                // Suma puntos al jugador
                player.setScore(player.getScore() + coin.getPoints());

                // Elimina la moneda del mapa
                gameMap.getCoins().remove(i);
                i--;

                // Notifica a los listeners
                listener.forEach(ICollisionListener::onCoinCollected);
            }
        }
    }

    /**
     * Verifica si el jugador entra en una puerta para cambiar de nivel
     *
     * @param level nivel actual
     * @param totalLevels total de niveles
     * @param onLevelChange acción al cambiar nivel
     * @param onSpawn acción al reaparecer jugador
     */
    public void checkDoorCollision(int level, int totalLevels,
                                   Consumer<Integer> onLevelChange,
                                   Runnable onSpawn) {

        GameMap gameMap = mapsHandler.readMaps().get(level - 1);

        // No permite entrar a la puerta si aún hay monedas
        if (!gameMap.getCoins().isEmpty()) return;

        int px = player.getPosX();
        int py = player.getPosY();
        int ps = player.getTamanio();

        gameMap.getDoors().stream()
                .filter(door -> {
                    boolean solapaX = px < door.getX() + door.getWidth()  && px + ps > door.getX();
                    boolean solapaY = py < door.getY() + door.getHeight() && py + ps > door.getY();
                    return solapaX && solapaY;
                })
                .findFirst()
                .ifPresent(door -> {
                    changeLevel = true;

                    if (level == LAST_LEVEL) {
                        // Si es el último nivel, el jugador gana
                        listener.forEach(ICollisionListener::onGameWon);
                    } else {
                        int newLevel = level + 1;

                        // Reubica jugador
                        spawnPlayer(newLevel);

                        // Notifica listeners
                        listener.forEach(l -> l.onDoorEntered(newLevel));

                        // Ejecuta acciones externas
                        onLevelChange.accept(newLevel);
                        onSpawn.run();
                    }

                    // Delay para evitar múltiples activaciones
                    Timer t = new Timer(200, e -> changeLevel = false);
                    t.setRepeats(false);
                    t.start();
                });
    }

    /**
     * Posiciona al jugador en el spawn correspondiente al nivel
     *
     * @param level nivel destino
     */
    public void spawnPlayer(int level) {
        int[] pos = SPAWN_POSITIONS.get(level);
        if (pos != null) {
            player.setPosX(pos[0]);
            player.setPosY(pos[1]);
        }
    }

    /**
     * Evento cuando el jugador golpea un muro
     */
    public void onWallHit() {
        if (wallCollisionSound != null) wallCollisionSound.play();
        listener.forEach(ICollisionListener::onWallHit);
    }
}