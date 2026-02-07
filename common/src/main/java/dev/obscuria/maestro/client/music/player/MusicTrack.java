package dev.obscuria.maestro.client.music.player;

import dev.obscuria.maestro.client.music.MusicCache;
import dev.obscuria.maestro.client.music.MusicDefinition;
import dev.obscuria.maestro.client.sound.MusicSoundInstance;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.Music;
import org.jetbrains.annotations.Nullable;

public final class MusicTrack {

    @Getter private final Music music;
    @Getter private final double fadeInTime;
    @Getter private final double fadeOutTime;
    @Setter private boolean suppressed;
    private @Nullable MusicSoundInstance instance;
    private int activeTick;

    public MusicTrack(Music music, double fadeInTime, double fadeOutTime) {
        this.music = music;
        this.fadeInTime = fadeInTime;
        this.fadeOutTime = fadeOutTime;
    }

    public static MusicTrack forVanilla(Music music) {
        return MusicCache.getTrack(music);
    }

    public static @Nullable MusicTrack forDefinition(@Nullable MusicDefinition definition) {
        return definition != null ? MusicCache.getTrack(MusicCache.getMusic(definition)) : null;
    }

    public boolean suppressed() {
        return suppressed;
    }

    public void markActive() {
        this.activeTick = 2;
    }

    public void tick() {

        this.activeTick -= 1;

        validateInstance();

        if (activeTick > 0) {
            if (instance == null) {
                instance = new MusicSoundInstance(this);
                Minecraft.getInstance().getSoundManager().play(instance);
            }
            instance.fadeIn();
        } else {
            if (instance == null) return;
            instance.fadeOut();
        }
    }

    public void kill() {
        this.activeTick = 0;
        if (instance == null) return;
        Minecraft.getInstance().getSoundManager().stop(instance);
    }

    public boolean isPlaying() {
        validateInstance();
        return instance != null;
    }

    private void validateInstance() {
        if (instance == null) return;
        if (instance.isStopped()) instance = null;
        if (!Minecraft.getInstance().getSoundManager().isActive(instance)) instance = null;
    }
}
