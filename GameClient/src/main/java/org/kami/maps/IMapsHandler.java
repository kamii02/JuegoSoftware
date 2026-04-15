package org.kami.maps;

import org.kami.model.GameMap;

import java.util.List;

/**
 * Define el contrato para la gestión y lectura de mapas del juego.
 * <p>
 * Las implementaciones de esta interfaz son responsables de cargar,
 * almacenar y proporcionar acceso a los diferentes {@link GameMap}.
 * </p>
 */
public interface IMapsHandler {

    /**
     * Obtiene la lista de mapas disponibles en el juego.
     *
     * @return una lista de objetos {@link GameMap} que representan los niveles del juego
     */
    List<GameMap> readMaps();
}
