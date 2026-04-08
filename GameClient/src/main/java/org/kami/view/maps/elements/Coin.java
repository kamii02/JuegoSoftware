package org.kami.view.maps.elements;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kami.audio.ISoundEffect;
import org.kami.config.element.Player;
import org.kami.view.maps.mapelementsfactory.IMapElement;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coin implements IMapElement {
    private int x;
    private int y;
    private int height;
    private int width;
    private int points = 20;
    private long spawnTime;
    private long duration = 7000;

    public boolean isExpired() {
        return System.currentTimeMillis() - spawnTime > duration;
    }


}
