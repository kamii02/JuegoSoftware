package org.kami.maps;

import org.kami.model.GameMap;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.net.URL;

public class BackgroundLoader implements IBackgroundLoader {

    private final IMapsHandler mapsHandler;
    private Image backgroundGif;
    private String currentBackgroundPath = "";

    public BackgroundLoader(IMapsHandler mapsHandler) {
        this.mapsHandler = mapsHandler;
    }

    @Override
    public void load(int level) {
        GameMap gameMap = mapsHandler.readMaps().get(level - 1);
        String path = gameMap.getBackgroundPath();

        if (path != null && !path.equals(currentBackgroundPath)) {
            currentBackgroundPath = path;

            URL gifUrl = getClass().getResource("/images/gif/" + path);

            if (gifUrl != null) {
                backgroundGif = new ImageIcon(gifUrl).getImage();
                System.out.println("Fondo cargado: " + path);
            } else {
                backgroundGif = null;
                System.out.println(" No se encontró: " + path);
            }
        }
    }

    @Override
    public void draw(Graphics2D g2d, int width, int height, ImageObserver observer) {
        if (backgroundGif != null) {
            g2d.drawImage(backgroundGif, 0, 0, width, height, observer);
        } else {
            g2d.setColor(new Color(173, 216, 230));
            g2d.fillRect(0, 0, width, height);
        }
    }
}