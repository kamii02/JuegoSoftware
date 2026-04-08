package org.kami.view.maps.elements;

import org.kami.config.element.Player;

public interface ICoinService {
    void checkCollision(Player player, GameMap gameMap);
}
