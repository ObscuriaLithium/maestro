package dev.obscuria.maestro.forge;

import dev.obscuria.maestro.Maestro;
import dev.obscuria.maestro.forge.client.ForgeMaestroClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@Mod(Maestro.MODID)
public final class ForgeMaestro {

    public ForgeMaestro() {
        Maestro.init();
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ForgeMaestroClient::init);
    }
}