package dev.obscuria.maestro.client.registry;

import com.google.gson.JsonParser;
import dev.obscuria.maestro.Maestro;
import dev.obscuria.maestro.client.music.MusicCache;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import org.apache.commons.lang3.Strings;

public final class ResourceManager implements ResourceManagerReloadListener {

    public static final ResourceManager SHARED = new ResourceManager();

    private ResourceManager() {}

    @Override
    public void onResourceManagerReload(net.minecraft.server.packs.resources.ResourceManager manager) {
        MusicCache.clear();
        for (var kind : ResourceKind.values()) {
            kind.spec.onReloadStart();
            final var resources = manager.listResources(kind.spec.resourceDir(), this::isValidResource);
            resources.forEach((path, resource) -> loadResource(kind, path, resource));
            kind.spec.onReloadEnd();
        }
    }

    private boolean isValidResource(Identifier path) {
        return path.toString().endsWith(".json");
    }

    private void loadResource(ResourceKind kind, Identifier path, Resource resource) {
        try {
            kind.spec.load(extractKey(kind, path), JsonParser.parseReader(resource.openAsReader()));
        } catch (Exception exception) {
            Maestro.LOGGER.error("Failed to load resource {}: {}", path, exception.getMessage());
        }
    }

    private Identifier extractKey(ResourceKind kind, Identifier path) {
        return path.withPath(it -> {
            var result = Strings.CS.removeStart(it, kind.spec.resourceDir() + "/");
            result = Strings.CS.removeEnd(result, ".json");
            return result;
        });
    }
}
