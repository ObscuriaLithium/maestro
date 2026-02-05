package dev.obscuria.maestro.client.registry;

import com.google.gson.JsonParser;
import dev.obscuria.maestro.Maestro;
import dev.obscuria.maestro.client.music.MusicCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import org.apache.commons.lang3.StringUtils;

public final class MusicManager implements ResourceManagerReloadListener {

    public static final MusicManager SHARED = new MusicManager();

    private MusicManager() {}

    @Override
    public void onResourceManagerReload(ResourceManager manager) {
        MusicCache.clear();
        for (var kind : ResourceKind.values()) {
            kind.spec.onReloadStart();
            final var resources = manager.listResources(kind.spec.resourceDir(), this::isValidResource);
            resources.forEach((path, resource) -> loadResource(kind, path, resource));
            kind.spec.onReloadEnd();
        }
    }

    private boolean isValidResource(ResourceLocation path) {
        return path.toString().endsWith(".json");
    }

    private void loadResource(ResourceKind kind, ResourceLocation path, Resource resource) {
        try {
            kind.spec.load(extractKey(kind, path), JsonParser.parseReader(resource.openAsReader()));
        } catch (Exception exception) {
            Maestro.LOGGER.error("Failed to load resource {}: {}", path, exception.getMessage());
        }
    }

    private ResourceLocation extractKey(ResourceKind kind, ResourceLocation path) {
        return path.withPath(it -> {
            var result = StringUtils.removeStart(it, kind.spec.resourceDir() + "/");
            result = StringUtils.removeEnd(result, ".json");
            return result;
        });
    }
}
