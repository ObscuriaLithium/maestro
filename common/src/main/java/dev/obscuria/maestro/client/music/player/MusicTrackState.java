package dev.obscuria.maestro.client.music.player;

import org.jetbrains.annotations.Nullable;

public final class MusicTrackState {

    int layerActivityTicks = 0;
    int restartDelayTicks = 0;

    boolean suppressed = false;
    boolean justAssigned = false;
    boolean terminated = false;

    @Nullable MusicStopReason pendingStopReason;
}
