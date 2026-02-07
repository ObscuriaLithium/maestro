package dev.obscuria.maestro.mixin.client;

import net.minecraft.client.sounds.SoundManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoundManager.class)
public abstract class MixinSoundManager {

    @Inject(method = "pause", at = @At("HEAD"), cancellable = true)
    private void suppressPause(CallbackInfo info) {
        info.cancel();
    }
}
