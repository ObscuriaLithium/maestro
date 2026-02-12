package dev.obscuria.maestro.content.network;

import dev.obscuria.maestro.Maestro;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public record ClientboundStructureListPayload(List<ResourceLocation> structures) implements CustomPacketPayload {

    public static final Type<ClientboundStructureListPayload> TYPE;
    public static final StreamCodec<FriendlyByteBuf, ClientboundStructureListPayload> CODEC;

    @Override
    public Type<ClientboundStructureListPayload> type() {
        return TYPE;
    }

    public static void handle(Player player, ClientboundStructureListPayload payload) {
        ClientNetworkHandler.handle(player, payload);
    }

    static {
        TYPE = new Type<>(Maestro.key("clientbound_structure_list"));
        CODEC = StreamCodec.composite(
                ResourceLocation.STREAM_CODEC.apply(ByteBufCodecs.list()),
                ClientboundStructureListPayload::structures,
                ClientboundStructureListPayload::new);
    }
}
