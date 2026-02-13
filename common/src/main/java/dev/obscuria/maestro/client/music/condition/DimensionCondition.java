package dev.obscuria.maestro.client.music.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public record DimensionCondition(
        Set<Identifier> values
) implements MusicCondition {

    public static final MapCodec<DimensionCondition> CODEC;

    @Override
    public MapCodec<DimensionCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@Nullable Level level, @Nullable Player player) {
        if (level == null) return false;
        return values.contains(level.dimension().identifier());
    }

    static {
        CODEC = RecordCodecBuilder.mapCodec(codec -> codec.group(
                Identifier.CODEC.listOf().xmap(Set::copyOf, List::copyOf).fieldOf("values").forGetter(DimensionCondition::values)
        ).apply(codec, DimensionCondition::new));
    }
}
