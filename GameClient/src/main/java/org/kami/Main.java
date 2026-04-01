package org.kami;

import org.kami.client.AppConfig;
import org.kami.client.ConsoleRenderer;
import org.kami.client.NetworkManager;

public class Main {
    public static void main(String[] args) throws Exception {

        // --- Arranque de red (3 líneas, nunca más) ---
        AppConfig      config  = new AppConfig();
        NetworkManager network = new NetworkManager(config, new ConsoleRenderer());
        network.connect();

        // --- Aquí va toda tu lógica del juego ---
        int x = 100, y = 100;

        // Ejemplo: cuando tu personaje se mueva, llamas:
        network.sendPosition(x, y);

        // Cuando el juego cierre:
        network.disconnect();
    }
}
