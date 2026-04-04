package org.kami.view;

import org.kami.config.ILayoutConfig;

import javax.swing.*;

public class MainWindow extends JFrame {
    private ILayoutConfig layoutConfig;

    public MainWindow(Layout layout, ILayoutConfig layoutConfig){
        this.layoutConfig = layoutConfig;
        setTitle("Pantalla Local");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(layoutConfig.getWidth(), layoutConfig.getHeight());
        add(layout);
        //pack();
        setLocationRelativeTo(null);
        setResizable(false);
        //setVisible(true);
    }
}
