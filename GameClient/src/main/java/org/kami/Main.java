package org.kami;

import org.kami.config.element.Player;
import org.kami.client.AppConfig;
import org.kami.client.ConsoleRenderer;
import org.kami.client.NetworkManager;
import org.kami.config.IConfigReader;
import org.kami.config.ILayoutConfig;
import org.kami.config.LayoutConfig;
import org.kami.config.PropertiesManager;
import org.kami.factory.ImageBallCreator;
import org.kami.view.Layout;
import org.kami.view.MainWindow;

public class Main {
    public static void main(String[] args) throws Exception {
        IConfigReader reader = new PropertiesManager("application.properties");
        ILayoutConfig layoutConfig = new LayoutConfig(reader);
        ImageBallCreator creator = new ImageBallCreator("bola.png", layoutConfig);
        Player player = creator.CharacterBuilder(800, 800);

        Layout l = new Layout(layoutConfig, player);
        new MainWindow(l);
        l.repaint();

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
