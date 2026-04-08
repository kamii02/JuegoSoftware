package org.kami.view.maps.elements;

import org.kami.audio.ISoundEffect;
import org.kami.config.element.Player;

public class CoinService implements ICoinService{

    private ISoundEffect coinSound;

    public CoinService(ISoundEffect coinSound) {
        this.coinSound = coinSound;
    }

    @Override
    public void checkCollision(Player player, GameMap gameMap) {

        int px = player.getPosX();
        int py = player.getPosY();
        int ps = player.getTamanio();

        for (int i = 0; i < gameMap.getCoins().size(); i++) {
            Coin coin = gameMap.getCoins().get(i);

            boolean solapaX = px < coin.getX() + coin.getWidth()  && px + ps > coin.getX();
            boolean solapaY = py < coin.getY() + coin.getHeight() && py + ps > coin.getY();

            if (solapaX && solapaY) {

                if (coinSound != null) coinSound.play();

               // gameMap.addScore(20);
                gameMap.getCoins().remove(i);

                i--; // importante
            }
        }
    }


}
