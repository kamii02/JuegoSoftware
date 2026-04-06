package org.kami.view.maps.elements;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GameMap {
    private List<Wall> walls;
    private List<Coin> coins;
    private List<Door> doors;
}
