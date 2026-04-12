package org.kami.ViewSwing;

import org.kami.GameLauncher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que representa la interfaz gráfica inicial del videojuego Zupi.
 *
 * Esta clase se encarga de mostrar un menú donde el usuario puede ingresar
 * su nombre antes de iniciar la partida. Incluye validaciones básicas para
 * evitar que el campo esté vacío y controla el flujo de inicio del juego.
 *
 * Además, gestiona el fondo personalizado del panel principal utilizando
 * una imagen cargada desde los recursos del proyecto.
 *
 * @author Camila Prada
 * @version 1.0.0
 */
public class Zupi {

    /**
     * Campo de texto donde el usuario ingresa su nombre.
     */
    private JTextField txtName;

    /**
     * Panel principal que contiene los componentes de la interfaz.
     */
    private JPanel textfield;

    /**
     * Botón que permite iniciar el juego.
     */
    private JButton PLAYButton;

    /**
     * Panel de fondo (utilizado en el diseñador visual).
     */
    private JPanel fondoPanel;

    /**
     * Constructor de la clase Zupi.
     *
     * Inicializa los eventos de los componentes, en este caso el botón PLAY.
     * Se valida que el usuario haya ingresado un nombre antes de iniciar el juego.
     * Si la validación es correcta, se lanza la lógica principal del juego
     * y se cierra la ventana actual.
     */
    public Zupi() {
        PLAYButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Validación: el nombre no puede estar vacío
                if (txtName.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter a name");
                } else {
                    String name = txtName.getText();

                    // Inicia el juego con el nombre del jugador
                    GameLauncher.startGame(name);

                    // Cierra la ventana del menú actual
                    SwingUtilities.getWindowAncestor(textfield).dispose();
                }
            }
        });
    }

    /**
     * Obtiene el panel principal de la interfaz.
     *
     * Este método permite integrar esta vista en otras ventanas o
     * contenedores dentro de la aplicación.
     *
     * @return JPanel que contiene todos los componentes del menú inicial.
     */
    public JPanel getPanel() {
        return textfield;
    }

    /**
     * Método utilizado por el diseñador de interfaces para crear
     * componentes personalizados.
     *
     * En este caso, se sobreescribe el panel principal para dibujar
     * una imagen de fondo escalada al tamaño del contenedor.
     */
    private void createUIComponents() {

        // Muestra en consola la ruta del recurso (útil para depuración)
        System.out.println(getClass().getResource("/images/Fondo.png"));

        // Creación de un panel personalizado con imagen de fondo
        textfield = new JPanel() {

            /**
             * Imagen de fondo cargada desde los recursos del proyecto.
             */
            private Image fondo = new ImageIcon(
                    getClass().getResource("/images/Fondo.png")
            ).getImage();

            /**
             * Sobrescritura del método paintComponent para dibujar
             * la imagen de fondo en el panel.
             *
             * @param g objeto Graphics utilizado para renderizar la imagen
             */
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Dibuja la imagen ajustándola al tamaño del panel
                g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
            }
        };

        // Se usa layout nulo para posicionamiento manual de componentes
        textfield.setLayout(null);
    }
}