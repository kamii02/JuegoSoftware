package org.kami.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kami.maps.ITexturedElement;
import org.kami.maps.mapelementsfactory.IMapElement;

/**
 * Representa un muro dentro del mapa del juego.
 * Define su posición, dimensiones y la textura utilizada
 * para su representación visual.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wall implements IMapElement, ITexturedElement {

    /**
     * Coordenada horizontal del muro.
     */
    private int x;

    /**
     * Coordenada vertical del muro.
     */
    private int y;

    /**
     * Altura del muro.
     */
    private int height;

    /**
     * Ancho del muro.
     */
    private int width;

    /**
     * Ruta de la textura asociada al muro.
     */
    private String texturePath;

    /**
     * Establece la ruta de la textura del muro.
     * @param path ruta de la textura
     */
    @Override
    public void setTexturePath(String path) {
        this.texturePath = path;
    }

    /**
     * Obtiene la ruta de la textura del muro.
     * @return ruta de la textura
     */
    @Override
    public String getTexturePath() {
        return texturePath;
    }

}