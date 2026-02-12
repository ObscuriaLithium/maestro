package dev.obscuria.maestro.client.music.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public record UnderwaterCondition(
        boolean value
) implements MusicCondition {

    public static final MapCodec<UnderwaterCondition> CODEC;

    @Override
    public MapCodec<UnderwaterCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@Nullable Level level, @Nullable Player player) {
        if (level == null || player == null) return false;
        return value == player.isUnderWater();
    }

    static {
        CODEC = RecordCodecBuilder.mapCodec(codec -> codec.group(
                Codec.BOOL.optionalFieldOf("value", true).forGetter(UnderwaterCondition::value)
        ).apply(codec, UnderwaterCondition::new));
    }
}
