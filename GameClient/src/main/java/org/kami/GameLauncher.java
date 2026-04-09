package org.kami;

import org.kami.client.SwingRenderer;
import org.kami.config.*;
import org.kami.config.element.Player;
import org.kami.client.NetworkManager;
import org.kami.config.maps.IMapsHandler;
import org.kami.config.maps.MapReader;
import org.kami.view.Layout;
import org.kami.view.MainWindow;
import org.kami.audio.*;

/**
 * Clase encargada de inicializar y lanzar el juego completo.
 *
 * Se encarga de configurar todos los componentes principales como
 * la configuración general, la música, los efectos de sonido,
 * el mapa, el jugador, la ventana principal y la conexión de red.
 *
 * Permite iniciar el juego utilizando un identificador dinámico
 * de jugador recibido desde la interfaz gráfica.
 *
 * @author Camila Prada
 * @version 1.0.0
 */
public class GameLauncher {

    /**
     * Método que inicia la ejecución del juego.
     *
     * Carga la configuración desde el archivo properties, inicializa
     * la música y los efectos de sonido, construye el mapa y el jugador,
     * crea la ventana principal y establece la conexión de red.
     *
     * También configura los eventos de movimiento del jugador y los
     * sonidos asociados a las interacciones dentro del juego.
     *
     * @param playerName Nombre del jugador ingresado desde la interfaz.
     *                   Se utiliza como identificador único en la red.
     */
    public static void startGame(String playerName) {
        try {
            IConfigReader reader = new PropertiesManager("application.properties");
            ILayoutConfig layoutConfig = new LayoutConfig(reader);

            // Música
            String musicPath = reader.getString("music.path");
            IMusicPlayer musicPlayer = new MusicPlayer(musicPath);
            musicPlayer.play();

            // Sonidos
            ISoundEffect coinSound = new SoundEffect(reader.getString("sound.coin"));
            ISoundEffect wallCollisionSound = new SoundEffect(reader.getString("sound.wall"));
            ISoundEffect playerCollisionSound = new SoundEffect(reader.getString("sound.player"));

            // Mapa y jugador
            IMapsHandler mapsHandler = new MapReader(layoutConfig);
            Player player = new Player(100,700,30);

            Layout l = new Layout(layoutConfig, mapsHandler, player);
            MainWindow mainWindow = new MainWindow(l, layoutConfig);
            mainWindow.setVisible(true);

            // Red
            IConfigReader configReader = new PropertiesManager("application.properties");
            UDPConfig config = new UDPConfig(configReader);

            config.setPlayerId(playerName);

            NetworkManager network = new NetworkManager(
                    config,
                    new SwingRenderer(l, config.getPlayerId())
            );

            network.connect();

            // Eventos
            l.setOnMove(data -> network.sendPosition(data[0], data[1], data[2]));
            l.setCoinSound(coinSound);
            l.setWallCollisionSound(wallCollisionSound);
            l.setPlayerCollisionSound(playerCollisionSound);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}