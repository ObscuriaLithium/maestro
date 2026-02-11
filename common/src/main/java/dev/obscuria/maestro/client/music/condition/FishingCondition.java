package dev.obscuria.maestro.client.music.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public record FishingCondition(
        boolean value
) implements MusicCondition {

    public static final Codec<FishingCondition> CODEC;

    @Override
    public Codec<FishingCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@Nullable Level level, @Nullable Player player) {
        if (player == null) return false;
        return value == (player.fishing != null);
    }

    static {
        CODEC = RecordCodecBuilder.create(codec -> codec.group(
                Codec.BOOL.optionalFieldOf("value", true).forGetter(FishingCondition::value)
        ).apply(codec, FishingCondition::new));
    }
}
