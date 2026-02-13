package dev.obscuria.maestro.mixin;

import dev.obscuria.fragmentum.content.network.FragmentumNetworking;
import dev.obscuria.maestro.content.network.ClientboundStructureListPayload;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@Mixin(ServerPlayer.class)
public abstract class MixinServerPlayer {

    @Inject(method = "tick", at = @At("RETURN"))
    private void injectStructureScanner(CallbackInfo info) {
        var self = (ServerPlayer) (Object) this;
        if ((self.tickCount % 20) != 0) return;

        var pos = self.blockPosition();
        var level = self.level();
        var structureManager = level.structureManager();
        var registry = level.registryAccess().lookupOrThrow(Registries.STRUCTURE);
        var result = new ArrayList<Identifier>(1);

        for (var structure : structureManager.getAllStructuresAt(pos).keySet()) {
            @Nullable var key = registry.getKey(structure);
            if (key == null) continue;
            var start = structureManager.getStructureAt(pos, structure);
            if (start == null) continue;
            if (!structureManager.structureHasPieceAt(pos, start)) continue;
            result.add(key);
        }

        FragmentumNetworking.sendTo(self, new ClientboundStructureListPayload(result));
    }
}
