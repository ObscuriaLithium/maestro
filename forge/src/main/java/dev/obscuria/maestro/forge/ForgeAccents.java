package dev.obscuria.maestro.forge;

import dev.obscuria.maestro.Maestro;
import dev.obscuria.maestro.compat.AccentsCompats;
import dev.obscuria.maestro.forge.client.ForgeAccentsClient;
import dev.obscuria.maestro.forge.compat.CuriosCompat;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(Maestro.MODID)
public final class ForgeAccents {

    public ForgeAccents() {
        Maestro.init();
        if (FMLEnvironment.dist.isClient())
            ForgeAccentsClient.init();
        var eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::initCompats);
    }

    private void initCompats(final FMLCommonSetupEvent event) {
        if (AccentsCompats.CURIOS.isLoaded())
            CuriosCompat.init();
    }
}