package dev.obscuria.maestro.client.music.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public record InGameCondition(
        boolean value
) implements MusicCondition {

    public static final MapCodec<InGameCondition> CODEC;

    @Override
    public MapCodec<InGameCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@Nullable Level level, @Nullable Player player) {
        return value == (level != null && player != null);
    }

    static {
        CODEC = RecordCodecBuilder.mapCodec(codec -> codec.group(
                Codec.BOOL.optionalFieldOf("value", true).forGetter(InGameCondition::value)
        ).apply(codec, InGameCondition::new));
    }
}
