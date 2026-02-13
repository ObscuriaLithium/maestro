package dev.obscuria.maestro.fabric;

import dev.obscuria.maestro.Maestro;
import net.fabricmc.api.ModInitializer;

public final class FabricMaestro implements ModInitializer {

    @Override
    public void onInitialize() {
        Maestro.init();
    }
}