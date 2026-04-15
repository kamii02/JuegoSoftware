package org.kami.controller;

import org.kami.network.SwingRenderer;
import org.kami.config.*;
import org.kami.model.Player;
import org.kami.network.NetworkManager;
import org.kami.maps.IMapsHandler;
import org.kami.maps.MapReader;
import org.kami.view.Layout;
import org.kami.view.MainWindow;
import org.kami.audio.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Clase encargada de inicializar y lanzar el juego completo.
 *
 * <p>Actúa como punto de entrada principal para la ejecución del juego,
 * encargándose de ensamblar todos los componentes necesarios:</p>
 *
 * <ul>
 *     <li>Configuración general del sistema</li>
 *     <li>Música de fondo y efectos de sonido</li>
 *     <li>Mapa del juego y jugador</li>
 *     <li>Interfaz gráfica (ventana principal)</li>
 *     <li>Conexión de red para multijugador</li>
 * </ul>
 *
 * <p>Además, establece la comunicación entre la lógica del juego,
 * la interfaz y la red mediante eventos.</p>
 *
 * @author Camila Prada
 * @version 1.0.0
 */
public class GameLauncher {

    /**
     * Método que inicia la ejecución del juego.
     *
     * <p>Realiza las siguientes acciones:</p>
     * <ol>
     *     <li>Carga la configuración desde archivo properties</li>
     *     <li>Inicializa la música de fondo</li>
     *     <li>Carga los efectos de sonido</li>
     *     <li>Construye el mapa y el jugador</li>
     *     <li>Crea la interfaz gráfica principal</li>
     *     <li>Configura la conexión de red (multijugador)</li>
     *     <li>Asocia eventos entre UI, lógica y red</li>
     * </ol>
     *
     * <p>Este método centraliza toda la inicialización del juego,
     * facilitando el mantenimiento y la escalabilidad.</p>
     *
     * @param playerName Nombre del jugador ingresado desde la interfaz.
     *                   Se utiliza como identificador único en la red.
     */
    public static void startGame(String playerName) {
        try {

            // CONFIGURACIÓN GENERAL


            /** Lector de configuración desde archivo properties */
            IConfigReader reader = PropertiesManager.getInstance();

            /** Configuración del layout (tamaño, posiciones, etc.) */
            ILayoutConfig layoutConfig = new LayoutConfig(reader);



            // MÚSICA DE FONDO


            /** Ruta de la música obtenida desde configuración */
            String musicPath = reader.getString("music.path");

            /** Reproductor de música */
            IMusicPlayer musicPlayer = new MusicPlayer(musicPath);

            /** Inicia la reproducción de música */
            musicPlayer.play();


            // EFECTOS DE SONIDO


            /** Sonido al recoger moneda */
            ISoundEffect coinSound = new SoundEffect(reader.getString("sound.coin"));

            /** Sonido al colisionar con muro */
            ISoundEffect wallCollisionSound = new SoundEffect(reader.getString("sound.wall"));

            /** Sonido al colisionar con otro jugador */
            ISoundEffect playerCollisionSound = new SoundEffect(reader.getString("sound.player"));


            // MAPA Y JUGADOR


            /** Manejador de mapas del juego */
            IMapsHandler mapsHandler = MapReader.getInstance(layoutConfig);

            /** Instancia del jugador con posición inicial y tamaño */
            Player player = new Player(100,700,30);



            // INTERFAZ GRÁFICA


            /** Layout principal que contiene la lógica visual del juego */
            Layout l = new Layout(layoutConfig, mapsHandler, player);

            /** Ventana principal del juego */
            MainWindow mainWindow = new MainWindow(l, layoutConfig);

            /** Hace visible la ventana */
            mainWindow.setVisible(true);



            // CONFIGURACIÓN DE RED

            /** Lector de configuración reutilizado */
            IConfigReader configReader = PropertiesManager.getInstance();

            /** Configuración específica de red UDP */
            UDPConfig config = new UDPConfig(configReader);

            /** Asigna el identificador del jugador */
            config.setPlayerId(playerName);

            /** Gestor de red encargado de la comunicación */
            NetworkManager network = new NetworkManager(
                    config,
                    new SwingRenderer(l, config.getPlayerId())
            );

            /** Inicia la conexión con el servidor */
            network.connect();

            /**
             * Evento que se ejecuta cuando la conexión está lista.
             * Activa el estado de juego y redibuja la interfaz.
             */
            network.setOnReady(() ->{
                l.setGameReady(true);
                l.repaint();
            });



            // EVENTOS DEL JUEGO


            /**
             * Evento cuando el jugador gana.
             * Se envía la notificación a la red.
             */
            l.setOnWin(() -> network.sendWin());

            /**
             * Evento cuando otro jugador gana.
             * Muestra la pantalla de victoria en la UI.
             */
            network.setOnWin(raw ->
                    l.showWinScreen(raw)
            );

            /**
             * Evento de movimiento del jugador.
             * Envía posición, dirección y puntaje a la red.
             */
            l.setOnMove(data ->
                    network.sendPosition(data[0], data[1], data[2], player.getScore())
            );


            // ASIGNACIÓN DE SONIDOS

            /** Asigna sonidos al layout */
            l.setCoinSound(coinSound);
            l.setWallCollisionSound(wallCollisionSound);
            l.setPlayerCollisionSound(playerCollisionSound);

        } catch (Exception e) {

            /**
             * Manejo de errores general.
             * Imprime el stacktrace en consola para depuración.
             */
            e.printStackTrace();
        }
    }
}