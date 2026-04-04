package org.kami.view;

import javax.swing.*;

public class MainWindow extends JFrame {

    public MainWindow(Layout layout){
        setTitle("Pantalla Local");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(layout);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
