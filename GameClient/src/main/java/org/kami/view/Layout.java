package org.kami.view;

import org.kami.config.element.CharacterStatus;
import org.kami.config.element.Player;
import org.kami.config.ILayoutConfig;

import javax.swing.*;
import java.awt.*;
//Recibe ancho y alto y dibuja
public class Layout extends JPanel {

    private Player player;


    public Layout(ILayoutConfig config, Player player){
        this.player = player;


        int width = config.getWidth();
        int height = config.getHeight();

        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Dimensiones inválidas");
        }

        setPreferredSize(new Dimension(width, height));
        setBackground(new Color(173, 216, 230));
    }

    /**
     * Método de Swing encargado de dibujar el contenido del panel.
     * Muestra el último estado de la bola y registra el evento.
     *
     * @param g objeto Graphics utilizado para realizar el dibujo
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (player == null) return;
        Graphics2D g2d = (Graphics2D) g;
        aplicarCalidadRenderizado(g2d);
        drawBall(g2d);
    }

    /**
     * Configura opciones de renderizado para mejorar la calidad del dibujo.
     *
     * @param g2d objeto Graphics2D usado para renderizar la imagen
     */
    private void aplicarCalidadRenderizado(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    }

    /**
     * Dibuja la bola en el panel usando su estado actual.
     * Si la imagen no está disponible, se dibuja un círculo como respaldo.
     *
     * @param g2d objeto Graphics2D utilizado para dibujar
     */
    private void drawBall(Graphics2D g2d) {
        CharacterStatus status = player.getStatus();

        int x   = status.getPosX();
        int y   = status.getPosY();
        int tam = status.getTamanio();

        if (player.getImagen() != null) {
            g2d.drawImage(player.getImagen(), x, y, tam, tam, this);
        } else {
            g2d.setColor(Color.WHITE);
            g2d.fillOval(x, y, tam, tam);
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.drawOval(x, y, tam, tam);
        }
    }
}

