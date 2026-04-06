package org.kami.config.maps;

import org.kami.config.ILayoutConfig;
import org.kami.config.LayoutConfig;
import org.kami.view.maps.elements.Coin;
import org.kami.view.maps.elements.Door;
import org.kami.view.maps.elements.GameMap;
import org.kami.view.maps.elements.Wall;
import org.kami.view.maps.mapelementsfactory.IMapElement;
import org.kami.view.maps.mapelementsfactory.IMapElementFactory;
import org.kami.view.maps.mapelementsfactory.MapElementFactory;
import org.kami.view.maps.mapelementsfactory.MapElementType;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MapReader implements IMapsHandler {

    private ILayoutConfig layoutConfig;

    public MapReader(ILayoutConfig layoutConfig){
        this.layoutConfig = layoutConfig;
    }

    private GameMap readMap(String path) {
        IMapElementFactory factory = new MapElementFactory();
        GameMap gameMap = new GameMap();
        List<Wall> walls = new ArrayList<>();
        List<Coin> coins = new ArrayList<>();
        List<Door> doors = new ArrayList<>();
        try(InputStream input = getClass().getClassLoader().getResourceAsStream(path)) {
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            String line;
            while((line = br.readLine())!=null){
                String[] cols = line.split(",");
                MapElementType type = MapElementType.valueOf(cols[0]);
                IMapElement element = factory.createMapElement(type);
                element.setX(Integer.parseInt(cols[1]));
                element.setY(Integer.parseInt(cols[2]));
                element.setWidth(Integer.parseInt(cols[3]));
                element.setHeight(Integer.parseInt(cols[4]));
                switch (type){
                    case MapElementType.WALL -> walls.add((Wall)element);
                    case MapElementType.COIN -> coins.add((Coin)element);
                    case MapElementType.DOOR -> doors.add((Door)element);
                }
            }
            gameMap.setWalls(walls);
            gameMap.setCoins(coins);
        }catch (IOException e){
            System.out.println("Error critico al leer las caracteristicas del mapa:  "+ e.getMessage());
        }
        return gameMap;
    }

    @Override
    public List<GameMap> readMaps() {
        List<GameMap> loadedMaps = null;
        try {
            URI mapsFolderURI = getClass().getClassLoader().getResource(layoutConfig.getMapFolder()).toURI();
            File folder = new File(mapsFolderURI);
            List<String> maps = Arrays.stream(folder.listFiles())
                    .filter(File::isFile)
                    .map(File::getName)
                    .sorted(Comparator.comparingInt(n-> Integer.parseInt(n.replace(".csv", ""))))
                    .collect(Collectors.toList());
            loadedMaps = maps.stream().map(m -> {
                String path = layoutConfig.getMapFolder()+"/"+m;
                return this.readMap(path);
            }).toList();

        } catch (URISyntaxException e) {
            System.out.println("Ocurrio un error leyendo el directorio de mapas");
        }
        return loadedMaps;

    }
}
