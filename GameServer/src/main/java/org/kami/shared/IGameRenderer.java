package org.kami.shared;

/**
 * L: cualquier renderer (Swing, JavaFX, LibGDX) puede
 * reemplazar a otro sin tocar GameClient.
 * Cuando elijas tu motor gráfico, solo implementas esta interfaz.
 */
public interface IGameRenderer {
    void render(GameState state);
}