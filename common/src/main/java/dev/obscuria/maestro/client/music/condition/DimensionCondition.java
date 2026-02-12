package dev.obscuria.maestro.client.music.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public record DimensionCondition(
        Set<ResourceLocation> values
) implements MusicCondition {

    public static final MapCodec<DimensionCondition> CODEC;

    @Override
    public MapCodec<DimensionCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@Nullable Level level, @Nullable Player player) {
        if (level == null) return false;
        return values.contains(level.dimension().location());
    }

    static {
        CODEC = RecordCodecBuilder.mapCodec(codec -> codec.group(
                ResourceLocation.CODEC.listOf().xmap(Set::copyOf, List::copyOf).fieldOf("values").forGetter(DimensionCondition::values)
        ).apply(codec, DimensionCondition::new));
    }
}
