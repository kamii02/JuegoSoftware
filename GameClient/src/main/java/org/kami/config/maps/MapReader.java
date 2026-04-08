package org.kami.config.maps;

import org.kami.config.ILayoutConfig;
import org.kami.view.maps.elements.*;
import org.kami.view.maps.mapelementsfactory.*;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
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

            while((line = br.readLine()) != null){

                if(line.isBlank()) continue;

                String[] cols = line.split(",");
                MapElementType type = MapElementType.valueOf(cols[0]);

                if(type == MapElementType.BACKGROUND){
                    gameMap.setBackgroundPath(cols[1]);
                    continue;
                }

                IMapElement element = factory.createMapElement(type);

                element.setX(Integer.parseInt(cols[1]));
                element.setY(Integer.parseInt(cols[2]));
                element.setWidth(Integer.parseInt(cols[3]));
                element.setHeight(Integer.parseInt(cols[4]));

                if (cols.length > 5 && element instanceof ITexturedElement textured) {
                    textured.setTexturePath(cols[5]);
                }
                switch (type){
                    case WALL -> walls.add((Wall)element);
                    case COIN -> coins.add((Coin)element);
                    case DOOR -> doors.add((Door)element);
                }
            }

            gameMap.setWalls(walls);
            gameMap.setCoins(coins);
            gameMap.setDoors(doors);

        } catch (IOException e){
            System.out.println("Error leyendo el mapa: " + e.getMessage());
        }

        return gameMap;
    }

    @Override
    public List<GameMap> readMaps() {
        List<GameMap> loadedMaps = null;

        try {
            URI mapsFolderURI = getClass()
                    .getClassLoader()
                    .getResource(layoutConfig.getMapFolder())
                    .toURI();

            File folder = new File(mapsFolderURI);

            List<String> maps = Arrays.stream(folder.listFiles())
                    .filter(File::isFile)
                    .map(File::getName)
                    .filter(n -> n.endsWith(".csv"))
                    .sorted(Comparator.comparingInt(n ->
                            Integer.parseInt(n.replace(".csv", ""))
                    ))
                    .collect(Collectors.toList());

            loadedMaps = maps.stream()
                    .map(m -> layoutConfig.getMapFolder() + "/" + m)
                    .map(this::readMap)
                    .toList();

        } catch (URISyntaxException e) {
            System.out.println("Error leyendo carpeta de mapas");
        }

        return loadedMaps;
    }
}