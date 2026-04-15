package org.kami.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kami.maps.ITexturedElement;
import org.kami.maps.mapelementsfactory.IMapElement;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coin implements IMapElement, ITexturedElement {
    private int x;
    private int y;
    private int height;
    private int width;
    private int points = 20;
    private long spawnTime;
    private long duration = 7000;
    private String texturePath;

    public boolean isExpired() {
        return System.currentTimeMillis() - spawnTime > duration;
    }


    @Override
    public void setTexturePath(String path) {
        this.texturePath = path;
    }

    @Override
    public String getTexturePath() {
        return texturePath;
    }
}
