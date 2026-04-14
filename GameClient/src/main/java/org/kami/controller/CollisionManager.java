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

public class CollisionManager {
    private static final int LAST_LEVEL = 4;
    private final IMapsHandler mapsHandler;
    private final Player player;
    private final List<ICollisionListener> listener = new  ArrayList<>();

    private ISoundEffect coinSound;
    private ISoundEffect wallCollisionSound;
    private ISoundEffect playerCollisionSound;

    private boolean changeLevel = false;
    private static final Map<Integer, int[]> SPAWN_POSITIONS = Map.of(
            1, new int[]{100,700},
            2, new int[]{600,740},
            3, new int[]{220,740},
            4, new int[]{50,740}
    );

    public CollisionManager(IMapsHandler mapsHandler, Player player) {
        this.mapsHandler = mapsHandler;
        this.player = player;
    }

    public void addListener(ICollisionListener listener) {
        this.listener.add(listener);
    }
    public void removeListener(ICollisionListener listener) {this.listener.remove(listener);}

    public void setCoinSound(ISoundEffect coinSound) {
        this.coinSound = coinSound;
    }

    public void setWallCollissionSound(ISoundEffect wallCollisionSound) {
        this.wallCollisionSound = wallCollisionSound;
    }

    public void setPlayerCollissionSound(ISoundEffect playerCollisionSound) {
        this.playerCollisionSound = playerCollisionSound;
    }

    public boolean isChangeLevel() {
        return changeLevel;
    }

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
                player.setScore(player.getScore() + coin.getPoints());
                gameMap.getCoins().remove(i);
                i--;
                listener.forEach(ICollisionListener::onCoinCollected);
            }
        }
    }

    public void checkDoorCollision(int level, int totalLevels,
                                   Consumer<Integer> onLevelChange,
                                   Runnable onSpawn) {
        GameMap gameMap = mapsHandler.readMaps().get(level - 1);

        if(!gameMap.getCoins().isEmpty()) return;

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
                        listener.forEach(ICollisionListener::onGameWon);
                    } else {
                        int newLevel = level + 1;
                        spawnPlayer(newLevel);
                        listener.forEach(l -> l.onDoorEntered(newLevel));
                        onLevelChange.accept(newLevel);
                        onSpawn.run();
                    }

                    Timer t = new Timer(200, e -> changeLevel = false);
                    t.setRepeats(false);
                    t.start();
                });
    }

    public void spawnPlayer(int level) {
        int[] pos = SPAWN_POSITIONS.get(level);
        if (pos != null) {
            player.setPosX(pos[0]);
            player.setPosY(pos[1]);
        }
    }

    public void onWallHit() {
        if (wallCollisionSound != null) wallCollisionSound.play();
        listener.forEach(ICollisionListener::onWallHit);
    }
}
