package dev.obscuria.maestro.client.registry;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import dev.obscuria.maestro.Maestro;
import dev.obscuria.maestro.client.music.MusicDefinition;
import net.minecraft.resources.ResourceLocation;

public enum ResourceKind {

    DEFINITION(new Spec<>("definition", "music", MusicDefinition.CODEC, MaestroRegistries.Resource.MUSIC_DEFINITION));

    public final Spec<?> spec;

    ResourceKind(Spec<?> spec) {
        this.spec = spec;
    }

    public record Spec<T>(
            String name,
            String directory,
            Codec<T> codec,
            ResourceRegistry<T> registry) {

        public String resourceDir() {
            return directory;
        }

        public void onReloadStart() {
            registry.onReloadStart();
        }

        public void load(ResourceLocation key, JsonElement element) {
            final var result = codec.decode(JsonOps.INSTANCE, element);
            result.result().ifPresent(it -> registry.register(key, it.getFirst()));
            result.error().ifPresent(it -> Maestro.LOGGER.error("Failed to register {} with key {}: {}", name, key, it.message()));
        }

        public void onReloadEnd() {
            registry.onReloadEnd();
            Maestro.LOGGER.info("Loaded {} resources from {}", registry.total(), resourceDir());
        }
    }
}