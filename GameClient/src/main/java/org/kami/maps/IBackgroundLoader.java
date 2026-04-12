package org.kami.maps;

import java.awt.*;
import java.awt.image.ImageObserver;

public interface IBackgroundLoader {
    void load(int level);
    void draw(Graphics2D g2d, int width, int height, ImageObserver observer);
}
