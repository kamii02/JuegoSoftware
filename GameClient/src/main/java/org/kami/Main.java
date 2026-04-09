package org.kami;

import org.kami.ViewSwing.Zupi;

import javax.swing.*;

/**
 * Clase principal de la aplicación.
 *
 * Se encarga de iniciar la ejecución del programa mostrando
 * la interfaz gráfica inicial del juego. Esta interfaz permite
 * al usuario ingresar su nombre antes de comenzar la partida.
 *
 * Utiliza el hilo de eventos de Swing para garantizar que la
 * interfaz gráfica se ejecute de manera segura.
 *
 * @author Camila Prada
 * @version 1.0.0
 */
public class Main {

    /**
     * Método principal que inicia la aplicación.
     *
     * Crea la ventana principal y carga el panel de la interfaz
     * inicial del juego. Configura el tamaño, posición y comportamiento
     * de cierre de la ventana.
     *
     * @param args Argumentos de línea de comandos (no utilizados).
     * @throws Exception Excepción general en caso de errores durante la ejecución.
     */
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("ZUPI");
            frame.setContentPane(new Zupi().getPanel());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 800);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}