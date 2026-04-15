package org.kami.maps;

import org.kami.model.GameMap;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.net.URL;

/**
 * Implementación de {@link IBackgroundLoader} encargada de cargar y dibujar
 * el fondo del juego a partir de la información de los mapas.
 * <p>
 * Esta clase obtiene la ruta del fondo desde un {@link GameMap} y carga
 * la imagen (GIF) correspondiente desde los recursos del proyecto.
 * También evita recargar el fondo si ya se encuentra cargado.
 * </p>
 */
public class BackgroundLoader implements IBackgroundLoader {

    /**
     * Manejador de mapas utilizado para obtener la información de los niveles.
     */
    private final IMapsHandler mapsHandler;

    /**
     * Imagen del fondo actualmente cargado.
     */
    private Image backgroundGif;

    /**
     * Ruta del fondo actualmente cargado, utilizada para evitar recargas innecesarias.
     */
    private String currentBackgroundPath = "";


    /**
     * Constructor que recibe el manejador de mapas.
     *
     * @param mapsHandler el manejador encargado de proporcionar los mapas del juego
     */
    public BackgroundLoader(IMapsHandler mapsHandler) {
        this.mapsHandler = mapsHandler;
    }


    /**
     * Carga el fondo correspondiente al nivel especificado.
     * <p>
     * Si el fondo ya fue cargado previamente, no se vuelve a cargar.
     * </p>
     *
     * @param level el número de nivel (basado en 1)
     */
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

    /**
     * Dibuja el fondo en el contexto gráfico proporcionado.
     * <p>
     * Si existe un fondo cargado, se renderiza escalado al tamaño indicado.
     * En caso contrario, se dibuja un fondo de color sólido por defecto.
     * </p>
     *
     * @param g2d el contexto gráfico donde se dibujará el fondo
     * @param width el ancho del área de dibujo
     * @param height el alto del área de dibujo
     * @param observer el observador de la imagen
     */
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