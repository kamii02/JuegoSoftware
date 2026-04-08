package org.kami.view;

import org.kami.config.element.Player;
import org.kami.config.ILayoutConfig;
import org.kami.config.maps.IMapsHandler;
import org.kami.view.maps.elements.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import org.kami.audio.ISoundEffect;

public class Layout extends JPanel {

    private Player player;
    private IMapsHandler mapsHandler;
    private int level;

    private final Map<String, int[]> remotePlayers = new ConcurrentHashMap<>();

    private BiConsumer<Integer,Integer> onMove;

    private ISoundEffect coinSound;
    private ISoundEffect wallCollisionSound;
    private ISoundEffect playerCollisionSound;
    private CoinAnimator coinAnimator;
    private boolean changeLevel = false;

    private Image coinImage;
    private IBackgroundLoader backgroundLoader;
    private Map<String, Image> imageCache = new HashMap<>();

    private static final Map<Integer, int[]> SPAWN_POSITIONS = Map.of(
            1, new int[]{100,700},
            2, new int[]{600,740},
            3, new int[]{220,740},
            4, new int[]{50,740}
    );

    public Layout(ILayoutConfig config, IMapsHandler mapsHandler, Player player) {
        this.player = player;
        this.level = 1;
        this.mapsHandler = mapsHandler;
        this.backgroundLoader = new BackgroundLoader(mapsHandler); // ✅ se crea aquí directamente

        setFocusable(true);
        requestFocusInWindow();
        startControlls();

        this.backgroundLoader.load(level); // ✅ ya no es null

        this.coinAnimator = new CoinAnimator(7000, this::repaint);
        coinAnimator.start();
    }

    public void setOnMove(BiConsumer<Integer, Integer> onMove) {
        this.onMove = onMove;
    }

    public void setCoinSound(ISoundEffect coinSound) {
        this.coinSound = coinSound;
    }

    public void setWallCollisionSound(ISoundEffect wallCollisionSound) {
        this.wallCollisionSound = wallCollisionSound;
    }

    public void setPlayerCollisionSound(ISoundEffect playerCollisionSound) {
        this.playerCollisionSound = playerCollisionSound;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        backgroundLoader.draw(g2d, getWidth(), getHeight(), this);
        drawMapElements(g2d);
        drawRemotePlayers(g2d);
        drawPlayer(g2d);
    }

    private void drawMapElements(Graphics2D g2d) {
        GameMap gameMap = mapsHandler.readMaps().get(level-1);
        for (Wall wall : gameMap.getWalls()) {

            if (wall.getTexturePath() != null) {

                Image img = imageCache.computeIfAbsent(
                        wall.getTexturePath(),
                        path -> new ImageIcon(
                                getClass().getResource("/images/" + path)
                        ).getImage()
                );

                g2d.drawImage(
                        img,
                        wall.getX(),
                        wall.getY(),
                        wall.getWidth(),
                        wall.getHeight(),
                        null
                );

            } else {
                g2d.setColor(Color.GRAY);
                g2d.fillRect(
                        wall.getX(),
                        wall.getY(),
                        wall.getWidth(),
                        wall.getHeight()
                );
            }
        }

        gameMap.getDoors().forEach(door -> {

            if (door.getTexturePath() != null) {

                Image img = imageCache.computeIfAbsent(
                        door.getTexturePath(),
                        path -> new ImageIcon(
                                getClass().getResource("/images/" + path)
                        ).getImage()
                );

                g2d.drawImage(
                        img,
                        door.getX(),
                        door.getY(),
                        door.getWidth(),
                        door.getHeight(),
                        null
                );

            } else {
                g2d.setColor(Color.RED);
                g2d.fillRect(
                        door.getX(),
                        door.getY(),
                        door.getWidth(),
                        door.getHeight()
                );
            }
        });

        // monedas
        gameMap.getCoins().forEach(coin -> {
            g2d.drawImage(
                    coinImage,
                    coin.getX(),
                    coin.getY(),
                    coin.getWidth(),
                    coin.getHeight(),
                    null
            );
        });
    }

    private void drawRemotePlayers(Graphics2D g2d) {
        Random rand = new Random();

        remotePlayers.forEach((id, pos) -> {
            g2d.setColor(new Color(
                    rand.nextInt(256),
                    rand.nextInt(256),
                    rand.nextInt(256)
            ));
            g2d.fillRect(pos[0], pos[1], player.getTamanio(), player.getTamanio());
            g2d.setColor(Color.BLACK);
            g2d.drawString(id, pos[0], pos[1]-5);
        });
    }

    public void updRemotePlayers(Map<String, int[]> positions, String myId) {
        remotePlayers.clear();

        for (String id : positions.keySet()) {
            if (!id.equals(myId)) {
                remotePlayers.put(id, positions.get(id));
            }
        }

        repaint();
        checkPlayerCollision();
    }

    private void drawPlayer(Graphics2D g2d){
        g2d.setColor(Color.BLUE);
        g2d.fillRect(player.getPosX(), player.getPosY(), player.getTamanio(), player.getTamanio());
    }


    public void setLevel(int level) {
        this.level = level;
        backgroundLoader.load(level); // ✅ nuevo
        repaint();
        requestFocusInWindow();
    }
    public void startControlls(){
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e){
                switch (e.getKeyCode()){
                    case KeyEvent.VK_UP    -> moveUp();
                    case KeyEvent.VK_DOWN  -> moveDown();
                    case KeyEvent.VK_LEFT  -> moveLeft();
                    case KeyEvent.VK_RIGHT -> moveRight();
                }
            }
        });
    }

    public void move(int newX, int newY){
        if(changeLevel) return;

        if (!collision(newX, newY)) {

            player.setPosX(newX);
            player.setPosY(newY);

            updateCoins();

            if(onMove!=null) {
                onMove.accept(player.getPosX(), player.getPosY());
            }

            checkDoorCollision();

            repaint();
        }
        else {
            if (wallCollisionSound != null) wallCollisionSound.play();
            spawnPlayer();
            repaint();
        }
    }

    private void moveUp(){ move(player.getPosX(), player.getPosY() - 5); }
    private void moveDown(){ move(player.getPosX(), player.getPosY() + 5); }
    private void moveLeft(){ move(player.getPosX() - 5, player.getPosY()); }
    private void moveRight(){ move(player.getPosX() + 5, player.getPosY()); }

    private void updateCoins() {
        GameMap gameMap = mapsHandler.readMaps().get(level - 1);
        int px = player.getPosX();
        int py = player.getPosY();
        int ps = player.getTamanio();

        for (int i = 0; i < gameMap.getCoins().size(); i++) {
            Coin coin = gameMap.getCoins().get(i);

            boolean solapaX = px < coin.getX() + coin.getWidth() && px + ps > coin.getX();
            boolean solapaY = py < coin.getY() + coin.getHeight() && py + ps > coin.getY();

            if (solapaX && solapaY) {
                if (coinSound != null) coinSound.play();
                gameMap.getCoins().remove(i);
                //gameMap.addScore(coin.getPoints());
                i--;
            }
        }
    }

    private boolean collision(int newX, int newY){
        GameMap gameMap = mapsHandler.readMaps().get(level-1);

        for(Wall wall: gameMap.getWalls()){
            boolean chocaX = newX < wall.getX() + wall.getWidth() && newX + player.getTamanio() > wall.getX();
            boolean chocaY = newY < wall.getY() + wall.getHeight() && newY + player.getTamanio() > wall.getY();

            if(chocaX && chocaY){
                return true;
            }
        }
        return false;
    }

    private void checkDoorCollision() {
        GameMap gameMap = mapsHandler.readMaps().get(level - 1);

        int px = player.getPosX();
        int py = player.getPosY();
        int ps = player.getTamanio();

        gameMap.getDoors().stream()
                .filter(door -> {
                    boolean solapaX = px < door.getX() + door.getWidth() && px + ps > door.getX();
                    boolean solapaY = py < door.getY() + door.getHeight() && py + ps > door.getY();
                    return solapaX && solapaY;
                })
                .findFirst()
                .ifPresent(door -> {
                    changeLevel = true;

                    int totalLevels = mapsHandler.readMaps().size();
                    level = (level < totalLevels) ? level + 1 : 1;
                    setLevel(level);
                    spawnPlayer();

                    repaint();
                    requestFocusInWindow();

                    Timer t = new Timer(200, e -> changeLevel = false);
                    t.setRepeats(false);
                    t.start();
                });
    }

    private void checkPlayerCollision() {
        int px = player.getPosX();
        int py = player.getPosY();
        int ps = player.getTamanio();

        remotePlayers.forEach((id, pos) -> {
            boolean solapaX = px < pos[0] + ps && px + ps > pos[0];
            boolean solapaY = py < pos[1] + ps && py + ps > pos[1];

            if (solapaX && solapaY) {
                if (playerCollisionSound != null) playerCollisionSound.play();
            }
        });
    }

    private void spawnPlayer(){
        int[] pos = SPAWN_POSITIONS.get(level);

        if(pos != null){
            player.setPosX(pos[0]);
            player.setPosY(pos[1]);
        }
    }
}