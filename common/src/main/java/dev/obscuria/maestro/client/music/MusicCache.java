package dev.obscuria.maestro.client.music;

import net.minecraft.sounds.Music;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class MusicCache {

    private static final Map<MusicDefinition, Music> CACHE = new ConcurrentHashMap<>();

    public static Music get(MusicDefinition definition) {
        if (CACHE.containsKey(definition)) return CACHE.get(definition);
        var music = definition.assemble();
        CACHE.put(definition, music);
        return music;
    }

    public static void clear() {
        CACHE.clear();
    }
}
