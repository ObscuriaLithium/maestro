package dev.obscuria.maestro.forge;

import dev.obscuria.maestro.Maestro;
import dev.obscuria.maestro.forge.client.NeoForgeMaestroClient;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(Maestro.MODID)
public final class NeoForgeMaestro {

    public NeoForgeMaestro() {
        Maestro.init();
        if (FMLEnvironment.dist.isClient()) {
            NeoForgeMaestroClient.init();
        }
    }
}