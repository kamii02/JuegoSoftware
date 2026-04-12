package org.kami.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kami.maps.ITexturedElement;
import org.kami.maps.mapelementsfactory.IMapElement;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wall implements IMapElement, ITexturedElement {

    private int x;
    private int y;
    private int height;
    private int width;
    private String texturePath;

    @Override
    public void setTexturePath(String path) {
        this.texturePath = path;
    }

    @Override
    public String getTexturePath() {
        return texturePath;
    }

}
