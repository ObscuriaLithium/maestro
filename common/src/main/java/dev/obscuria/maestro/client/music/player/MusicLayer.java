package dev.obscuria.maestro.client.music.player;

import lombok.Getter;
import net.minecraft.sounds.Music;
import org.jetbrains.annotations.Nullable;

public final class MusicLayer {

    @Getter private @Nullable MusicTrack currentTrack;

    public void tick(boolean suppressed) {
        if (currentTrack == null) return;
        this.currentTrack.markActive();
        this.currentTrack.setSuppressed(suppressed);
    }

    public void setTrack(@Nullable MusicTrack track) {
        this.currentTrack = track;
    }

    public void kill() {
        if (currentTrack != null) currentTrack.kill();
        this.currentTrack = null;
    }

    public boolean isPlaying(Music music) {
        return currentTrack != null && currentTrack.getMusic() == music;
    }

    public boolean isPlaying() {
        return currentTrack != null;
    }
}
