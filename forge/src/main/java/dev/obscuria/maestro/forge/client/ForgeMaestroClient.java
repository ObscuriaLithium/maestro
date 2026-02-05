package dev.obscuria.maestro.forge.client;

import dev.obscuria.maestro.Maestro;
import dev.obscuria.maestro.client.registry.MusicManager;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.resources.ReloadableResourceManager;

public final class ForgeMaestroClient {

    public static void init() {
        Maestro.init();
        if (Minecraft.getInstance().getResourceManager() instanceof ReloadableResourceManager manager)
            manager.registerReloadListener(MusicManager.SHARED);
    }
}
