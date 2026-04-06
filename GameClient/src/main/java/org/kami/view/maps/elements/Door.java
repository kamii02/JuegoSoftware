package org.kami.view.maps.elements;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kami.view.maps.mapelementsfactory.IMapElement;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Door implements IMapElement {

    private int x;
    private int y;
    private int width;
    private int height;
}