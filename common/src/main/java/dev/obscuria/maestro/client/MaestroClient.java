package dev.obscuria.maestro.client;

import dev.obscuria.maestro.client.registry.MaestroClientRegistries;
import dev.obscuria.maestro.config.ClientConfig;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public final class MaestroClient {

    public static final List<ResourceLocation> structureList = new ArrayList<>();

    public static void init() {
        ClientConfig.init();
        MaestroClientRegistries.init();
    }
}
