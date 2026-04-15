package org.kami.maps.mapelementsfactory;
/**
 * Representa un elemento dentro de un mapa con propiedades básicas
 * de posición y tamaño.
 * <p>
 * Esta interfaz define los métodos necesarios para obtener y modificar
 * las coordenadas (x, y) y las dimensiones (ancho y alto) de un elemento.
 * </p>
 */
public interface IMapElement {
    /**
     * Obtiene la coordenada X del elemento en el mapa.
     *
     * @return la posición horizontal del elemento
     */
    int getX();

    /**
     * Obtiene la coordenada Y del elemento en el mapa.
     *
     * @return la posición vertical del elemento
     */
    int getY();

    /**
     * Obtiene la altura del elemento.
     *
     * @return la altura del elemento
     */
    int getHeight();

    /**
     * Obtiene el ancho del elemento.
     *
     * @return el ancho del elemento
     */
    int getWidth();

    /**
     * Establece la coordenada X del elemento en el mapa.
     *
     * @param x la nueva posición horizontal
     */
    void setX(int x);

    /**
     * Establece la coordenada Y del elemento en el mapa.
     *
     * @param y la nueva posición vertical
     */
    void setY(int y);

    /**
     * Establece la altura del elemento.
     *
     * @param height la nueva altura del elemento
     */
    void setHeight(int height);

    /**
     * Establece el ancho del elemento.
     *
     * @param width el nuevo ancho del elemento
     */
    void setWidth(int width);
}
