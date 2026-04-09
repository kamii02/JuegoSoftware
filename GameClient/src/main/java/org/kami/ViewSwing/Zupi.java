package org.kami.ViewSwing;

import org.kami.GameLauncher;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que representa la interfaz gráfica inicial del juego.
 *
 * Permite al usuario ingresar su nombre y comenzar la partida.
 * Valida que el campo de texto no esté vacío antes de iniciar
 * el juego. Al presionar el botón de jugar, se lanza la lógica
 * principal del juego y se cierra la ventana del menú.
 *
 * @author Camila Prada
 * @version 1.0.0
 */
public class Zupi {

    private JTextField txtName;
    private JPanel textfield;
    private JButton PLAYButton;

    /**
     * Constructor que inicializa los componentes de la interfaz
     * y define el comportamiento del botón de inicio.
     */
    public Zupi() {
        PLAYButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (txtName.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter a name");
                } else {
                    String name = txtName.getText();

                    // Inicia el juego con el nombre del jugador
                    GameLauncher.startGame(name);

                    // Cierra la ventana del menú
                    SwingUtilities.getWindowAncestor(textfield).dispose();
                }
            }
        });
    }

    /**
     * Retorna el panel principal de la interfaz.
     *
     * @return JPanel que contiene los componentes gráficos del menú.
     */
    public JPanel getPanel() {
        return textfield;
    }
}