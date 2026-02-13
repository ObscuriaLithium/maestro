package dev.obscuria.maestro.mixin.client;

import dev.obscuria.maestro.client.music.player.MusicPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import net.minecraft.client.sounds.MusicManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {

    @Shadow @Final @Mutable private MusicManager musicManager;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void injectMusicPlayer(GameConfig gameConfig, CallbackInfo info) {
        this.musicManager = new MusicPlayer((Minecraft) (Object) this);
    }
}
