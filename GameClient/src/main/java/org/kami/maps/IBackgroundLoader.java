package org.kami.maps;

import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * Define el contrato para la carga y renderizado del fondo de un mapa.
 * <p>
 * Las implementaciones de esta interfaz son responsables de gestionar
 * los recursos gráficos del fondo y dibujarlos en pantalla.
 * </p>
 */
public interface IBackgroundLoader {

    /**
     * Carga el fondo correspondiente a un nivel específico.
     *
     * @param level el número de nivel a cargar (generalmente basado en 1)
     */
    void load(int level);

    /**
     * Dibuja el fondo en el contexto gráfico proporcionado.
     *
     * @param g2d el contexto gráfico donde se renderizará el fondo
     * @param width el ancho del área de dibujo
     * @param height el alto del área de dibujo
     * @param observer el observador de la imagen utilizado para el renderizado
     */
    void draw(Graphics2D g2d, int width, int height, ImageObserver observer);
}
