package org.kami;

import org.kami.config.element.Player;
import org.kami.client.AppConfig;
import org.kami.client.ConsoleRenderer;
import org.kami.client.NetworkManager;
import org.kami.config.IConfigReader;
import org.kami.config.ILayoutConfig;
import org.kami.config.LayoutConfig;
import org.kami.config.PropertiesManager;
import org.kami.config.maps.IMapsHandler;
import org.kami.config.maps.MapReader;
import org.kami.factory.ImageBallCreator;
import org.kami.view.Layout;
import org.kami.view.MainWindow;
import org.kami.view.maps.elements.GameMap;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        IConfigReader reader = new PropertiesManager("application.properties");
        ILayoutConfig layoutConfig = new LayoutConfig(reader);

        //Player player = creator.CharacterBuilder(800, 800);
        //Maps config
        IMapsHandler mapsHandler = new MapReader(layoutConfig);
        Player player = new Player(100,700,30);
        Layout l = new Layout(layoutConfig, mapsHandler, player);
        MainWindow mainWindow = new MainWindow(l, layoutConfig);
        mainWindow.setVisible(true);

        Thread.sleep(5000);
        l.setLevel(2);
        Thread.sleep(5000);
        l.setLevel(3);
        Thread.sleep(5000);
        l.setLevel(4);

        // --- Arranque de red (3 líneas, nunca más) ---
        AppConfig      config  = new AppConfig();
        NetworkManager network = new NetworkManager(config, new ConsoleRenderer());

        network.connect();

        // --- Aquí va toda tu lógica del juego ---
        int x = 100, y = 100;
        // Ejemplo: cuando tu personaje se mueva, llamas:
        network.sendPosition(x, y);

        // Cuando el juego cierre:
        //network.disconnect();

    }
}
