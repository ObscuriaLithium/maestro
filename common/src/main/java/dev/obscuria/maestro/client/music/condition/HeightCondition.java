package dev.obscuria.maestro.client.music.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public record HeightCondition(
        Optional<Integer> above,
        Optional<Integer> below
) implements MusicCondition {

    public static final Codec<HeightCondition> CODEC;

    @Override
    public Codec<HeightCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@Nullable Level level, @Nullable Player player) {
        if (level == null || player == null) return false;
        if (above.isPresent() && !isAbove(player, above.get())) return false;
        if (below.isPresent() && !isBelow(player, below.get())) return false;
        return true;
    }

    private boolean isAbove(Player player, int threshold) {
        return player.blockPosition().getY() >= threshold;
    }

    private boolean isBelow(Player player, int threshold) {
        return player.blockPosition().getY() < threshold;
    }

    static {
        CODEC = RecordCodecBuilder.create(codec -> codec.group(
                Codec.INT.optionalFieldOf("above").forGetter(HeightCondition::above),
                Codec.INT.optionalFieldOf("below").forGetter(HeightCondition::below)
        ).apply(codec, HeightCondition::new));
    }
}
