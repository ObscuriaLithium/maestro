package dev.obscuria.maestro.client.music.player;

import dev.obscuria.maestro.client.music.MusicCache;
import dev.obscuria.maestro.client.music.MusicDefinition;
import dev.obscuria.maestro.client.music.MusicLayerEnum;
import dev.obscuria.maestro.client.registry.MaestroClientRegistries;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.Music;
import org.jetbrains.annotations.Nullable;

public final class MusicTrack {

    @Getter private final String name;
    @Getter private final Music music;
    @Getter private final double fadeInTime;
    @Getter private final double fadeOutTime;
    @Getter private final MusicTrackState state = new MusicTrackState();

    public final int cooldownTicks;
    public final boolean occupyLayerDuringDelay;
    public final boolean resetDelayOnReactivation;

    private @Nullable MusicSoundInstance instance;

    public MusicTrack(Music music, MusicDefinition definition) {
        this.music = music;
        this.fadeInTime = 0;
        this.fadeOutTime = 0;
        this.cooldownTicks = 20 * definition.cooldownSeconds();
        this.occupyLayerDuringDelay = definition.occupyLayerDuringCooldown();
        this.resetDelayOnReactivation = definition.resetCooldownOnReactivation();
        this.name = "maestro ♪ " + MaestroClientRegistries.Resource.MUSIC_DEFINITION.getKey(definition);
    }

    public MusicTrack(Music music, double fadeInTime, double fadeOutTime) {
        this.music = music;
        this.fadeInTime = fadeInTime;
        this.fadeOutTime = fadeOutTime;
        this.cooldownTicks = 0;
        this.occupyLayerDuringDelay = false;
        this.resetDelayOnReactivation = false;
        this.name = "vanilla ♪ " + music.sound().value().location();
    }

    public static MusicTrack fromVanilla(Music music) {
        return MusicCache.getTrack(music);
    }

    public static @Nullable MusicTrack fromDefinition(@Nullable MusicDefinition definition) {
        return definition != null ? MusicCache.getTrack(definition) : null;
    }

    public boolean canBeSelected() {
        if (state.terminated) return false;
        return occupyLayerDuringDelay || state.restartDelayTicks <= 0;
    }

    public void onAssigned(MusicLayerEnum layer) {
        state.justAssigned = true;
    }

    public void onLayerTick() {
        state.layerActivityTicks = 2;

        if (state.justAssigned
                && resetDelayOnReactivation) {
            state.restartDelayTicks = 0;
        }

        if (!state.justAssigned &&
                !isInstanceActive()
                && state.restartDelayTicks < 0) {
            state.restartDelayTicks = cooldownTicks;
        }

        if (!isInstanceActive()
                && state.restartDelayTicks <= 0) {
            startInstance();
        }

        if (instance != null) {
            instance.fadeIn();
        }
    }

    public void onPlayerTick() {
        state.justAssigned = false;
        state.restartDelayTicks--;
        state.layerActivityTicks--;

        if (state.layerActivityTicks <= 0
                && instance != null) {
            instance.fadeOut();
        }
    }

    public void setSuppressed(boolean suppressed) {
        state.suppressed = suppressed;
    }

    public void clearSuppression() {
        state.suppressed = false;
    }

    public void stop(MusicStopReason reason) {
        state.pendingStopReason = reason;
        if (instance == null) return;
        Minecraft.getInstance().getSoundManager().stop(instance);
    }

    public void terminate() {
        stop(MusicStopReason.SYSTEM);
        state.terminated = true;
    }

    public boolean isPlaying() {
        return isInstanceActive();
    }

    public float getVolume() {
        return instance != null ? instance.getInstanceVolume() : 0;
    }

    private void startInstance() {
        if (state.terminated) return;
        instance = new MusicSoundInstance(this);
        Minecraft.getInstance().getSoundManager().play(instance);
    }

    private boolean isInstanceActive() {
        if (instance == null) return false;
        return Minecraft.getInstance().getSoundManager().isActive(instance);
    }

    @Override
    public String toString() {
        return "MusicTrack[id=%s, active=%s, delay=%s]".formatted(
                music.sound().value().location(),
                isInstanceActive(),
                state.restartDelayTicks);
    }
}
