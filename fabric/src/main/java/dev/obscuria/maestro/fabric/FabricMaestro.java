package dev.obscuria.maestro.fabric;

import dev.obscuria.maestro.Maestro;
import net.fabricmc.api.ClientModInitializer;

public final class FabricMaestro implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Maestro.init();
    }
}