package dev.obscuria.maestro.config;

import dev.obscuria.fragmentum.config.ConfigBuilder;
import dev.obscuria.fragmentum.config.ConfigValue;
import dev.obscuria.maestro.Maestro;

public final class ClientConfig {

    public static final ConfigValue<Boolean> ENABLED;

    public static void init() {}

    static {
        final var builder = new ConfigBuilder("obscuria/maestro-client.toml");

        ENABLED = builder
                .comment("Whether Maestro should be active at all.")
                .defineBoolean("enabled", true);

        builder.buildClient(Maestro.MODID);
    }
}
