package org.kami.controller;

public interface ICollisionListener {
    void onWallHit();
    void onCoinCollected();
    void onDoorEntered(int newLevel);
    void onGameWon();
}
