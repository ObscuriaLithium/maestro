package dev.obscuria.maestro.content.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public record ClientboundStructureListPayload(List<ResourceLocation> structures) {

    public static void encode(ClientboundStructureListPayload payload, FriendlyByteBuf buffer) {
        buffer.writeInt(payload.structures.size());
        payload.structures.forEach(buffer::writeResourceLocation);
    }

    public static ClientboundStructureListPayload decode(FriendlyByteBuf buffer) {
        var structures = new ArrayList<ResourceLocation>();
        var size = buffer.readInt();
        for (var i = 0; i < size; i++) {
            structures.add(buffer.readResourceLocation());
        }
        return new ClientboundStructureListPayload(structures);
    }

    public static void handle(Player player, ClientboundStructureListPayload payload) {
        ClientNetworkHandler.handle(player, payload);
    }
}
