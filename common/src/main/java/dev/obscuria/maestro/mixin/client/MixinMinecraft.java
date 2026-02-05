package dev.obscuria.maestro.mixin.client;

import dev.obscuria.maestro.client.music.MusicCache;
import dev.obscuria.maestro.client.registry.MaestroRegistries;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.Music;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {

    @Inject(method = "getSituationalMusic", at = @At("HEAD"), cancellable = true)
    private void injectCustomMusic(CallbackInfoReturnable<Music> info) {
        for (var definition : MaestroRegistries.Resource.MUSIC_DEFINITION.listElements()) {
            if (!definition.test()) continue;
            info.setReturnValue(MusicCache.get(definition));
            return;
        }
    }
}
