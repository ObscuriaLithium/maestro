package dev.obscuria.maestro.neoforge;

import dev.obscuria.maestro.Maestro;
import dev.obscuria.maestro.neoforge.client.NeoForgeMaestroClient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(Maestro.MODID)
public final class NeoForgeMaestro {

    public NeoForgeMaestro(IEventBus eventBus) {
        Maestro.init();
        if (FMLEnvironment.getDist().isClient()) {
            NeoForgeMaestroClient.init(eventBus);
        }
    }
}