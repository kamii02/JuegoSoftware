package org.kami.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kami.maps.ITexturedElement;
import org.kami.maps.mapelementsfactory.IMapElement;

/**
 * Representa una puerta dentro del mapa del juego.
 * Define su posición, dimensiones y la textura asociada para su representación visual.
 * Implementa interfaces que permiten tratarla como un elemento del mapa
 * y manejar su apariencia mediante texturas.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Door implements IMapElement, ITexturedElement {

    /**
     * Coordenada horizontal de la puerta.
     */
    private int x;

    /**
     * Coordenada vertical de la puerta.
     */
    private int y;

    /**
     * Ancho de la puerta.
     */
    private int width;

    /**
     * Altura de la puerta.
     */
    private int height;

    /**
     * Ruta de la textura asociada a la puerta.
     */
    private String texturePath;

    /**
     * Establece la ruta de la textura de la puerta.
     * @param path ruta de la textura
     */
    @Override
    public void setTexturePath(String path) {
        this.texturePath = path;
    }

    /**
     * Obtiene la ruta de la textura de la puerta.
     * @return ruta de la textura
     */
    @Override
    public String getTexturePath() {
        return texturePath;
    }

}