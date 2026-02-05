package dev.obscuria.maestro;

import dev.obscuria.maestro.client.registry.MaestroRegistries;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Maestro {

    public static final String MODID = "maestro";
    public static final String DISPLAY_NAME = "Maestro";
    public static final Logger LOGGER = LoggerFactory.getLogger(DISPLAY_NAME);

    public static ResourceLocation key(String name) {
        return new ResourceLocation(MODID, name);
    }

    public static void init() {
        MaestroRegistries.init();
    }
}
