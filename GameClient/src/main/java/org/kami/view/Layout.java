package org.kami.view;

import org.kami.config.element.CharacterStatus;
import org.kami.config.element.Player;
import org.kami.config.ILayoutConfig;
import org.kami.config.maps.IMapsHandler;
import org.kami.view.maps.elements.GameMap;
import org.kami.view.maps.elements.Wall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

//Recibe ancho y alto y dibuja
public class Layout extends JPanel {

    private Player player;
    private IMapsHandler mapsHandler;
    private int level;


    public Layout(ILayoutConfig config, IMapsHandler mapsHandler, Player player){
        setFocusable(true);
        requestFocusInWindow();
        startControlls();
        this.player = player;
        this.level = 1;
        this.mapsHandler = mapsHandler;


        /*
        int width = config.getWidth();
        int height = config.getHeight();

        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Dimensiones inválidas");
        }

        setPreferredSize(new Dimension(width, height));*/
        setBackground(new Color(173, 216, 230));
    }

    /**
     * Método de Swing encargado de dibujar el contenido del panel.
     * Muestra el último estado de la bola y registra el evento.
     *
     * @param g objeto Graphics utilizado para realizar el dibujo
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //if (player == null) return;
        Graphics2D g2d = (Graphics2D) g;
        drawMapElements(g2d);
        drawPlayer(g2d);

    }

    /**
     * Configura opciones de renderizado para mejorar la calidad del dibujo.
     *
     * @param g2d objeto Graphics2D usado para renderizar la imagen
     */
    private void aplicarCalidadRenderizado(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    }



    private void drawMapElements(Graphics2D g2d) {
        GameMap gameMap = mapsHandler.readMaps().get(level-1);
        gameMap.getWalls().forEach(wall -> {
            g2d.setColor(Color.BLACK);
            g2d.fillRect(wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight());
        });
        gameMap.getCoins().forEach(coin -> {
            g2d.setColor(Color.PINK);
            g2d.fillOval(coin.getX(), coin.getY(), coin.getWidth(), coin.getHeight());
            g2d.setColor(Color.BLACK);
            g2d.drawOval(coin.getX(), coin.getY(), coin.getWidth(), coin.getHeight());
        });
        /*for(Wall wall : gameMap.getWalls()){
            g2d.setColor(Color.BLACK);
            g2d.fillRect(wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight());
        }
        System.out.println("Pintando");*/
    }

    private void drawPlayer(Graphics2D g2d){
        Random rand = new Random();
        g2d.setColor(new Color(
                rand.nextInt(256),
                rand.nextInt(256),
                rand.nextInt(256)
        ));
        g2d.fillRect(player.getPosX(), player.getPosY(), player.getTamanio(), player.getTamanio());
    }

    public void setLevel(int level){
        this.level = level;
        repaint();
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
    private void moveUp(){
        int newY = this.player.getPosY() - 5;
        if (!collision(this.player.getPosX(), newY)) {
            this.player.setPosY(newY);
            repaint();
        }

    }
    private void moveDown(){
        int newY = this.player.getPosY() + 5;
        if (!collision(this.player.getPosX(), newY)) {
            this.player.setPosY(newY);
            repaint();
        }
    }
    private void moveLeft(){
        int newX = this.player.getPosX() - 5;
        if (!collision(newX, this.player.getPosY())) {
            this.player.setPosX(newX);
            repaint();
        }
    }
    private void moveRight(){
        int newX = this.player.getPosX() + 5;
        if (!collision(newX, this.player.getPosY())) {
            this.player.setPosX(newX);
            repaint();
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


}

