package org.kami.view;

import org.kami.maps.BackgroundLoader;
import org.kami.maps.CoinAnimator;
import org.kami.maps.IBackgroundLoader;
import org.kami.model.GameMap;
import org.kami.model.GameState;
import org.kami.model.Player;
import org.kami.config.ILayoutConfig;
import org.kami.maps.IMapsHandler;
import org.kami.model.Wall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import org.kami.audio.ISoundEffect;
import org.kami.controller.CollisionManager;
import org.kami.controller.ICollisionListener;

public class Layout extends JPanel implements ICollisionListener {

    private Player player;
    private IMapsHandler mapsHandler;
    private int level;
    private boolean gameReady = false;
    private boolean gameOver = false;
    private String winnerId = "";
    private Map<String, Integer> finalScores = new HashMap<>();
    private Runnable onWin;

    private final Map<String, int[]> remotePlayers = new ConcurrentHashMap<>();
    private final Map<String, Image> imageCache = new HashMap<>();
    private final Map<String, Color> playerColors = new ConcurrentHashMap<>();

    private Consumer<int[]> onMove;
    private final IBackgroundLoader backgroundLoader;
    private final CoinAnimator coinAnimator;
    private final CollisionManager collisionManager;


    public Layout(ILayoutConfig config, IMapsHandler mapsHandler, Player player) {
        this.player           = player;
        this.level            = 1;
        this.mapsHandler      = mapsHandler;
        this.collisionManager = new CollisionManager(mapsHandler, player);
        this.collisionManager.addListener(this);
        this.backgroundLoader = new BackgroundLoader(mapsHandler);

        setFocusable(true);
        requestFocusInWindow();
        startControlls();

        this.backgroundLoader.load(level);

        this.coinAnimator = new CoinAnimator(7000, this::repaint);
        coinAnimator.start();
    }

    public void setGameReady(boolean gameReady)         {this.gameReady = gameReady;}
    public void setOnMove(Consumer<int[]> onMove)       {this.onMove = onMove;}
    public void setCoinSound(ISoundEffect s)            {collisionManager.setCoinSound(s);}
    public void setWallCollisionSound(ISoundEffect s)   {collisionManager.setWallCollissionSound(s);}
    public void setPlayerCollisionSound(ISoundEffect s) {collisionManager.setPlayerCollissionSound(s);}
    public void setOnWin(Runnable onWin)                {this.onWin = onWin;}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if(gameOver){
            drawWinScreen(g2d);
            return;
        }
        if(!gameReady) {
            drawWaitingScreen(g2d);
            return;
        }
        backgroundLoader.draw(g2d, getWidth(), getHeight(), this);
        drawMapElements(g2d);
        drawRemotePlayers(g2d);
        drawPlayer(g2d);
        drawHUD(g2d);
    }

    private void drawWaitingScreen(Graphics2D g2d){
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0,getWidth(),getHeight());
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 30));
        g2d.drawString("Esperando jugadores...", 220, 380);
        g2d.setFont(new Font("Arial", Font.PLAIN, 18));
        g2d.drawString("Se necesitan al menos 2 jugadores para empezar.", 230, 420);
    }

    public void showWinScreen(String raw) {
        String[] parts     = raw.split(" ",3);
        this.winnerId      = parts[1];
        this.finalScores   = parseScores(parts[2]);
        this.gameOver      = true;
        repaint();
    }

    private Map<String, Integer> parseScores(String stateRaw) {
        Map<String, Integer> scores = new LinkedHashMap<>();
        GameState state = GameState.deserialize(stateRaw);
        state.getPositions().forEach((id, pos) -> scores.put(id, pos[3]));
        return scores;
    }

    private void drawWinScreen(Graphics2D g2d) {
        g2d.setColor(new Color(0, 0, 0, 200));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setColor(Color.YELLOW);
        g2d.setFont(new Font("Arial", Font.BOLD, 40));
        g2d.drawString("WINNER: " + winnerId, 220, 200);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        g2d.drawString("Final Scores", 320, 280);

        g2d.setFont(new Font("Arial", Font.PLAIN, 20));
        int y = 320;
        for (Map.Entry<String, Integer> entry : finalScores.entrySet()) {
            g2d.drawString(entry.getKey() + ":  " + entry.getValue() + " pts", 280, y);
            y += 35;
        }
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
            Image img = imageCache.computeIfAbsent(
                    coin.getTexturePath(),
                    path -> new ImageIcon(
                            getClass().getResource("/images/" + path)
                    ).getImage()
            );
            g2d.drawImage(
                    img,
                    coin.getX(),
                    coin.getY(),
                    coin.getWidth(),
                    coin.getHeight(),
                    null
            );
        });
    }

    private void drawRemotePlayers(Graphics2D g2d) {

        remotePlayers.forEach((id, pos) -> {
            if(pos[2] != level) return;
            Color color = playerColors.computeIfAbsent(id, k ->{
               Random randC = new Random(k.hashCode());
               return new Color(randC.nextInt(256), randC.nextInt(256), randC.nextInt(256));
            });
            g2d.setColor(color);
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
    }

    private void drawPlayer(Graphics2D g2d){
        g2d.setColor(Color.BLUE);
        g2d.fillRect(player.getPosX(), player.getPosY(), player.getTamanio(), player.getTamanio());
    }

    private void drawHUD(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString("Score: " + player.getScore(), 650, 40);
    }


    public void setLevel(int level) {
        this.level = level;
        backgroundLoader.load(level);
        repaint();
        requestFocusInWindow();
    }

    public void startControlls(){
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e){
                switch (e.getKeyCode()){
                    case KeyEvent.VK_UP    -> move(player.getPosX(), player.getPosY() - 5);
                    case KeyEvent.VK_DOWN  -> move(player.getPosX(), player.getPosY() + 5);
                    case KeyEvent.VK_LEFT  -> move(player.getPosX() - 5, player.getPosY());
                    case KeyEvent.VK_RIGHT -> move(player.getPosX() + 5, player.getPosY());
                }
            }
        });
    }

    public void move(int newX, int newY) {
        if (!gameReady || gameOver) return;
        if (collisionManager.isChangeLevel()) return;

        if (!collisionManager.collidesWithWall(newX, newY, level)) {
            player.setPosX(newX);
            player.setPosY(newY);

            collisionManager.checkCoinCollision(level);

            if (onMove != null) onMove.accept(new int[]{player.getPosX(), player.getPosY(), level, player.getScore()});

            collisionManager.checkDoorCollision(
                    level,
                    mapsHandler.readMaps().size(),
                    newLevel -> setLevel(newLevel),
                    () -> {
                        if (onMove != null)
                            onMove.accept(new int[]{player.getPosX(), player.getPosY(), level});
                        repaint();
                        requestFocusInWindow();
                    }
            );

            repaint();
        } else {
            collisionManager.onWallHit();
            collisionManager.spawnPlayer(level);
            repaint();
        }
    }

    @Override
    public void onWallHit() {}

    @Override
    public void onCoinCollected() {}

    @Override
    public void onDoorEntered(int newLevel) {
        setLevel(newLevel);
        if(onMove != null)onMove.accept(new int[]{player.getPosX(), player.getPosY(), newLevel, player.getScore()});
        repaint();
        requestFocusInWindow();
    }

    @Override
    public void onGameWon() {
        if(onWin != null) onWin.run();
    }
}