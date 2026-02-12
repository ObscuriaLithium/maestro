package dev.obscuria.maestro.config;

import dev.obscuria.fragmentum.config.ConfigBuilder;
import dev.obscuria.fragmentum.config.ConfigValue;
import dev.obscuria.maestro.Maestro;

public final class ClientConfig {

    public static final ConfigValue<Boolean> MAESTRO_ENABLED;
    public static final ConfigValue<Boolean> VANILLA_ENABLED;
    public static final ConfigValue<Boolean> DEBUG_OVERLAY;

    public static boolean isMaestroEnabled() {
        try {
            return MAESTRO_ENABLED.get();
        } catch (Exception ignored) {
            return false;
        }
    }

    public static boolean isVanillaEnabled() {
        try {
            return VANILLA_ENABLED.get();
        } catch (Exception ignored) {
            return false;
        }
    }

    public static boolean isDebugOverlayEnabled() {
        try {
            return DEBUG_OVERLAY.get();
        } catch (Exception ignored) {
            return false;
        }
    }

    public static void init() {}

    static {
        final var builder = new ConfigBuilder("obscuria/maestro-client.toml");

        MAESTRO_ENABLED = builder
                .comment(
                        "Whether Maestro custom music should be active at all.",
                        "When turned off, only vanilla music will be played.")
                .defineBoolean("maestro_enabled", true);
        VANILLA_ENABLED = builder
                .comment(
                        "Whether vanilla music should be active at all.",
                        "When turned off, music can only be played via Maestro Definitions.",
                        "Intended for advanced users who want full control from a clean slate.")
                .defineBoolean("vanilla_enabled", true);
        DEBUG_OVERLAY = builder
                .comment(
                        "Renders a debug overlay showing the current music layer state.",
                        "Displays active tracks, silence, and suppression.",
                        "Intended for debugging and development.")
                .defineBoolean("debug_overlay", false);

        builder.buildClient(Maestro.MODID);
    }
}
