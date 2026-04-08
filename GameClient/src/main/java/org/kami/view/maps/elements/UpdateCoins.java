package org.kami.view.maps.elements;

import org.kami.config.element.Player;
import org.kami.config.maps.IMapsHandler;
import org.kami.audio.ISoundEffect;

public class UpdateCoins {

    private Player player;
    private IMapsHandler mapsHandler;
    private ISoundEffect coinSound;

    public UpdateCoins(Player player, IMapsHandler mapsHandler) {
        this.player = player;
        this.mapsHandler = mapsHandler;
    }

    // 🔥 setter para evitar NULL
    public void setCoinSound(ISoundEffect coinSound) {
        this.coinSound = coinSound;
    }

    // 🔥 MÉTODO PRINCIPAL
    public void updateCoins(int level) {

        GameMap gameMap = mapsHandler.readMaps().get(level - 1);

        int px = player.getPosX();
        int py = player.getPosY();
        int ps = player.getTamanio();

        for (int i = 0; i < gameMap.getCoins().size(); i++) {
            Coin coin = gameMap.getCoins().get(i);

            // ⏳ eliminar por tiempo
            if (coin.isExpired()) {
                gameMap.getCoins().remove(i);
                i--;
                continue;
            }

            // 🎯 colisión
            boolean solapaX = px < coin.getX() + coin.getWidth() && px + ps > coin.getX();
            boolean solapaY = py < coin.getY() + coin.getHeight() && py + ps > coin.getY();

            if (solapaX && solapaY) {

                System.out.println("💰 Moneda recogida"); // DEBUG

                if (coinSound != null) {
                    coinSound.play();
                }

                //gameMap.addScore(20);         // ➕ puntos
                gameMap.getCoins().remove(i); // ❌ eliminar moneda

                i--; // 🔥 importantísimo
            }
        }
    }
}