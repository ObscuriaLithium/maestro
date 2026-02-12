package dev.obscuria.maestro.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import dev.obscuria.maestro.client.music.player.MusicPlayer;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class MixinGameRenderer {

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/toasts/ToastComponent;render(Lnet/minecraft/client/gui/GuiGraphics;)V"))
    private void injectDebugRenderer(DeltaTracker deltaTracker, boolean renderLevel,
                                     CallbackInfo info, @Local GuiGraphics graphics) {
        if (Minecraft.getInstance().gui.getDebugOverlay().showDebugScreen()) return;
        if (!(Minecraft.getInstance().getMusicManager() instanceof MusicPlayer player)) return;
        player.debugRenderer(graphics);
    }
}
