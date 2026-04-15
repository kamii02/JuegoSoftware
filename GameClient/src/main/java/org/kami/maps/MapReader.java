package org.kami.maps;

import org.kami.config.ILayoutConfig;
import org.kami.model.Coin;
import org.kami.model.Door;
import org.kami.model.GameMap;
import org.kami.model.Wall;
import org.kami.maps.mapelementsfactory.*;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementación de {@link IMapsHandler} encargada de leer y construir
 * los mapas del juego a partir de archivos CSV.
 * <p>
 * Esta clase utiliza el patrón Singleton para garantizar una única instancia,
 * y mantiene una caché de los mapas ya cargados para evitar lecturas repetidas.
 * </p>
 */
public class MapReader implements IMapsHandler {

    /**
     * Instancia única de la clase (Singleton).
     */
    private static volatile MapReader instance;

    /**
     * Configuración de layout que proporciona la ruta de los mapas.
     */
    private ILayoutConfig layoutConfig;

    /**
     * Caché de mapas ya cargados.
     */
    private List<GameMap> cachedMaps = null;

    /**
     * Constructor privado para el patrón Singleton.
     *
     * @param layoutConfig configuración del layout
     */
    private MapReader(ILayoutConfig layoutConfig) {
        this.layoutConfig = layoutConfig;
    }

    /**
     * Obtiene la instancia única de {@link MapReader}.
     * <p>
     * Implementa inicialización perezosa con doble verificación (double-checked locking).
     * </p>
     *
     * @param layoutConfig configuración del layout
     * @return instancia única de {@link MapReader}
     */
    public static MapReader getInstance(ILayoutConfig layoutConfig) {
        if(instance == null){
            synchronized (MapReader.class){
                if(instance == null){
                    instance = new MapReader(layoutConfig);
                }
            }
        }
        return instance;
    }

    /**
     * Lee un archivo CSV y construye un {@link GameMap}.
     *
     * @param path ruta del archivo dentro del classpath
     * @return el mapa construido a partir del archivo
     */
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

    /**
     * Lee todos los mapas disponibles desde la carpeta configurada.
     * <p>
     * Los mapas se cargan una sola vez y se almacenan en caché para
     * futuras llamadas.
     * </p>
     *
     * @return lista de {@link GameMap} ordenados por nombre de archivo
     */
    @Override
    public List<GameMap> readMaps() {
        if(cachedMaps != null) return cachedMaps;
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

        cachedMaps = loadedMaps;
        return cachedMaps;
    }
}