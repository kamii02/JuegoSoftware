package org.kami;

import org.kami.client.SwingRenderer;
import org.kami.config.*;
import org.kami.config.element.Player;
import org.kami.client.NetworkManager;
import org.kami.config.maps.IMapsHandler;
import org.kami.config.maps.MapReader;
import org.kami.view.Layout;
import org.kami.view.MainWindow;
import org.kami.audio.IMusicPlayer;
import org.kami.audio.MusicPlayer;

import org.kami.audio.ISoundEffect;
import org.kami.audio.SoundEffect;

public class Main {
    public static void main(String[] args) throws Exception {
        IConfigReader reader = new PropertiesManager("application.properties");
        ILayoutConfig layoutConfig = new LayoutConfig(reader);


// ─── MÚSICA GLOBAL ──────────────────────────────────────────────────
        // Leemos la ruta del .wav desde application.properties (clave "music.path").
        // Usamos IConfigReader (ya instanciado arriba) para mantener el patrón:
        // toda la configuración pasa por la misma abstracción.
        String musicPath = reader.getString("music.path");

        // Creamos el reproductor a través de la interfaz IMusicPlayer,
        // exactamente igual que el resto del proyecto (ILayoutConfig, INetworkConnection…).
        IMusicPlayer musicPlayer = new MusicPlayer(musicPath);

        // Iniciamos la música antes de mostrar la ventana.
        // A partir de aquí suena en bucle continuo en un hilo daemon de Java Sound.
        musicPlayer.play();


        ISoundEffect coinSound            = new SoundEffect(reader.getString("sound.coin"));
        ISoundEffect wallCollisionSound   = new SoundEffect(reader.getString("sound.wall"));
        ISoundEffect playerCollisionSound = new SoundEffect(reader.getString("sound.player"));


        //Player player = creator.CharacterBuilder(800, 800);
        //Maps config
        IMapsHandler mapsHandler = new MapReader(layoutConfig);
        Player player = new Player(100,700,30);
        Layout l = new Layout(layoutConfig, mapsHandler, player);
        MainWindow mainWindow = new MainWindow(l, layoutConfig);
        mainWindow.setVisible(true);

        /*Thread.sleep(5000);
        l.setLevel(2);
        Thread.sleep(5000);
        l.setLevel(3);*/

        // --- Arranque de red (3 líneas, nunca más) ---
        IConfigReader  configReader    = new PropertiesManager("application.properties");
        IUDPConfig     config          = new UDPConfig(configReader);
        NetworkManager network         = new NetworkManager(config, new SwingRenderer(l, config.getPlayerId()));

        network.connect();

        //Conectamos la logica independiente que conecta el movimiento con la red
        l.setOnMove(data -> network.sendPosition(data[0], data[1], data[2]));
        l.setCoinSound(coinSound);
        l.setWallCollisionSound(wallCollisionSound);
        l.setPlayerCollisionSound(playerCollisionSound);

        // Cuando el juego cierre:
        //network.disconnect();

    }
}
