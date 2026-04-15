package org.kami.maps.mapelementsfactory;

import org.kami.model.Coin;
import org.kami.model.Door;
import org.kami.model.Wall;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Implementación de {@link IMapElementFactory} que utiliza un registro interno
 * basado en un {@link Map} para crear instancias de elementos del mapa.
 * <p>
 * Esta clase aplica el patrón Factory junto con referencias a constructores
 * ({@link Supplier}) para desacoplar la creación de objetos concretos.
 * </p>
 */
public class MapElementFactory implements IMapElementFactory{

    /**
     * Mapa que asocia cada {@link MapElementType} con un proveedor
     * ({@link Supplier}) capaz de crear instancias de {@link IMapElement}.
     */
    private final Map<MapElementType, Supplier<IMapElement>> factory;

    /**
     * Constructor que inicializa el registro de tipos de elementos de mapa
     * y sus respectivos proveedores de instancia.
     */
    public MapElementFactory(){
        this.factory = new HashMap<>();
        factory.put(MapElementType.WALL, Wall::new);
        factory.put(MapElementType.COIN, Coin::new);
        factory.put(MapElementType.DOOR, Door::new);
    }

    /**
     * Crea un elemento de mapa basado en el tipo especificado.
     *
     * @param type el tipo de elemento de mapa a crear
     * @return una nueva instancia de {@link IMapElement} si el tipo está registrado,
     *         o {@code null} si no existe un proveedor para ese tipo
     */
    public IMapElement createMapElement(MapElementType type){
        Supplier<IMapElement> supplier = factory.get(type);
        if (supplier == null) return null;
        return supplier.get();
    }
}
