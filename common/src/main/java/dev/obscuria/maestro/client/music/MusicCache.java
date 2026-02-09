package dev.obscuria.maestro.client.music;

import dev.obscuria.maestro.client.music.player.MusicTrack;
import net.minecraft.sounds.Music;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class MusicCache {

    private static final Map<MusicDefinition, Music> CACHE = new ConcurrentHashMap<>();
    private static final Map<Music, MusicTrack> TRACKS = new ConcurrentHashMap<>();

    public static Collection<MusicTrack> allTracks() {
        return TRACKS.values();
    }

    public static Music getMusic(MusicDefinition definition) {
        if (CACHE.containsKey(definition)) return CACHE.get(definition);
        var music = definition.assemble();
        CACHE.put(definition, music);
        return music;
    }

    public static MusicTrack getTrack(Music music) {
        if (TRACKS.containsKey(music)) return TRACKS.get(music);
        var track = new MusicTrack(music, 1.0, 1.0);
        TRACKS.put(music, track);
        return track;
    }

    public static MusicTrack getTrack(MusicDefinition definition) {
        var music = getMusic(definition);
        if (TRACKS.containsKey(music)) return TRACKS.get(music);
        var track = new MusicTrack(music, definition);
        TRACKS.put(music, track);
        return track;
    }

    public static void clear() {
        TRACKS.values().forEach(MusicTrack::terminate);
        CACHE.clear();
        TRACKS.clear();
    }
}
