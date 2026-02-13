package dev.obscuria.maestro.neoforge.client;

import dev.obscuria.maestro.Maestro;
import dev.obscuria.maestro.client.MaestroClient;
import dev.obscuria.maestro.client.registry.ResourceManager;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.AddClientReloadListenersEvent;

public final class NeoForgeMaestroClient {

    public static void init(IEventBus eventBus) {
        MaestroClient.init();
        eventBus.addListener(NeoForgeMaestroClient::registerReloadListener);
    }

    private static void registerReloadListener(final AddClientReloadListenersEvent event) {
        event.addListener(Maestro.key("listener"), ResourceManager.SHARED);
    }
}
