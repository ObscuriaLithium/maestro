package dev.obscuria.maestro.client.music.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public record YLevelCondition(
        Optional<Integer> above,
        Optional<Integer> below
) implements MusicCondition {

    public static final Codec<YLevelCondition> CODEC;

    @Override
    public Codec<YLevelCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@Nullable Level level, @Nullable Player player) {
        if (level == null || player == null) return false;
        if (above.isPresent() && !isAbove(player, above.get())) return false;
        if (below.isPresent() && !isBelow(player, below.get())) return false;
        return true;
    }

    private boolean isAbove(Player player, int height) {
        return player.blockPosition().getY() >= height;
    }

    private boolean isBelow(Player player, int height) {
        return player.blockPosition().getY() < height;
    }

    static {
        CODEC = RecordCodecBuilder.create(codec -> codec.group(
                Codec.INT.optionalFieldOf("above").forGetter(YLevelCondition::above),
                Codec.INT.optionalFieldOf("below").forGetter(YLevelCondition::below)
        ).apply(codec, YLevelCondition::new));
    }
}
