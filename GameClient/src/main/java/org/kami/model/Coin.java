package org.kami.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kami.maps.ITexturedElement;
import org.kami.maps.mapelementsfactory.IMapElement;

/**
 * Representa una moneda dentro del mapa del juego.
 * Contiene información sobre su posición, tamaño, valor en puntos,
 * tiempo de aparición, duración y textura asociada.
 * Implementa interfaces para comportarse como elemento del mapa
 * y para manejar una textura visual.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coin implements IMapElement, ITexturedElement {

    /**
     * Coordenada horizontal de la moneda.
     */
    private int x;

    /**
     * Coordenada vertical de la moneda.
     */
    private int y;

    /**
     * Altura de la moneda.
     */
    private int height;

    /**
     * Ancho de la moneda.
     */
    private int width;

    /**
     * Cantidad de puntos que otorga la moneda al ser recolectada.
     */
    private int points = 20;

    /**
     * Momento en el que la moneda aparece en el mapa, en milisegundos.
     */
    private long spawnTime;

    /**
     * Tiempo que la moneda permanece activa antes de expirar, en milisegundos.
     */
    private long duration = 7000;

    /**
     * Ruta de la textura asociada a la moneda.
     */
    private String texturePath;

    /**
     * Determina si la moneda ha expirado en función del tiempo actual.
     * @return true si el tiempo transcurrido desde su aparición supera la duración establecida, false en caso contrario
     */
    public boolean isExpired() {
        return System.currentTimeMillis() - spawnTime > duration;
    }

    /**
     * Establece la ruta de la textura de la moneda.
     * @param path ruta de la textura
     */
    @Override
    public void setTexturePath(String path) {
        this.texturePath = path;
    }

    /**
     * Obtiene la ruta de la textura de la moneda.
     * @return ruta de la textura
     */
    @Override
    public String getTexturePath() {
        return texturePath;
    }
}