package org.kami.shared;

import org.kami.model.GameState;

public interface IStateListener {
    void onStateChanged(GameState state);
}
