package dev.obscuria.maestro.client.music;

import dev.obscuria.maestro.client.music.player.MusicTrack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.network.chat.Component;

public final class MusicToast implements Toast {

    private final MusicLayerEnum layer;
    private final MusicTrack track;

    public MusicToast(MusicLayerEnum layer, MusicTrack track) {
        this.layer = layer;
        this.track = track;
    }

    public Toast.Visibility render(GuiGraphics graphics, ToastComponent component, long timeSinceLastVisible) {
        var title = Component.literal("Music → %s".formatted(layer));
        var description = track.getName();
        graphics.blit(TEXTURE, 0, 0, 0, 32, this.width(), this.height());
        graphics.drawString(component.getMinecraft().font, title, 30, 7, -11534256, false);
        graphics.drawString(component.getMinecraft().font, description, 30, 18, -16777216, false);
        return timeSinceLastVisible >= 5000.0F * component.getNotificationDisplayTimeMultiplier()
                ? Visibility.HIDE
                : Visibility.SHOW;
    }
}
