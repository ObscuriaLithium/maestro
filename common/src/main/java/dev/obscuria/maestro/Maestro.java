package dev.obscuria.maestro;

import dev.obscuria.fragmentum.content.network.FragmentumNetworking;
import dev.obscuria.maestro.content.network.ClientboundStructureListPayload;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Maestro {

    public static final String MODID = "maestro";
    public static final String DISPLAY_NAME = "Maestro";
    public static final Logger LOGGER = LoggerFactory.getLogger(DISPLAY_NAME);

    public static ResourceLocation key(String name) {
        return ResourceLocation.fromNamespaceAndPath(MODID, name);
    }

    public static void init() {
        registerPayloads();
    }

    private static void registerPayloads() {
        final var registrar = FragmentumNetworking.registrar(MODID);
        registrar.registerClientbound(
                ClientboundStructureListPayload.class,
                ClientboundStructureListPayload.TYPE,
                ClientboundStructureListPayload.CODEC,
                ClientboundStructureListPayload::handle);
    }
}
