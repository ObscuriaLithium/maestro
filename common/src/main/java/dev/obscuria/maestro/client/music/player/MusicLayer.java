package dev.obscuria.maestro.client.music.player;

import dev.obscuria.maestro.client.music.MusicLayerEnum;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

public final class MusicLayer {

    private static final Component SILENT_LABEL;

    private final MusicLayerEnum layer;
    private final Component layerLabel;
    private @Nullable MusicTrack activeTrack;
    private @Nullable Component debugComponent;

    public MusicLayer(MusicLayerEnum layer) {
        this.layer = layer;
        this.layerLabel = Component.literal(layer.toString()).withStyle(ChatFormatting.LIGHT_PURPLE);
    }

    public void tick(boolean suppressedByHigherLayer) {
        if (activeTrack == null) return;
        this.activeTrack.onLayerTick();
        this.activeTrack.setSuppressed(suppressedByHigherLayer);
    }

    public void select(@Nullable MusicTrack track) {
        if (activeTrack == track) return;
        this.activeTrack = track;
        if (track == null) return;
        track.onAssigned(layer);
    }

    public void stop(MusicStopReason reason) {
        if (activeTrack != null) {
            activeTrack.stop(reason);
            activeTrack = null;
        }
    }

    public boolean isActive() {
        return activeTrack != null;
    }

    public int debugPrepare(Font font) {
        this.debugComponent = buildDebugComponent();
        return font.width(debugComponent);
    }

    public int debugRender(GuiGraphics graphics, Font font, int x, int y) {
        if (debugComponent == null) return 0;
        graphics.drawString(font, debugComponent, x, y, 0xFFFFFFFF);
        return 11;
    }

    private Component buildDebugComponent() {
        final var result = layerLabel.copy();
        result.append(CommonComponents.SPACE);
        return result.append(activeTrack != null
                ? colorByRatio(activeTrack.getName(), activeTrack.getVolume())
                : SILENT_LABEL);
    }

    private Component colorByRatio(String text, float ratio) {
        ratio = Mth.clamp(ratio, 0f, 1f);
        var split = Math.round(text.length() * ratio);
        var left = Component.literal(text.substring(0, split)).withStyle(ChatFormatting.GRAY);
        var right = Component.literal(text.substring(split)).withStyle(ChatFormatting.DARK_GRAY);
        return left.append(right);
    }

    static {
        SILENT_LABEL = Component.literal("silent").withStyle(ChatFormatting.DARK_GRAY);
    }
}
