package org.kami.view.observer;

public interface ICollisionListener {
    void onWallHit();
    void onCoinCollected();
    void onDoorEntered(int newLevel);
    void onPlayerCollision(String remoteId);
}
