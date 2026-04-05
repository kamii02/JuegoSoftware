package org.kami.view.maps.mapelementsfactory;

import org.kami.view.maps.elements.Coin;
import org.kami.view.maps.elements.Wall;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MapElementFactory implements IMapElementFactory{
    private final Map<MapElementType, Supplier<IMapElement>> factory;

    public MapElementFactory(){
        this.factory = new HashMap<>();
        factory.put(MapElementType.WALL, Wall::new);
        factory.put(MapElementType.COIN, Coin::new);
    }

    public IMapElement createMapElement(MapElementType type){
        return factory.get(type).get();
    }
}
