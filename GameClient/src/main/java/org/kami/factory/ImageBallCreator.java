package org.kami.factory;

import org.kami.config.ILayoutConfig;
import org.kami.config.element.ConfigPlayer;
import org.kami.config.element.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ImageBallCreator extends CharacterBuilder{

    /** Ruta del archivo de imagen que se utilizará para la bola */
    private final String rutaImagen;
    private ILayoutConfig layoutConfig;

    /**
     * Constructor que recibe la ruta de la imagen de la bola.
     *
     * @param rutaImagen ubicación del archivo de imagen
     */
    public ImageBallCreator(String rutaImagen, ILayoutConfig layoutConfig) {
        this.layoutConfig = layoutConfig;
        this.rutaImagen = rutaImagen;
    }

    @Override
    protected Player createCharacters(int ScreenWidth, int ScreenHeight) {
        Image imagen = cargarImagen();

        int size = layoutConfig.getPlayerSize();

        int margin = 3;

        int x = ScreenWidth - size - margin;
        int y = ScreenHeight - size - margin;

        return new Player(
                x,
                y,
                size,
                imagen,
                ScreenWidth,
                ScreenHeight
        );
    }

    private Image cargarImagen() {
        try {
            File archivo = new File(rutaImagen);
            if (!archivo.exists()) {
                System.err.println("[CharacterBuilder] Imagen no encontrada: " + rutaImagen);
                return null;
            }
            return ImageIO.read(archivo);
        } catch (IOException e) {
            System.err.println("[CharacterBuilder] Error al cargar imagen: " + e.getMessage());
            return null;
        }
    }


}
