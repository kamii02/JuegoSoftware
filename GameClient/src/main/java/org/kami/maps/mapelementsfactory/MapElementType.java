package org.kami.maps.mapelementsfactory;

/**
 * Enum que define los diferentes tipos de elementos que pueden existir en el mapa.
 * <p>
 * Cada valor representa una categoría de objeto que puede ser creado
 * mediante la {@link IMapElementFactory}.
 * </p>
 */
public enum MapElementType {

    /**
     * Representa una pared u obstáculo sólido en el mapa.
     */
    WALL,

    /**
     * Representa una moneda coleccionable dentro del mapa.
     */
    COIN,

    /**
     * Representa una puerta, posiblemente utilizada para transiciones
     * entre niveles o áreas.
     */
    DOOR,

    /**
     * Representa el fondo del mapa o un elemento decorativo no interactivo.
     */
    BACKGROUND,

}
