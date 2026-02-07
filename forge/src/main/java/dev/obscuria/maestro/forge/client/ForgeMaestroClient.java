package dev.obscuria.maestro.forge.client;

import dev.obscuria.maestro.client.MaestroClient;
import dev.obscuria.maestro.client.registry.ResourceManager;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.resources.ReloadableResourceManager;

public final class ForgeMaestroClient {

    public static void init() {
        MaestroClient.init();
        if (Minecraft.getInstance().getResourceManager() instanceof ReloadableResourceManager manager)
            manager.registerReloadListener(ResourceManager.SHARED);
    }
}
