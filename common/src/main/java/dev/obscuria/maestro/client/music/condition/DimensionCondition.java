package dev.obscuria.maestro.client.music.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public record DimensionCondition(
        ResourceLocation dimension
) implements MusicCondition {

    public static final Codec<DimensionCondition> CODEC;

    @Override
    public Codec<DimensionCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@Nullable Level level, @Nullable Player player) {
        if (level == null || player == null) return false;
        return level.dimension().location().equals(dimension);
    }

    static {
        CODEC = RecordCodecBuilder.create(codec -> codec.group(
                ResourceLocation.CODEC.fieldOf("dimension").forGetter(DimensionCondition::dimension)
        ).apply(codec, DimensionCondition::new));
    }
}
