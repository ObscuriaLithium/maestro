package dev.obscuria.maestro.client.music.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public record WeatherCondition(
        Optional<Boolean> isRaining,
        Optional<Boolean> isThundering
) implements MusicCondition {

    public static final Codec<WeatherCondition> CODEC;

    @Override
    public Codec<WeatherCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@Nullable Level level, @Nullable Player player) {
        if (level == null) return false;
        if (isRaining.isPresent() && level.isRaining() != isRaining.get()) return false;
        if (isThundering.isPresent() && level.isThundering() != isThundering.get()) return false;
        return true;
    }

    static {
        CODEC = RecordCodecBuilder.create(codec -> codec.group(
                Codec.BOOL.optionalFieldOf("is_raining").forGetter(WeatherCondition::isRaining),
                Codec.BOOL.optionalFieldOf("is_thundering").forGetter(WeatherCondition::isThundering)
        ).apply(codec, WeatherCondition::new));
    }
}
