package dev.obscuria.maestro.client.music.player;

import dev.obscuria.maestro.client.music.MusicCache;
import dev.obscuria.maestro.client.music.MusicLayerEnum;
import dev.obscuria.maestro.client.registry.MaestroClientRegistries;
import dev.obscuria.maestro.config.ClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import org.jetbrains.annotations.Nullable;

public final class MusicPlayer extends MusicManager {

    private static final int REEVALUATE_INTERVAL_TICKS = 20;

    private final Minecraft client;
    private final MusicLayer layerEncounter = new MusicLayer();
    private final MusicLayer layerUnderscore = new MusicLayer();

    private int tickCount;

    public MusicPlayer(Minecraft client) {
        super(client);
        this.client = client;
    }

    @Override
    public void tick() {
        tickCount += 1;

        for (var track : MusicCache.allTracks()) {
            track.clearSuppression();
        }

        if (tickCount % REEVALUATE_INTERVAL_TICKS == 0) {
            layerEncounter.select(resolveTrack(MusicLayerEnum.ENCOUNTER));
            layerUnderscore.select(resolveTrack(MusicLayerEnum.UNDERSCORE));
        }

        layerEncounter.tick(false);
        layerUnderscore.tick(layerEncounter.isActive());

        for (var track : MusicCache.allTracks()) {
            track.onPlayerTick();
        }
    }

    @Override
    public void startPlaying(Music music) {
        // Ignored
    }

    @Override
    public void stopPlaying(Music music) {
        MusicCache.getTrack(music).stop(MusicStopReason.MANUAL);
    }

    @Override
    public void stopPlaying() {
        for (var track : MusicCache.allTracks()) {
            track.stop(MusicStopReason.SYSTEM);
        }
    }

    @Override
    public boolean isPlayingMusic(Music music) {
        if (music == Musics.UNDER_WATER) return false;
        return MusicCache.getTrack(music).isPlaying();
    }

    private @Nullable MusicTrack resolveTrack(MusicLayerEnum layer) {
        if (ClientConfig.isMaestroEnabled()) {
            for (var definition : MaestroClientRegistries.Resource.MUSIC_DEFINITION.listElements()) {
                if (definition.layer() != layer) continue;
                if (!definition.test()) continue;
                var track = MusicTrack.fromDefinition(definition);
                if (track.canBeSelected()) return track;
            }
        }

        if (ClientConfig.isVanillaEnabled() && layer == MusicLayerEnum.UNDERSCORE) {
            var track = MusicTrack.fromVanilla(client.getSituationalMusic());
            if (track.canBeSelected()) return track;
        }

        return null;
    }
}
