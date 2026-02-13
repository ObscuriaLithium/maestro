package dev.obscuria.maestro.fabric.mixin.client;

import dev.obscuria.maestro.client.registry.ResourceManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {

    private @Shadow @Final ReloadableResourceManager resourceManager;

    @Inject(method = "<init>", at = @At(value = "NEW", target = "(Lnet/minecraft/client/Minecraft;)Lnet/minecraft/client/sounds/MusicManager;"))
    private void init(GameConfig config, CallbackInfo info) {
        this.resourceManager.registerReloadListener(ResourceManager.SHARED);
    }
}