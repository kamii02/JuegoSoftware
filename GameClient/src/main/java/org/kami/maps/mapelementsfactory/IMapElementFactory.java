package org.kami.maps.mapelementsfactory;

/**
 * Define el contrato para la creación de elementos de mapa.
 * <p>
 * Implementa el patrón de diseño Factory, permitiendo crear distintos
 * tipos de {@link IMapElement} según el tipo especificado.
 * </p>
 */
public interface IMapElementFactory {

    /**
     * Crea una instancia de un elemento de mapa según el tipo proporcionado.
     *
     * @param type el tipo de elemento de mapa a crear
     * @return una instancia de {@link IMapElement} correspondiente al tipo indicado
     * @throws IllegalArgumentException si el tipo proporcionado no es válido o no está soportado
     */
    IMapElement createMapElement(MapElementType type);
}
