package org.kami.shared;

/**
 * I: interfaz pequeña y específica — solo quien necesita
 * escuchar cambios de estado la implementa.
 */
public interface IStateListener {
    void onStateChanged(GameState state);
}