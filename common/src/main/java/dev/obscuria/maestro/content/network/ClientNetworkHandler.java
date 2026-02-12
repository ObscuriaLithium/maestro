package dev.obscuria.maestro.content.network;

import dev.obscuria.maestro.client.MaestroClient;
import net.minecraft.world.entity.player.Player;

public interface ClientNetworkHandler {

    static void handle(Player player, ClientboundStructureListPayload payload) {
        MaestroClient.structureList.clear();
        MaestroClient.structureList.addAll(payload.structures());
    }
}
