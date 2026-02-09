package dev.obscuria.maestro.client.music.player;

import lombok.Getter;
import org.jetbrains.annotations.Nullable;

public final class MusicLayer {

    @Getter private @Nullable MusicTrack activeTrack;

    public void tick(boolean suppressedByHigherLayer) {
        if (activeTrack == null) return;
        this.activeTrack.onLayerTick();
        this.activeTrack.setSuppressed(suppressedByHigherLayer);
    }

    public void select(@Nullable MusicTrack track) {
        if (activeTrack == track) return;
        this.activeTrack = track;
        if (track == null) return;
        track.onAssigned();
    }

    public void stop(MusicStopReason reason) {
        if (activeTrack != null) {
            activeTrack.stop(reason);
            activeTrack = null;
        }
    }

    public boolean isActive() {
        return activeTrack != null;
    }
}
