package dev.obscuria.maestro.client.music.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.Nullable;

public record BiomeTagCondition(
        TagKey<Biome> tagKey
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
        return holder.is(tagKey);
    }

    static {
        CODEC = RecordCodecBuilder.create(codec -> codec.group(
                TagKey.codec(Registries.BIOME).fieldOf("tag_key").forGetter(BiomeTagCondition::tagKey)
        ).apply(codec, BiomeTagCondition::new));
    }
}
