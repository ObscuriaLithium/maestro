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

    private final Minecraft client;
    private final MusicLayer layerEncounter;
    private final MusicLayer layerUnderscore;

    public MusicPlayer(Minecraft client) {
        super(client);
        this.client = client;
        this.layerEncounter = new MusicLayer();
        this.layerUnderscore = new MusicLayer();
    }

    @Override
    public void tick() {
        for (var track : MusicCache.allTracks()) track.setSuppressed(false);
        this.layerEncounter.setTrack(evaluate(MusicLayerEnum.ENCOUNTER));
        this.layerUnderscore.setTrack(evaluate(MusicLayerEnum.UNDERSCORE));
        this.layerEncounter.tick(false);
        this.layerUnderscore.tick(layerEncounter.isPlaying());
        for (var track : MusicCache.allTracks()) track.tick();
    }

    @Override
    public void startPlaying(Music music) {}

    @Override
    public void stopPlaying(Music music) {
        MusicCache.getTrack(music).kill();
    }

    @Override
    public void stopPlaying() {
        MusicCache.allTracks().forEach(MusicTrack::kill);
    }

    @Override
    public boolean isPlayingMusic(Music music) {
        if (music == Musics.UNDER_WATER) return false;
        return MusicCache.getTrack(music).isPlaying();
    }

    private @Nullable MusicTrack evaluate(MusicLayerEnum layer) {
        if (ClientConfig.isMaestroEnabled()) {
            for (var definition : MaestroClientRegistries.Resource.MUSIC_DEFINITION.listElements()) {
                if (definition.layer() != layer) continue;
                if (!definition.test()) continue;
                return MusicTrack.forDefinition(definition);
            }
        }
        if (ClientConfig.isVanillaEnabled()) {
            return layer == MusicLayerEnum.UNDERSCORE
                    ? MusicTrack.forVanilla(client.getSituationalMusic())
                    : null;
        }
        return null;
    }
}
