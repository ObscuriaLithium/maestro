package dev.obscuria.maestro.client.music.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public record BiomeTagCondition(
        Set<TagKey<Biome>> values
) implements MusicCondition {

    public static final Codec<BiomeTagCondition> CODEC;

    @Override
    public Codec<BiomeTagCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@Nullable Level level, @Nullable Player player) {
        if (level == null || player == null) return false;
        var holder = level.getBiome(player.blockPosition());
        for (var value : values) {
            if (!holder.is(value)) continue;
            return true;
        }
        return false;
    }

    static {
        CODEC = RecordCodecBuilder.create(codec -> codec.group(
                TagKey.codec(Registries.BIOME).listOf().xmap(Set::copyOf, List::copyOf).fieldOf("values").forGetter(BiomeTagCondition::values)
        ).apply(codec, BiomeTagCondition::new));
    }
}
