package dev.obscuria.maestro.fabric.client;

import dev.obscuria.maestro.client.MaestroClient;
import net.fabricmc.api.ClientModInitializer;

public final class FabricMaestroClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        MaestroClient.init();
    }
}
