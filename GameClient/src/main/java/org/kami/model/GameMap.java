package org.kami.model;

import java.util.List;

/**
 * Representa el mapa del juego.
 * Contiene los elementos que lo componen como muros, monedas y puertas,
 * además de la ruta del fondo visual.
 */
public class GameMap {

    /**
     * Lista de muros presentes en el mapa.
     */
    private List<Wall> walls;

    /**
     * Lista de monedas disponibles en el mapa.
     */
    private List<Coin> coins;

    /**
     * Lista de puertas presentes en el mapa.
     */
    private List<Door> doors;

    /**
     * Ruta de la imagen de fondo del mapa.
     */
    private String backgroundPath;

    /**
     * Obtiene la lista de muros del mapa.
     * @return lista de muros
     */
    public List<Wall> getWalls() {
        return walls;
    }

    /**
     * Establece la lista de muros del mapa.
     * @param walls lista de muros
     */
    public void setWalls(List<Wall> walls) {
        this.walls = walls;
    }

    /**
     * Obtiene la lista de monedas del mapa.
     * @return lista de monedas
     */
    public List<Coin> getCoins() {
        return coins;
    }

    /**
     * Establece la lista de monedas del mapa.
     * @param coins lista de monedas
     */
    public void setCoins(List<Coin> coins) {
        this.coins = coins;
    }

    /**
     * Obtiene la lista de puertas del mapa.
     * @return lista de puertas
     */
    public List<Door> getDoors() {
        return doors;
    }

    /**
     * Establece la lista de puertas del mapa.
     * @param doors lista de puertas
     */
    public void setDoors(List<Door> doors) {
        this.doors = doors;
    }

    /**
     * Obtiene la ruta de la imagen de fondo del mapa.
     * @return ruta del fondo
     */
    public String getBackgroundPath() {
        return backgroundPath;
    }

    /**
     * Establece la ruta de la imagen de fondo del mapa.
     * @param backgroundPath ruta del fondo
     */
    public void setBackgroundPath(String backgroundPath) {
        this.backgroundPath = backgroundPath;
    }
}