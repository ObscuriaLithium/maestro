package dev.obscuria.maestro.client.music.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public record TimeCondition(
        Optional<Integer> above,
        Optional<Integer> below
) implements MusicCondition {

    public static final MapCodec<TimeCondition> CODEC;

    @Override
    public MapCodec<TimeCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@Nullable Level level, @Nullable Player player) {
        if (level == null) return false;
        if (above.isPresent() && !isAbove(level, above.get())) return false;
        if (below.isPresent() && !isBelow(level, below.get())) return false;
        return true;
    }

    private boolean isAbove(Level level, int threshold) {
        return level.getDayTime() % 24000 >= threshold;
    }

    private boolean isBelow(Level level, int threshold) {
        return level.getDayTime() % 24000 < threshold;
    }

    static {
        CODEC = RecordCodecBuilder.mapCodec(codec -> codec.group(
                Codec.INT.optionalFieldOf("above").forGetter(TimeCondition::above),
                Codec.INT.optionalFieldOf("below").forGetter(TimeCondition::below)
        ).apply(codec, TimeCondition::new));
    }
}
